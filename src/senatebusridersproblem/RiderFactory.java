package senatebusridersproblem;

import java.util.logging.Logger;

public class RiderFactory extends Thread {
   private static Logger logger = Logger.getLogger(RiderFactory.class.getName());
   @Override
   public void run() {
      produce();
   }

   public void produce() {
      long produceInterval = 0;
      while(true) {
         produceInterval = (long) ( -(Math.log(Math.random()))*SenateBusRiders.RIDER_ARRIVAL_TIME_MEAN);
         logger.info("Next rider arrives in " + produceInterval + " milliseconds");
         try {
            Thread.sleep(produceInterval);
            new Rider().start();
         } catch (InterruptedException e) {
            System.out.println(e.getMessage());
         }
      }
   }
}
