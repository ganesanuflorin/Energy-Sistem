import java.util.Comparator;

public final class IdComparator implements Comparator<Producer> {
    @Override
    public int compare(final Producer producer, final Producer t1) {
        return producer.getId() - t1.getId();
    }
}
