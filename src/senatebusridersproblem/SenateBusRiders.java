package senatebusridersproblem;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Main class.
 */
public class SenateBusRiders {
    private static Logger logger = Logger.getLogger(SenateBusRiders.class.getName());
    public static final SharedVariables sharedVariables = new SharedVariables();
    public static final long RIDER_ARRIVAL_TIME_MEAN = 3000;
    public static final long BUS_ARRIVAL_TIME_MEAN = 60000;
    public static void main(String args[]) {
       init();
    }
    public static void init() {
        new BusFactory().start();
        new RidersFactory().start();
    }
}
