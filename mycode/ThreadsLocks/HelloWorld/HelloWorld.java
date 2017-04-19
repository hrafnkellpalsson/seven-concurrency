public class HelloWorld {
  public static void main(String[] args) throws InterruptedException {
    Runnable myRunnable = () -> { System.out.println("Hello from new thread"); };
    Thread myThread = new Thread(myRunnable);
    myThread.start();
    Thread.yield();
    // Thread.sleep(1);
    System.out.println("Hello from main thread");
    myThread.join();
  }
}
