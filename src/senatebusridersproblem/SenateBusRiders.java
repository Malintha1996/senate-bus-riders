package senatebusridersproblem;

/**
 * Main class.
 */
public class SenateBusRiders {
    public static final SharedResources SHARED_RESOURCES = new SharedResources();
    public static final long RIDER_ARRIVAL_TIME_MEAN = 30000;
    public static final long BUS_ARRIVAL_TIME_MEAN = 1200000;
    public static void main(String args[]) {
       init();
    }
    public static void init() {
        new BusFactory().start();
        new RiderFactory().start();
    }
}
