public final class Consumer {
    private boolean indebted = false;
    private boolean noMoney = false;
    private int money;
    private int id;
    private int initialBudget;
    private int monthlyIncome;
    private Contract contract = null;
    private Contract secondContract = null;

    boolean checkIfContractIsNull() {
        return contract == null;
    }
    boolean checkIfSecondContractIsNull() {
        return secondContract == null;
    }

    public boolean isIndebted() {
        return indebted;
    }

    public void setIndebted(final boolean indebted) {
        this.indebted = indebted;
    }

    public Contract getSecondContract() {
        return secondContract;
    }

    public void setSecondContract(final Contract secondContract) {
        this.secondContract = secondContract;
    }

    public boolean isNoMoney() {
        return noMoney;
    }

    public void setNoMoney(final boolean noMoney) {
        this.noMoney = noMoney;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(final int money) {
        this.money = money;
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    /**
     * @param initialBudget set money
     */
    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
        setMoney(initialBudget);
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
}
