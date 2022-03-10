import java.util.ArrayList;

public final class InputReader {
    private InputData initialData;
    private ArrayList<Update> monthlyUpdates;
    private int numberOfTurns;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InputData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InputData initialData) {
        this.initialData = initialData;
    }

    public ArrayList<Update> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final ArrayList<Update> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
