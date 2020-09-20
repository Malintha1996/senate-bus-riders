package senatebusridersproblem;

import java.util.logging.Logger;

public class BusFactory extends Thread {
    private static Logger logger = Logger.getLogger(BusFactory.class.getName());
    @Override
    public void run() {
        this.produce();
    }

    public void produce() {
        long produceInterval = 0;
        while (true) {
            produceInterval = (long) (-(Math.log(Math.random())) * SenateBusRiders.BUS_ARRIVAL_TIME_MEAN);
            logger.info("Next bus arrives in " + produceInterval + " milliseconds");
            try {
                Thread.sleep(produceInterval);
                new Bus().start();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }


    }
}
