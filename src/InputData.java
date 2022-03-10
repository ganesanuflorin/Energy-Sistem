import java.util.ArrayList;

public final class InputData {
    private ArrayList<Consumer> consumers = new ArrayList<>();
    private ArrayList<Distributor> distributors = new ArrayList<>();
    private ArrayList<Producer> producers = new ArrayList<>();

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<Distributor> distributors) {
        this.distributors = distributors;
    }

    public ArrayList<Producer> getProducers() {
        return producers;
    }

    public void setProducers(final ArrayList<Producer> producers) {
        this.producers = producers;
    }
}
