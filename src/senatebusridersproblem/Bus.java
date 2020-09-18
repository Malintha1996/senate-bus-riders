package senatebusridersproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bus extends Thread {
    private static Logger logger = Logger.getLogger(Bus.class.getName());
    private final int SEAT_LIMIT = 50;

    @Override
    public void run() {
        this.runImpl();
    }

    public void runImpl() {
        try {
            SenateBusRiders.sharedVariables.getMutex().acquire();    //Bus waits to acquire riders mutex so that it can start boarding
            //Riders cannot enter the bus stop, till the bus releases lock
            System.out.println("Riders Queue mutex aquired by Bus "  +this.getId());
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        int boardingRidersCount = Math.min(SenateBusRiders.sharedVariables.getWaitingRidersCount().get(), SEAT_LIMIT);
        System.out.format("\nRiders count in the Queue: %d\n",SenateBusRiders.sharedVariables.getWaitingRidersCount().get());
        System.out.format("\nRiders count who can board: %d\n", boardingRidersCount);

        for(int i=0; i < boardingRidersCount; i++){
            SenateBusRiders.sharedVariables.getBus().release();      //signals each rider that they can get into the bus
            System.out.println("Bus semaphore released by Bus " +this.getId());
            try {
                SenateBusRiders.sharedVariables.getBoarded().acquire();
                /*
                Bus aquires boarded semaphore to say rider has boarded
                */
                System.out.println("Boarded semaphore aquired by Bus " +this.getId());
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
         /*
        Updates the waiting riders count and relase the lock so that passengers can come to the bus stop.
        Then the bus is fine to depart
        */
        int n = SenateBusRiders.sharedVariables.getWaitingRidersCount().get();
        SenateBusRiders.sharedVariables.getWaitingRidersCount().set(Math.max(n - 50, 0));
        SenateBusRiders.sharedVariables.getMutex().release();

        System.out.println("Riders Queue mutex released by Bus "+this.getId());
        System.out.println("Bus ["+this.getId()+"] Departs ");
    }
}
