/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/


import java.util.Random;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Philosopher extends Thread {
  private Chopstick left, right;
  private Random random;
  private int thinkCount;

  public Philosopher(Chopstick left, Chopstick right) {
    this.left = left; this.right = right;
    random = new Random();
  }

  public void run() {
    try {
      DateTimeFormatter f = DateTimeFormatter.ofPattern("mm:ss");
      while(true) {
        ++thinkCount;
        if (thinkCount % 10 == 0) {
          System.out.println(LocalTime.now());
          System.out.format("Philosopher %s has thought %s times at %s\n", this, thinkCount, f.format(LocalTime.now()));
        }
        Thread.sleep(random.nextInt(50));     // Think for a while
        synchronized(left) {                    // Grab left chopstick
          synchronized(right) {                 // Grab right chopstick
            Thread.sleep(random.nextInt(50)); // Eat for a while
          }
        }
      }
    } catch(InterruptedException e) {}
  }
}
