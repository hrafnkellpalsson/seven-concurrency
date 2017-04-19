public class Counting {
  public static void main(String[] args) throws InterruptedException {
    class Counter {
      int counter = 0;
      public synchronized void increment() { ++counter; }
      public synchronized int getCount() { return counter; }
    }
    Counter counter = new Counter();

    Runnable run = () -> {
      for (int i = 0; i < 10000; i++) {
        counter.increment(); // Accessing captured variable
      }
    };
    Thread t1 = new Thread(run);
    Thread t2 = new Thread(run);

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(counter.getCount());
  }
}
