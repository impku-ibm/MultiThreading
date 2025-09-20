package MultithreadingInJava;

/**
 * This class demonstrates the usage of ThreadLocal in Java.
 * ThreadLocal provides thread-local variables, which means each thread that accesses
 * a ThreadLocal variable has its own, independently initialized copy of the variable.
 */
public class ThreadLocalExample {
    public static void main(String[] args){
        // Create a ThreadLocal instance that will store Long values
        // Each thread will get its own copy of this variable
        ThreadLocal<Long> threadLocal=new ThreadLocal<>();

      // Sample user IDs to demonstrate thread-local storage
      Long userId1=12345L;
      Long userId2=123346762L;

      // Create first thread that will work with userId1
      Thread t1=new Thread(()->{
         System.out.println("Thread 1 is getting started.");
         // Set the thread-local value for Thread 1
         // This value is only accessible by Thread 1
         threadLocal.set(userId1);
         System.out.println("Completed the task for " + userId1);
         // Demonstrate that get() returns Thread 1's own copy of the value
         System.out.println("Completed task for " + threadLocal.get());
         System.out.println("Completed task for " + threadLocal.get());
         // Clean up the thread-local variable to prevent memory leaks
         threadLocal.remove();
         System.out.println("ThreadLocal removed the userId "  + userId1);

      });

      // Create second thread that will work with userId2
      Thread t2= new Thread(()->{
         System.out.println("Thread 2 is getting started.");
         // Set the thread-local value for Thread 2
         // This value is completely independent of Thread 1's value
         threadLocal.set(userId2);
         System.out.println("Completed task for " + userId2);
         // Demonstrate that Thread 2 gets its own copy of the value
         System.out.println("Completed task for " + threadLocal.get());
         // Clean up thread-local variable
         threadLocal.remove();
         // After removal, get() will return null
         System.out.println("Thread Local removed userid 2 " + threadLocal.get());
      });
      // Start both threads
      // Each thread will maintain its own copy of the userId in threadLocal
      t1.start();
      t2.start();

      // Note: Even though both threads use the same threadLocal variable,
      // they each get their own independent copy of the value
    }
}
