package senatebusridersproblem;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Follows, 'I'll do it for you approach"
 */
public class SharedResources {
    //Number of riders in the boarding area
    private final AtomicInteger waitingRidersCount = new AtomicInteger(0);
    //Protects waiting riders
    private final Semaphore mutex = new Semaphore(1);
    //Signals when the bus has arrived
    private final Semaphore bus = new Semaphore(0);
    //Signals that a rider has boarded.
    private final Semaphore boarded = new Semaphore(0);

    public AtomicInteger getWaitingRidersCount() {
        return waitingRidersCount;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public Semaphore getBus() {
        return bus;
    }

    public Semaphore getBoarded() {
        return boarded;
    }
}
