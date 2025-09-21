package MultithreadingInJava;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 🎯 QUICK INTERVIEW GUIDE: Lock Interface vs synchronized
 * 
 * 1. What is Lock Interface?
 *    - More flexible alternative to synchronized keyword
 *    - Part of java.util.concurrent.locks package
 *    - Provides explicit locking operations
 * 
 * 2. Why use Lock over synchronized?
 *    a) Timeout capability: Can specify how long to wait for lock
 *    b) TryLock: Attempt to get lock without blocking
 *    c) Interruptible locking: Can interrupt thread waiting for lock
 *    d) Fair locking: First-come-first-served policy possible
 *    e) Multiple conditions: Can have different wait-sets
 * 
 * 3. Key Methods of Lock:
 *    - lock(): Acquire the lock, wait if needed
 *    - tryLock(): Try to get lock, return false if can't
 *    - tryLock(time): Try for specified time only
 *    - unlock(): Must be called in finally block
 * 
 * 4. ReentrantLock Features:
 *    - Same thread can lock multiple times
 *    - Must unlock same number of times
 *    - Can be fair or unfair (constructor parameter)
 * 
 * 5. Best Practices:
 *    - Always use in try-finally block
 *    - Declare as private final
 *    - Release lock in finally block
 *    - Consider using tryLock with timeout
 * 
 * 🚨 Common Interview Questions:
 * Q1: Difference between Lock and synchronized?
 * A1: Lock is more flexible, has timeout, can be interrupted
 * 
 * Q2: What is ReentrantLock?
 * A2: Lock implementation that can be acquired multiple times by same thread
 * 
 * Q3: Why unlock in finally?
 * A3: To guarantee lock release even if exception occurs
 */
public class BasicLockExampleInPlaceOfSyncronized {
     public static void main(String[] args) {
      SbiAccount sbiAccount = new SbiAccount();

      Runnable task = () -> sbiAccount.withdrawAmount(500);

      Thread t1 = new Thread(task, "Pankaj ");
      Thread t2 = new Thread(task, "Pradeep");
      Thread t3 = new Thread(task, "Shyam");
      t1.start();
      t2.start();
      t3.start();
   }
}

/**
 * Represents a bank account with thread-safe withdrawal operations using ReentrantLock.
 * This implementation shows advanced locking features not available with synchronized keyword.
 */
/**
 * 📝 PRACTICAL IMPLEMENTATION EXAMPLE
 * 
 * Key Implementation Points:
 * 1. Lock Declaration: Always private final
 * 2. Resource Protection: Guards 'balance' variable
 * 3. Timeout Usage: Prevents indefinite waiting
 * 4. Error Handling: Proper interrupt handling
 * 
 * Interview Tip: This example demonstrates:
 * - Proper lock usage pattern
 * - Thread safety implementation
 * - Timeout handling
 * - Exception safety
 */
class SbiAccount {
   private int balance = 1000;  // Protected resource
   
   // Best Practice: Declare lock as private final
   private final Lock lock = new ReentrantLock();

   /**
    * 🔒 Thread-Safe Withdrawal Implementation
    * 
    * Pattern Breakdown:
    * 1. Try to acquire lock with timeout
    * 2. Check and modify protected resource
    * 3. Ensure lock release in finally
    * 4. Handle timeout and interruption
    * 
    * Interview Tips:
    * - Demonstrates proper lock lifecycle
    * - Shows timeout handling
    * - Illustrates critical section protection
    */
   public void withdrawAmount(int amount) {
      try {
         // Try to get lock with 1-second timeout
         if (lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
            if (balance >= amount) {
               System.out.println("Thread " + Thread.currentThread().getName() + " request to Withdraw $ " + amount);
               try {
                  System.out.println("Thread :- " + Thread.currentThread().getName() + " Withdrawal in progress.....");
                  Thread.sleep(2000);
               }
               catch (InterruptedException ignored) {
                  Thread.currentThread().interrupt();
               }
               finally {
                  lock.unlock();
               }
               System.out.println("Completed withdrawal by thread " + Thread.currentThread().getName());
               balance -= amount;
               System.out.println("Reamining balance is " + balance);
            }
            else {
               System.out.println("Insufficinet Balance.....");
            }
         }
         else {
            System.out.println("Thread " + Thread.currentThread().getName() + " failed to acquire lock. Please try again later...");
         }
      }
      catch (Exception e) {
         Thread.currentThread().interrupt();
      }

   }
}