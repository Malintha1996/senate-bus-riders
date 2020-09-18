package senatebusridersproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Rider extends Thread {
    private static Logger logger = Logger.getLogger(BusFactory.class.getName());

    @Override
    public void run() {
        runImpl();
    }

    public void runImpl() {
        try {
            SenateBusRiders.sharedVariables.getMutex().acquire();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        SenateBusRiders.sharedVariables.getWaitingRidersCount().incrementAndGet();
        SenateBusRiders.sharedVariables.getMutex().release();
        System.out.println("Riders mutex released by Rider " + this.getId());
        try {
            /*
            Rider waits to aqcuire the bus semaphore to enter the bus.
            It has to wait till Bus signals to acquire the lock.
            */
            SenateBusRiders.sharedVariables.getBus().acquire();
            System.out.println("Bus semaphore acquired by a Rider");

        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        System.out.println(this.getId()+" Rider boards bus");
        SenateBusRiders.sharedVariables.getBoarded().release();
        /*
        Rider signals that he has boarded
        */
        System.out.println("Boarded semaphore released by Rider " +this.getId());

    }
}
