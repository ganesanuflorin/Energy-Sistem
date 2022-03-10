import java.util.ArrayList;

public final class ProducerOutTemplate {
    private int id;
    private int maxDistributors;
    private float priceKW;
    private String energyType;
    private int energyPerDistributor;
    private ArrayList<ProducerMonthOutTemplate> monthlyStats;

    public ProducerOutTemplate(final int id, final int maxDistributors, final float priceKW,
                               final String energyType, final int energyPerDistributor,
                               final ArrayList<ProducerMonthOutTemplate> monthlyStats) {
        this.id = id;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyType = energyType;
        this.energyPerDistributor = energyPerDistributor;
        this.monthlyStats = monthlyStats;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(final int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(final float priceKW) {
        this.priceKW = priceKW;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(final String energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public ArrayList<ProducerMonthOutTemplate> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(final ArrayList<ProducerMonthOutTemplate> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }
}
