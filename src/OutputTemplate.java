import java.util.ArrayList;

public final class OutputTemplate {
    private ArrayList<ConsumerOutTemplate> consumers;
    private ArrayList<DistributorOutTemplate> distributors;
    private ArrayList<ProducerOutTemplate> energyProducers;

    public ArrayList<ProducerOutTemplate> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(final ArrayList<ProducerOutTemplate> energyProducers) {
        this.energyProducers = energyProducers;
    }

    public ArrayList<ConsumerOutTemplate> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<ConsumerOutTemplate> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<DistributorOutTemplate> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<DistributorOutTemplate> distributors) {
        this.distributors = distributors;
    }
}
