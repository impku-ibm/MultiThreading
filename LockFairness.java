package MultithreadingInJava;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 🎯 QUICK INTERVIEW GUIDE: Lock Fairness in ReentrantLock
 * 
 * 1. What is Lock Fairness?
 *    - Policy that determines which thread gets the lock when multiple threads are waiting
 *    - Fair Lock: First-come-first-served (FIFO ordering)
 *    - Unfair Lock: No guaranteed order (default behavior)
 * 
 * 2. Key Points for Interview:
 *    a) Fair Lock (new ReentrantLock(true)):
 *       - Threads acquire lock in the order they requested it
 *       - More predictable behavior
 *       - Lower throughput (10-20% slower)
 *       - Prevents thread starvation
 * 
 *    b) Unfair Lock (new ReentrantLock() or new ReentrantLock(false)):
 *       - No guaranteed order of lock acquisition
 *       - Better throughput
 *       - Possible thread starvation
 *       - Default behavior
 * 
 * 3. When to Use Fair Locks:
 *    - When thread starvation is a concern
 *    - Need predictable lock acquisition order
 *    - Can afford performance overhead
 * 
 * 4. When to Use Unfair Locks:
 *    - Need maximum throughput
 *    - Short lock holding times
 *    - Thread starvation isn't a concern
 * 
 * 🚨 Common Interview Questions:
 * Q1: Why is unfair locking default?
 * A1: Better performance, reduced thread suspension/resumption
 * 
 * Q2: Performance impact of fair locks?
 * A2: 10-20% slower due to strict ordering maintenance
 * 
 * Q3: Can fairness be changed after creation?
 * A3: No, must be set in constructor
 */
public class LockFairness {
   /**
    * Lock Creation Options:
    * 
    * 1. Unfair Lock (commented out):
    *    Lock lock = new ReentrantLock();
    *    - Default behavior
    *    - Better performance
    *    - No ordering guarantees
    */
   // Lock lock = new ReentrantLock();  // Unfair lock example

   /**
    * 2. Fair Lock (current implementation):
    *    - Guarantees FIFO ordering
    *    - Prevents thread starvation
    *    - Trade-off: Lower throughput
    */
   Lock lock = new ReentrantLock(true);  // Fair lock example

   /**
    * 🔒 Resource Access Pattern with Fair Lock
    * 
    * Implementation Points:
    * 1. lock() - With fair lock:
    *    - Threads queue up in arrival order
    *    - Earlier requests get priority
    * 
    * 2. Try-Finally Pattern:
    *    - Ensures lock release
    *    - Handles exceptions safely
    * 
    * 3. Sleep Simulation:
    *    - Demonstrates lock holding time
    *    - Shows thread ordering effects
    * 
    * Interview Tip: This pattern shows:
    * - Proper lock usage
    * - Thread safety
    * - Resource cleanup
    */
   public void accessResource(){
      lock.lock();  // Threads will queue in arrival order (fair)
      try{
         System.out.println(Thread.currentThread().getName() + " aquiring the lock");
         try {
            // Simulate work - observe thread ordering
            Thread.sleep(1000);
         }catch (Exception e){
            Thread.currentThread().interrupt();
         }
      }finally {
         System.out.println(Thread.currentThread().getName() + "  unlocking the lock.");
         lock.unlock();  // Always release in finally block
      }
   }

   /**
    * 🧪 Test Demonstration
    * 
    * Purpose:
    * - Demonstrates fair lock behavior with multiple threads
    * - Shows FIFO ordering of lock acquisition
    * - Illustrates thread queuing behavior
    * 
    * Expected Behavior:
    * - Threads should generally acquire lock in start order
    * - Observe predictable execution sequence
    * - Note: Exact ordering may vary slightly due to scheduling
    */
   public static void main(String[] args) {
      LockFairness reentrantLockExample = new LockFairness();
      // Method reference for clean, functional style
      Runnable task = reentrantLockExample::accessResource;

      // Create multiple threads to compete for the lock
      Thread t1 = new Thread(task, "Pankaj ");
      Thread t2 = new Thread(task, "Pradeep");
      Thread t3 = new Thread(task, "Shyam");
      t1.start();
      t2.start();
      t3.start();
   }
}
