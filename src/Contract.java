public final class Contract {
    private int price;
    private int months;
    private Distributor distributor;
    private Consumer consumer;

    public Contract(final int price, final int months, final Distributor distributor,
                    final Consumer consumer) {
        this.price = price;
        this.months = months;
        this.distributor = distributor;
        this.consumer = consumer;
    }

    public int getPrice() {
        return price;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(final int months) {
        this.months = months;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public Consumer getConsumer() {
        return consumer;
    }
}
