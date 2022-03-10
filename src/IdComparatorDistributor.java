import java.util.Comparator;

public final class IdComparatorDistributor implements Comparator<Distributor> {

    @Override
    public int compare(final Distributor distributor, final Distributor t1) {
        return distributor.getId() - t1.getId();
    }
}
