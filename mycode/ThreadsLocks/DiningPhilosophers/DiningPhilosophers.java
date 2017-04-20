/***
 * Excerpted from "Seven Concurrency Models in Seven Weeks",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/pb7con for more book information.
***/

public class DiningPhilosophers {

  public static void main(String[] args) throws InterruptedException {
    final int nPhilosophers = 5;

    Philosopher[] philosophers = new Philosopher[nPhilosophers];
    Chopstick[] chopsticks = new Chopstick[nPhilosophers];

    for (int i = 0; i < nPhilosophers; ++i)
      chopsticks[i] = new Chopstick(i);
    for (int i = 0; i < nPhilosophers; ++i) {
      philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % nPhilosophers], i);
      philosophers[i].start();
    }
    for (int i = 0; i < nPhilosophers; ++i)
      philosophers[i].join();
  }
}
