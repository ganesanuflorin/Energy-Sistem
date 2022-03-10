import java.util.ArrayList;

public final class GreenDistributorStrategy implements Strategy {
    @Override
    public void executeStrategy(final Distributor distributor,
                                final ArrayList<Producer> prod,
                                final int i) {
        ArrayList<Producer> newProducerArrayList = new ArrayList<>(prod);
        newProducerArrayList.sort(new GreenComparator());
        ArrayList<Producer> aux = new ArrayList<>();
        int energy = distributor.getEnergyNeededKW();
        for (int j = 0; j < newProducerArrayList.size(); j++) {
            if (newProducerArrayList.get(j).getLeft() > 0) {
                aux.add(newProducerArrayList.get(j));
                newProducerArrayList.get(j).setLeft(newProducerArrayList.get(j).getLeft() - 1);
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
