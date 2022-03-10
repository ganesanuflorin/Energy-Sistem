import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Entry point to the simulation
 */
public final class Main {

    private Main() {
    }

    /**
     * pick the strategy needed
     *
     * @param distributor
     * @return
     */
    public static Strategy selectStrategy(Distributor distributor) {
        if (distributor.getProducerStrategy().equals("GREEN")) {
            return new GreenDistributorStrategy();
        } else if (distributor.getProducerStrategy().equals("PRICE")) {
            return new PriceDistributorStrategy();
        } else {
            return new QuantityDistributorStrategy();
        }
    }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */

    public static void main(final String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DefaultPrettyPrinter dpp = new DefaultPrettyPrinter();
        DefaultPrettyPrinter.Indenter defaultPrettyPrinterIndenter
                = new DefaultIndenter("  ", DefaultIndenter.SYS_LF);
        dpp.indentObjectsWith(defaultPrettyPrinterIndenter);
        File inputFile = new File(args[0]);

        // se incarca datele de intrare despre producatori, distribuitori si produceri
        InputReader inputReader = objectMapper.readValue(inputFile, InputReader.class);
        ArrayList<Consumer> cons = inputReader.getInitialData().getConsumers();
        ArrayList<Distributor> dist = inputReader.getInitialData().getDistributors();
        ArrayList<Producer> prod = inputReader.getInitialData().getProducers();
        HashMap<Distributor, ArrayList<Contract>> contracts = new HashMap<>();

        for (int i = 0; i < prod.size(); i++) {
            prod.get(i).setLeft(prod.get(i).getMaxDistributors());
        }

        for (int i = 0; i < dist.size(); i++) {
            contracts.put(dist.get(i), new ArrayList<>());
        }
        for (int i = 0; i <= inputReader.getNumberOfTurns(); i++) {
            // flow lunar
            if (i != 0) {
                new Main().normalTurnUpdate(inputReader, i, dist, cons, contracts);
            }

            // prima runda
            if (i == 0) {
                for (int j = 0; j < dist.size(); j++) {
                    Strategy currentStrategy = selectStrategy(dist.get(j));
                    currentStrategy.executeStrategy(dist.get(j), prod, i);
                    dist.get(j).setProductionCost(i);
                }
            }

            // setarea pretului pe contract
            for (int j = 0; j < dist.size(); j++) {
                if (!dist.get(j).isNoMoney()) {
                    dist.get(j).setPrice(contracts.get(dist.get(j)).size());
                }
            }
            dist.sort(new DistributorPriceComparator());

            // consumatorii isi aleg contract si primesc salariu
            new Main().consGetContrAddMoney(cons, dist, contracts);

            new Main().consumersPayDistributors(cons, contracts);

            // distribuitorii platesc
            for (int j = 0; j < dist.size(); j++) {
                Distributor currentDistributor = dist.get(j);
                if (!dist.get(j).isNoMoney()) {
                    int currentSum = dist.get(j).getSum(contracts.get(currentDistributor).size());
                    currentDistributor.setMoney(currentDistributor.getMoney() - currentSum);
                    if (currentDistributor.getMoney() < 0) {
                        currentDistributor.setNoMoney(true);
                        for (int k = 0; k < contracts.get(currentDistributor).size(); k++) {
                            contracts.get(currentDistributor).get(k).getConsumer()
                                    .setContract(null);
                        }
                    }
                }
            }

            if (i != 0) {
                // actualizam datele despre producatori
                Update currentUpdate = inputReader.getMonthlyUpdates().get(i - 1);
                for (int j = 0; j < currentUpdate.getProducerChanges().size(); j++) {
                    for (int k = 0; k < prod.size(); k++) {
                        if (currentUpdate.getProducerChanges().get(j).getId()
                                == prod.get(k).getId()) {
                            prod.get(k).setEnergyPerDistributor(currentUpdate
                                    .getProducerChanges().get(j).getEnergyPerDistributor());
                            prod.get(k).setWasChanged(true);
                            break;
                        }
                    }
                }

                dist.sort(new IdComparatorDistributor());
                // tinem lista de produceri sortata dupa id
                prod.sort(new IdComparator());
                new Main().executeStrategy(dist, prod, i);

            }
        }
        ArrayList<ConsumerOutTemplate> consumerOutTemplates = new ArrayList<>();
        ArrayList<ProducerOutTemplate> producerOutTemplates = new ArrayList<>();
        ArrayList<DistributorOutTemplate> distributorOutTemplates = new ArrayList<>();
        OutputTemplate outputTemplate = new OutputTemplate();

        new Main().outBuilder(cons, consumerOutTemplates, producerOutTemplates, dist,
                prod, inputReader, contracts, distributorOutTemplates);

        outputTemplate.setConsumers(consumerOutTemplates);
        outputTemplate.setDistributors(distributorOutTemplates);
        outputTemplate.setEnergyProducers(producerOutTemplates);
        File out = new File(args[1]);
        objectMapper.writer(dpp).writeValue(out, outputTemplate);
    }

    /**
     * all distributors execute strategy if it is the case
     *
     * @param dist
     * @param prod
     * @param i
     */
    public void executeStrategy(final ArrayList<Distributor> dist,
                                final ArrayList<Producer> prod, final int i) {
        for (int j = 0; j < dist.size(); j++) {
            if (!dist.get(j).isNoMoney()) {
                boolean pause = false;
                for (int k = 0; k < dist.get(j).producers.size(); k++) {
                    if (dist.get(j).producers.get(k).isWasChanged()) {
                        dist.get(j).producers.get(k)
                                .setLeft(dist.get(j).producers.get(k).getLeft() + 1);
                        Strategy currentStrategy = selectStrategy(dist.get(j));
                        currentStrategy.executeStrategy(dist.get(j), prod, i);
                        pause = true;
                        break;
                    }
                }
                if (!pause) {
                    dist.get(j).getMonthProducers().put(i, dist.get(j).producers);
                }
                dist.get(j).setProductionCost(i);
            }
        }
        for (int j = 0; j < prod.size(); j++) {
            prod.get(j).setWasChanged(false);
        }
    }

    /**
     * output builder
     *
     * @param cons
     * @param consumerOutTemplates
     * @param producerOutTemplates
     * @param dist
     * @param prod
     * @param inputReader
     * @param contracts
     * @param distributorOutTemplates
     */
    public void outBuilder(final ArrayList<Consumer> cons,
                           final ArrayList<ConsumerOutTemplate> consumerOutTemplates,
                           final ArrayList<ProducerOutTemplate> producerOutTemplates,
                           final ArrayList<Distributor> dist,
                           final ArrayList<Producer> prod,
                           final InputReader inputReader,
                           final HashMap<Distributor, ArrayList<Contract>> contracts,
                           final ArrayList<DistributorOutTemplate> distributorOutTemplates) {
        //formam output pt consumeri
        for (int i = 0; i < cons.size(); i++) {
            consumerOutTemplates.add(new ConsumerOutTemplate(cons.get(i).getId(),
                    cons.get(i).isNoMoney(),
                    cons.get(i).getMoney()));
        }
        //formam output pt produceri
        for (int i = 0; i < prod.size(); i++) {
            Producer currentProducer = prod.get(i);
            ArrayList<ProducerMonthOutTemplate> stats = new ArrayList<>();
            for (int j = 1; j <= inputReader.getNumberOfTurns(); j++) {
                stats.add(new ProducerMonthOutTemplate(j, new ArrayList<>()));
            }
            for (int j = 0; j < dist.size(); j++) {
                for (int k = 1; k <= inputReader.getNumberOfTurns(); k++) {
                    if (dist.get(j).getMonthProducers().get(k).contains(currentProducer)) {
                        stats.get(k - 1).getDistributorsIds().add(dist.get(j).getId());
                    }
                }
            }
            producerOutTemplates.add(new ProducerOutTemplate(currentProducer.getId(),
                    currentProducer.getMaxDistributors(), (float) currentProducer.getPriceKW(),
                    currentProducer.getEnergyType(), currentProducer.getEnergyPerDistributor(),
                    stats));
        }
        //formam output pt distributori
        for (int i = 0; i < dist.size(); i++) {
            if (!dist.get(i).isNoMoney()) {
                ArrayList<ContractOutTemplate> contractOutTemplates = new ArrayList<>();

                ArrayList<Contract> currentContractList
                        = contracts.get(dist.get(i));
                for (int j = 0; j < currentContractList.size(); j++) {
                    if (!currentContractList.get(j).getConsumer().isNoMoney()) {
                        contractOutTemplates.add(new ContractOutTemplate(currentContractList
                                .get(j).getConsumer().getId(),
                                currentContractList.get(j).getPrice(), currentContractList.get(j)
                                .getMonths()));
                    }
                }
                Distributor currentDistributor = dist.get(i);
                distributorOutTemplates.add(new DistributorOutTemplate(currentDistributor.getId(),
                        currentDistributor.getEnergyNeededKW(), currentDistributor.getPrice(),
                        currentDistributor.getMoney(), currentDistributor.getProducerStrategy(),
                        currentDistributor.isNoMoney(), contractOutTemplates));
            }

        }
    }

    /**
     * update consumers and distributors
     *
     * @param inputReader
     * @param i
     * @param distributorArrayList
     * @param consumerArrayList
     */
    public void normalTurnUpdate(final InputReader inputReader, final int i,
                                 final ArrayList<Distributor> distributorArrayList,
                                 final ArrayList<Consumer> consumerArrayList,
                                 final HashMap<Distributor, ArrayList<Contract>> contracts) {
        Update currentUpdate = inputReader.getMonthlyUpdates().get(i - 1);
        for (int j = 0; j < currentUpdate.getNewConsumers().size(); j++) {
            Consumer newConsumer = new Consumer();
            newConsumer.setInitialBudget(currentUpdate.getNewConsumers().get(j)
                    .getInitialBudget());
            newConsumer.setId(currentUpdate.getNewConsumers().get(j).getId());
            newConsumer.setMonthlyIncome(currentUpdate.getNewConsumers().get(j)
                    .getMonthlyIncome());
            consumerArrayList.add(newConsumer);
        }
        for (int j = 0; j < currentUpdate.getDistributorChanges().size(); j++) {
            for (int k = 0; k < distributorArrayList.size(); k++) {
                Distributor current = distributorArrayList.get(k);
                if (!current.isNoMoney()) {
                    if (current.getId() == currentUpdate.getDistributorChanges().get(j).getId()) {
                        DistributorUpdate change = currentUpdate.getDistributorChanges().get(j);
                        current.setInitialInfrastructureCost(change.getInfrastructureCost());
                    }
                    current.setProductionCost(i - 1);
                    distributorArrayList.get(k).setPrice(contracts.get(current).size());
                }
            }
        }
    }

    /**
     * consumers pick a contract and receive salary
     *
     * @param cons
     * @param dist
     * @param contracts
     */
    public void consGetContrAddMoney(final ArrayList<Consumer> cons,
                                     final ArrayList<Distributor> dist,
                                     final HashMap<Distributor, ArrayList<Contract>> contracts) {
        for (int j = 0; j < cons.size(); j++) {
            if (!cons.get(j).isNoMoney()) {
                cons.get(j).setMoney(cons.get(j).getMoney()
                        + cons.get(j).getMonthlyIncome());
                Contract c = cons.get(j).getContract();
                Consumer consumer = cons.get(j);
                if (!consumer.checkIfContractIsNull()) {
                    if (c.getMonths() <= 0 || c.getDistributor().isNoMoney()) {
                        contracts.get(c.getDistributor()).remove(c);
                        cons.get(j).setContract(null);
                    }
                }
                if (!consumer.checkIfContractIsNull()) {
                    if (c.getMonths() > 0) {
                        continue;
                    }
                }
                boolean second = false;
                if (!consumer.checkIfContractIsNull()) {
                    if (c.getMonths() == 0 && consumer.isIndebted()) {
                        second = true;
                    }
                }

                for (int k = 0; k < dist.size(); k++) {
                    if (!dist.get(k).isNoMoney()) {
                        Distributor dis = dist.get(k);
                        Contract contr = new Contract(dis.getPrice(),
                                dis.getContractLength(), dis, cons.get(j));
                        if (second) {
                            consumer.setSecondContract(contr);
                        } else {
                            consumer.setContract(contr);
                        }
                        if (contracts.get(dis).isEmpty()) {
                            ArrayList<Contract> currentContracts = contracts.get(dis);
                            currentContracts.add(contr);
                            contracts.put(dis, currentContracts);
                        } else {
                            contracts.get(dis).add(contr);
                        }
                        break;
                    }
                }

            }
        }
    }

    /**
     * consumers pay to distributors
     *
     * @param cons
     * @param contracts
     */
    public void consumersPayDistributors(final ArrayList<Consumer> cons,
                                         final HashMap<Distributor, ArrayList<Contract>> contracts) {
        for (int j = 0; j < cons.size(); j++) {
            if (!cons.get(j).isNoMoney()) {
                Consumer con = cons.get(j);
                int sum = cons.get(j).getContract().getPrice();
                if (con.isIndebted()) {
                    sum = (int) Math.round(Math.floor(1.2f * sum));
                    Distributor dis = con.getContract().getDistributor();
                    Contract c = con.getContract();
                    if (con.getMoney() < sum + c.getPrice()) {
                        con.setNoMoney(true);
                        contracts.get(dis).remove(c);
                        continue;
                    } else {
                        con.setIndebted(false);
                        c.getDistributor().setMoney(dis.getMoney() + sum);
                        con.setMoney(con.getMoney() - sum);
                    }
                    if (con.checkIfSecondContractIsNull()) {
                        if (con.getMoney() < c.getPrice()) {
                            con.setIndebted(true);
                        }
                    }
                    c.setMonths(c.getMonths() - 1);
                } else {
                    Contract c = con.getContract();
                    if (con.getMoney() < sum) {
                        con.setIndebted(true);
                        c.setMonths(c.getMonths() - 1);
                    } else {
                        c.getDistributor().setMoney(c.getDistributor().getMoney() + sum);
                        con.setMoney(con.getMoney() - sum);
                        con.getContract().setMonths(c.getMonths() - 1);
                    }
                }
            }
        }
    }
}
