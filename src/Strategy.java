import java.util.ArrayList;

public interface Strategy {
    void executeStrategy(Distributor distributor,
                         ArrayList<Producer> producerArrayList,
                         int i);
}
