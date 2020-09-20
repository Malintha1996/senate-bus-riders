package senatebusridersproblem;

import java.util.logging.Logger;
/**
 * Rider..
 *
 * There is no constraint in the problem saying the early arrivals must be given priority.
 * Therefore, if some rider has entered the boarding(waiting) area before the bus arrives
 * he has an equal chance as much as the first rider to arrive in the waiting area.
 */

public class Rider extends Thread {
    private static Logger logger = Logger.getLogger(Rider.class.getName());

    @Override
    public void run() {
        runImpl();
    }

    public void runImpl() {
        try {
            //Enter the boarding area,one by one
            logger.info("Rider " + this.getId() +" waiting to enter the boarding/waiting area");
            SenateBusRiders.SHARED_RESOURCES.getMutex().acquire();
            logger.info("Rider " + this.getId() +" entered the boarding/waiting area");
            //Increment the waitingRidersCount by 1
            SenateBusRiders.SHARED_RESOURCES.getWaitingRidersCount().incrementAndGet();
            //Release the "mutex", allowing others Riders to enter
            SenateBusRiders.SHARED_RESOURCES.getMutex().release();
            //System.out.println("Riders mutex released by Rider " + this.getId());
            //Wait till a Bus releases the Bus semaphore.
            SenateBusRiders.SHARED_RESOURCES.getBus().acquire();
            //board bus after acquiring the Bus semaphore.
            boardBus();
            //Notify bus (release) that the Rider has boarded. (Allow others to board)
            SenateBusRiders.SHARED_RESOURCES.getBoarded().release();
            //System.out.println("Boarded semaphore released by Rider " + this.getId());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
    void boardBus() {
        logger.info("Rider " + this.getId()+ " boards the bus");
    }
}
