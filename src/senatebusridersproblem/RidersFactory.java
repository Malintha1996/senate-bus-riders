package senatebusridersproblem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RidersFactory extends Thread {
   private static Logger logger = Logger.getLogger(RidersFactory.class.getName());

   @Override
   public void run() {
      produce();
   }

   public void produce() {
      long produceInterval = 0;
      while(true) {
         produceInterval = (long) ( -(Math.log(Math.random()))*SenateBusRiders.RIDER_ARRIVAL_TIME_MEAN);
         System.out.println("Next rider arrives in " + produceInterval + " milliseconds");
         try {
            Rider.sleep(produceInterval);
            new Rider().start();
         } catch (InterruptedException e) {
            logger.log(Level.SEVERE,e.getMessage());
         }
      }
   }
}
