import java.util.ArrayList;
import java.util.HashMap;

public final class Distributor {
    private final HashMap<Integer, ArrayList<Producer>> monthProducers = new HashMap<>();
    ArrayList<Producer> producers = new ArrayList<>();
    private boolean noMoney = false;
    private int id;
    private int contractLength;
    private int initialBudget;
    private double initialInfrastructureCost;
    private int energyNeededKW;
    private String producerStrategy;
    private int money;
    private int productionCost;
    private int price;

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public void setProducers(final ArrayList<Producer> producers) {
        this.producers = producers;
    }

    public boolean isNoMoney() {
        return noMoney;
    }

    public void setNoMoney(final boolean noMoney) {
        this.noMoney = noMoney;
    }

    public int getPrice() {
        return price;
    }

    /**
     * set price for contract
     * @param count
     */
    public void setPrice(final int count) {
        int profit = (int) Math.round(Math.floor(0.2 * productionCost));
        if (count == 0) {
            price = (int) initialInfrastructureCost + productionCost + profit;
        } else {
            price = (int) Math.round(Math.floor(initialInfrastructureCost /
                    count) + productionCost + profit);
        }
    }

    public int getSum(final int count) {
        return (int) (initialInfrastructureCost + productionCost * count);
    }

    public HashMap<Integer, ArrayList<Producer>> getMonthProducers() {
        return monthProducers;
    }

    public void setProductionCost(final int index) {
        double aux = 0;
        ArrayList<Producer> currentProducers = monthProducers.get(index);
        for (int i = 0; i < currentProducers.size(); i++) {
            aux += currentProducers.get(i).getPriceKW()
                    * currentProducers.get(i).getEnergyPerDistributor();
        }

        productionCost = (int) Math.round(Math.floor(aux / 10));
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
        setMoney(initialBudget);
    }

    public double getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(final int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(final String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }
}
