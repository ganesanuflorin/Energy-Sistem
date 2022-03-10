import java.util.Comparator;

public final class DistributorPriceComparator implements Comparator<Distributor> {

    @Override
    public int compare(Distributor distributor, Distributor t1) {
        return distributor.getPrice() - t1.getPrice();
    }
}
