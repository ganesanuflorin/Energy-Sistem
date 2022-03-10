import java.util.Comparator;

public final class PriceComparator implements Comparator<Producer> {
    @Override
    public int compare(final Producer producer, final Producer t1) {
        double c = producer.getPriceKW() - t1.getPriceKW();
        if (c == 0) {
            c = t1.getEnergyPerDistributor() - producer.getEnergyPerDistributor();
            if (c == 0) {
                c = producer.getId() - t1.getId();
            }
        }
        int result = 0;
        if (c == 0) {
            result = 0;
        } else if (c > 0.0d) {
            result = 1;
        } else if (c < 0.0d) {
            result = -1;
        }
        return result;
    }
}
