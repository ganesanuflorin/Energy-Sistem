import java.util.Comparator;

public final class QuantityComparator implements Comparator<Producer> {

    @Override
    public int compare(final Producer producer, final Producer t1) {
        int c = t1.getEnergyPerDistributor() - producer.getEnergyPerDistributor();
        if (c == 0) {
            c = t1.getId() - producer.getId();
        }
        return c;
    }
}
