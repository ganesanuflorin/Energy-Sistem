import entities.EnergyType;

import java.util.Comparator;

public final class GreenComparator implements Comparator<Producer> {
    @Override
    public int compare(final Producer producer, final Producer t1) {
        int c = Boolean.compare(EnergyType.valueOf(t1.getEnergyType()).isRenewable(),
                EnergyType.valueOf(producer.getEnergyType()).isRenewable());

        if (c == 0) {
            c = Double.compare(producer.getPriceKW(), t1.getPriceKW());
            if (c == 0) {
                c = t1.getEnergyPerDistributor() - producer.getEnergyPerDistributor();
                if (c == 0) {
                    c = t1.getId() - producer.getId();
                }
            }
        }
        return c;
    }
}
