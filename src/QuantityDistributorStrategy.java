import java.util.ArrayList;

public final class QuantityDistributorStrategy implements Strategy {
    @Override
    public void executeStrategy(final Distributor distributor,
                                final ArrayList<Producer> producerArrayList, final int i) {
        ArrayList<Producer> newProducerArrayList = new ArrayList<>(producerArrayList);
        newProducerArrayList.sort(new QuantityComparator());
        ArrayList<Producer> aux = new ArrayList<>();
        int energy = distributor.getEnergyNeededKW();
        for (int j = 0; j < newProducerArrayList.size(); j++) {
            if (newProducerArrayList.get(j).getLeft() > 0) {
                newProducerArrayList.get(j).setLeft(newProducerArrayList.get(j).getLeft() - 1);
                aux.add(newProducerArrayList.get(j));
                energy -= newProducerArrayList.get(j).getEnergyPerDistributor();
                if (energy < 0) {
                    break;
                }
            }
        }
        distributor.getMonthProducers().put(i, aux);
        distributor.producers = new ArrayList<>(aux);
    }
}
