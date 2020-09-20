package senatebusridersproblem;

import java.util.logging.Logger;

public class Bus extends Thread {
    private final int BOARDING_LIMIT = 50;
    private static Logger logger = Logger.getLogger(Bus.class.getName());

    @Override
    public void run() {
        this.runImpl();
    }

    public void runImpl() {
        try {
            /**
             * Acquire the "mutex" so that only riders that had already arrived
             * before the arrival of bus are able to board the bus.
             */
            SenateBusRiders.SHARED_RESOURCES.getMutex().acquire();
            logger.info("Bus " + this.getId() +" arrived");
            int boardingRidersCount = Math.min(SenateBusRiders.SHARED_RESOURCES.getWaitingRidersCount().get(), BOARDING_LIMIT);
            for (int i = 0; i < boardingRidersCount; i++) {
                //Signals each rider the boarding pass, now a rider who has  acquired the boarding pass(Bus semaphore) can board.
                //System.out.println("Bus semaphore released by Bus " + this.getId());
                SenateBusRiders.SHARED_RESOURCES.getBus().release();
                //Wait for the rider to release the boarded semaphore.
                SenateBusRiders.SHARED_RESOURCES.getBoarded().acquire();
                //System.out.println("Boarded semaphore acquired by Bus " + this.getId());
            }
            int n = SenateBusRiders.SHARED_RESOURCES.getWaitingRidersCount().get();
            //Set the remaining riders as waitingRidersCount after all have boarded.
            SenateBusRiders.SHARED_RESOURCES.getWaitingRidersCount().set(Math.max(n - 50, 0));
            //release the boarding area entering pass(mutex)
            SenateBusRiders.SHARED_RESOURCES.getMutex().release();
            //System.out.println("Boarding area mutex released by Bus " + this.getId());
            depart();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    void depart() {
        logger.info("Bus " + this.getId() + " departs");
    }
}
