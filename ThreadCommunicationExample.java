package MultithreadingInJava;

/**
 * 🎯 QUICK INTERVIEW GUIDE: Thread Communication in Java
 * 
 * 1. Core Concepts:
 *    - wait(): Releases lock and puts thread in waiting state
 *    - notify(): Wakes up one waiting thread
 *    - notifyAll(): Wakes up all waiting threads
 *    - synchronized: Required for wait/notify mechanism
 * 
 * 2. Producer-Consumer Pattern:
 *    - Classic thread communication example
 *    - Producer creates data, Consumer uses it
 *    - Synchronization prevents data corruption
 *    - Wait/notify ensures proper handoff
 * 
 * 3. Key Rules for wait/notify:
 *    - Must be called from synchronized context
 *    - Must own the monitor (lock) to call these methods
 *    - Always use wait() in while loop (not if)
 *    - Handle InterruptedException properly
 * 
 * 4. Common Interview Questions:
 * Q1: Why use while loop with wait()?
 * A1: Prevents spurious wakeups, ensures condition is still true
 * 
 * Q2: Difference between notify() and notifyAll()?
 * A2: notify() wakes one thread, notifyAll() wakes all threads
 * 
 * Q3: Why synchronized is required?
 * A3: wait/notify need monitor lock, prevents race conditions
 * 
 * Q4: Why check condition in while loop?
 * A4: Multiple threads might be waiting, condition might change
 * 
 * 🚨 Interview Tips:
 * 1. Always mention spurious wakeups
 * 2. Explain proper exception handling
 * 3. Discuss importance of synchronized
 * 4. Mention potential deadlock scenarios
 */
public class ThreadCommunicationExample {
     /**
      * Demonstrates Producer-Consumer pattern with thread communication
      * 
      * Implementation shows:
      * 1. Thread creation and coordination
      * 2. Data passing between threads
      * 3. Synchronized communication
      * 4. Wait/notify mechanism in action
      */
     public static void main(String[] args) {
      // Shared resource instance for thread communication
      SharedResource sharedResource = new SharedResource();

      // Producer lambda: Generates 10 values
      Runnable producer = () -> {
         for (int i = 0; i < 10; i++) {
            sharedResource.producer(i);  // Produce each value
         }
      };

      // Consumer lambda: Consumes 10 values
      Runnable consumer = () -> {
         for (int i = 0; i < 10; i++) {
            sharedResource.consumer();  // Consume each value
         }
      };
      Thread t1=new Thread(producer, "Producer ");
      Thread t2= new Thread(consumer," Cosumer ");
      t1.start();
      t2.start();
   }
}

/**
 * SharedResource implements the Producer-Consumer pattern
 * 
 * Key Components:
 * 1. Shared Data: Protected by synchronization
 * 2. State Flag: Indicates data availability
 * 3. Producer Method: Creates and stores data
 * 4. Consumer Method: Retrieves and processes data
 * 
 * Interview Focus Points:
 * - Thread safety mechanisms
 * - State management
 * - Proper synchronization
 * - Wait/notify pattern
 */
class SharedResource {
   // Shared data between producer and consumer
   private int data;

   // Flag to track data availability
   // true: data available to consume
   // false: space available to produce
   private boolean hasData;

   /**
    * Producer method: Creates and stores data
    * 
    * Key Points:
    * - Synchronized for thread safety
    * - Uses while loop for condition check
    * - Handles interruption properly
    * - Notifies waiting consumer
    */
   public synchronized void producer(int value) {
      // Wait while buffer is full (hasData is true)
      // IMPORTANT: Use while, not if, to handle spurious wakeups
      while (hasData) {
         try {
            // Release lock and wait for consumer
            wait();  // Always in synchronized context
         }
         catch (InterruptedException e) {
            // Properly restore interrupt status
            Thread.currentThread().interrupt();
         }
      }
      // Critical section: Produce data
      data = value;
      hasData = true;  // Mark data as available
      System.out.println(Thread.currentThread().getName() + " Produced " + value);
      
      // Wake up waiting consumer
      notify();  // Could use notifyAll() if multiple consumers
   }

   /**
    * Consumer method: Retrieves and processes data
    * 
    * Key Points:
    * - Synchronized to ensure thread safety
    * - Waits if no data available
    * - Proper interrupt handling
    * - Notifies waiting producer
    * 
    * Interview Tip: Notice the symmetry with producer method
    */
   public synchronized int consumer() {
      // Wait while buffer is empty (hasData is false)
      // while loop protects against spurious wakeups
      while (!hasData) {
         try {
            // Release lock and wait for producer
            wait();  // Always in synchronized context
         } catch (InterruptedException e) {
            // Restore interrupted status
            Thread.currentThread().interrupt();
         }
      }
      // Critical section: Consume data
      hasData = false;  // Mark data as consumed
      System.out.println(Thread.currentThread().getName() + " Consumed " + data);
      
      // Wake up waiting producer
      notify();  // Could use notifyAll() if multiple producers
      return data;
   }
}

