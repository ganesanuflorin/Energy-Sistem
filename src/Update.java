import java.util.ArrayList;

public final class Update {
    private ArrayList<ConsumerUpdate> newConsumers;
    private ArrayList<DistributorUpdate> distributorChanges;
    private ArrayList<ProducerUpdate> producerChanges;

    public ArrayList<ConsumerUpdate> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final ArrayList<ConsumerUpdate> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public ArrayList<DistributorUpdate> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final ArrayList<DistributorUpdate> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public ArrayList<ProducerUpdate> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(final ArrayList<ProducerUpdate> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
