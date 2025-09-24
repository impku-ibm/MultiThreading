package MultithreadingInJava;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 🎯 QUICK INTERVIEW GUIDE: ReadWriteLock Pattern
 * 
 * 1. What is ReadWriteLock?
 *    - Advanced locking mechanism providing two locks:
 *      a) Read Lock: Multiple threads can read simultaneously
 *      b) Write Lock: Exclusive access for writing
 * 
 * 2. Key Benefits:
 *    - Better concurrency than simple locks
 *    - Multiple readers can access simultaneously
 *    - Write operations get exclusive access
 *    - Prevents read-write and write-write conflicts
 * 
 * 3. Rules of ReadWriteLock:
 *    - Multiple threads can hold read lock
 *    - Only one thread can hold write lock
 *    - If write lock is held, no read locks allowed
 *    - If any read lock is held, no write lock allowed
 * 
 * 4. Use Cases:
 *    - Cache implementations
 *    - Read-heavy scenarios
 *    - Data structures with frequent reads
 *    - When reads outnumber writes
 * 
 * 🚨 Common Interview Questions:
 * Q1: When to use ReadWriteLock over ReentrantLock?
 * A1: When reads are more frequent than writes and concurrent reads are safe
 * 
 * Q2: Can multiple threads read simultaneously?
 * A2: Yes, multiple threads can hold read lock at same time
 * 
 * Q3: What happens during write lock?
 * A3: All other operations (read/write) are blocked
 * 
 * Q4: Performance implications?
 * A4: Better than simple locks for read-heavy scenarios
 */
public class ReadWriteLockExample {

   /**
    * Shared resource that needs read-write protection
    * - Reads: Multiple threads can read simultaneously
    * - Writes: Only one thread can write at a time
    */
   int count = 0;

   /**
    * Core ReadWriteLock Components:
    * 
    * 1. ReadWriteLock: Main interface
    * 2. readLock: For concurrent read operations
    * 3. writeLock: For exclusive write operations
    */
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private final Lock readLock = lock.readLock();    // Multiple threads can hold this
   private final Lock writeLock = lock.writeLock();  // Only one thread can hold this

   /**
    * Read Operation Pattern
    * - Multiple threads can read simultaneously
    * - Uses readLock for thread-safe reading
    * - Demonstrates proper lock/unlock pattern
    * 
    * Interview Tip: Notice the try-finally pattern
    * ensuring lock release even if exception occurs
    */
   public int readCount() {
      readLock.lock();  // Multiple threads can acquire this lock
      try {
         return count;  // Safe to read: protected by readLock
      } finally {
         readLock.unlock();  // Always release lock in finally
      }
   }

   /**
    * Write Operation Pattern
    * - Only one thread can write at a time
    * - Uses writeLock for exclusive access
    * - Blocks both readers and writers
    * 
    * Interview Tip: Write operations need exclusive access
    * to maintain data consistency
    */
   public int writeCount() {
      writeLock.lock();  // Exclusive lock: blocks all other operations
      try {
         count++;  // Safe to modify: we have exclusive access
         return count;
      } finally {
         writeLock.unlock();  // Always release lock in finally
      }
   }

   /**
    * Demonstration of ReadWriteLock Usage
    * 
    * This example shows:
    * 1. Multiple readers working concurrently
    * 2. Exclusive writer access
    * 3. Thread interaction patterns
    * 4. Lock acquisition and release
    * 
    * Interview Tips:
    * - Observe how multiple readers can work together
    * - Notice how writer gets exclusive access
    * - Watch for proper lock handling
    */
    public static void main(String[] args) throws InterruptedException {
      ReadWriteLockExample readWriteLockExample = new ReadWriteLockExample();

      // Reader task: Multiple threads can execute this simultaneously
      Runnable read = () -> {
         for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+" is reading : " +readWriteLockExample.readCount());
         }
      };

      // Writer task: Only one thread can execute this at a time
      Runnable write = () -> {
         for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+" is writing : " + readWriteLockExample.writeCount());
         }
      };

      // Create one writer and two reader threads
      Thread t2 = new Thread(write, "Writer-Pradeep");  // Single writer
      Thread t1 = new Thread(read, "Reader-Pankaj");    // First reader
      Thread t3 = new Thread(read, "Reader-Shyam");     // Second reader

      // Start threads with delays to observe behavior
      t1.start();
      Thread.sleep(50);  // Small delay to observe thread interaction
      t2.start();
      Thread.sleep(50);  // Delay between operations
      t3.start();
      Thread.sleep(50);  // Delay for observation

      // Wait for all threads to complete
      t1.join();
      t2.join();
      t3.join();

      // Check final result
      System.out.println("Final count is :- " + readWriteLockExample.count);
   }
}