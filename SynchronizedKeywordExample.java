package MultithreadingInJava;

/**
 * This class demonstrates the usage of the synchronized keyword in Java for thread safety.
 * It simulates a bank account scenario where multiple threads (customers) try to withdraw
 * money simultaneously from the same account.
 * 
 * Without synchronization, this could lead to:
 * 1. Race conditions
 * 2. Inconsistent balance updates
 * 3. Double withdrawals
 * 4. Other thread safety issues
 * 
 * The synchronized keyword ensures that only one thread can execute the critical section
 * (withdrawal operation) at a time, maintaining data consistency.
 */
public class SynchronizedKeywordExample {
      public static void main(String[] args) {
      // Create a single bank account instance that will be shared among threads
      SbiAccount sbiAccount = new SbiAccount();

      // Define the withdrawal task that all threads will execute
      // Each thread will attempt to withdraw $500
      Runnable task = () -> sbiAccount.withdrawAmount(500);

      // Create three threads representing different customers
      // Each thread is given a name to identify which customer is performing the withdrawal
      Thread t1=new Thread(task,"Pankaj ");
      Thread t2=new Thread(task,"Pradeep");
      Thread t3=new Thread(task,"Shyam");
      // Start all three threads
      // They will compete for access to the synchronized withdrawAmount method
      t1.start();
      t2.start();
      t3.start();
   }
}

/**
 * Represents a bank account with thread-safe withdrawal operations.
 * This class demonstrates instance-level synchronization where the lock
 * is acquired on the instance of SbiAccount.
 */
class SbiAccount{
   // The account balance - this is the shared resource that needs protection
   private int balance=1000;

   /**
    * Thread-safe method to withdraw money from the account.
    * The synchronized keyword ensures that only one thread can execute this method at a time.
    * This prevents:
    * - Multiple simultaneous withdrawals
    * - Race conditions on the balance field
    * - Inconsistent account state
    */
   public synchronized void withdrawAmount(int amount){
      // Log the withdrawal request
      System.out.println("Thread " + Thread.currentThread().getName() + " request to Withdraw $ " + amount);

      // Check if sufficient balance exists
      // This check is thread-safe because it's within the synchronized block
      if(balance<amount){
         System.out.println("Insufficient balance....");
         return;
      }

      try {
         // Simulate processing time for the withdrawal
         // Even during this sleep, other threads cannot enter this method
         // because it's synchronized
         System.out.println("Thread :- " + Thread.currentThread().getName() +" Withdrawal in progress.....");
         Thread.sleep(2000);
      }
      catch (InterruptedException ignored) {
      }

      // Complete the withdrawal and update the balance
      // This operation is atomic because of synchronization
      System.out.println("Completed withdrawal by thread " + Thread.currentThread().getName());
      balance-=amount;
      System.out.println("Remaining balance is " + balance);
   }
}
