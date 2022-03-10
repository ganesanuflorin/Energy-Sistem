import java.util.ArrayList;

public final class ProducerMonthOutTemplate {
    private int month;
    private ArrayList<Integer> distributorsIds;

    public ProducerMonthOutTemplate(final int month, final ArrayList<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(final int month) {
        this.month = month;
    }

    public ArrayList<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(final ArrayList<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
