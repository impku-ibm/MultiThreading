package MultithreadingInJava;

import java.util.ArrayList;
import java.util.List;

/**
 * This class demonstrates the usage of Virtual Threads in Java (introduced in JDK 21).
 * Virtual Threads are lightweight threads that are designed for concurrent applications
 * that require a large number of threads. They are also known as "carrier threads" or
 * "fibers" in some other programming languages.
 * 
 * Key benefits of Virtual Threads:
 * 1. Very lightweight (few bytes of memory vs. ~2MB for platform threads)
 * 2. Efficient scheduling using the ForkJoinPool
 * 3. Ideal for I/O-bound applications
 * 4. Can create millions of virtual threads without system resources issues
 */
public class VirtualThreadExample {
    public static void main(String[] args){
           // Creating 100,000 threads - this would be impractical with platform threads
           // but is easily handled by virtual threads
           final int numberofthreads=100000;
    
    // List to keep track of all created threads
    List<Thread> threadList=new ArrayList<>();

    // Define the task that each thread will execute
    // This simulates an I/O-bound operation (like making API calls)
    Runnable task =()->{
       System.out.println("Making API calls.");
       try {
          // Simulate I/O operation with a 1-second delay
          // Virtual threads are optimized for such blocking operations
          // When a virtual thread blocks, it doesn't block the carrier thread
          Thread.sleep(1000);
       }
       catch (InterruptedException e) {
          throw new RuntimeException(e);
       }
       System.out.println("Api call completed");
    };


      // Create and start 100,000 virtual threads
      for(int i=0;i<numberofthreads;i++){
         // Traditional platform thread creation (commented out)
         // This would likely cause OutOfMemoryError with 100,000 threads
         //  Thread thread=new Thread(task);

         // Create a virtual thread using the factory method
         // Virtual threads are much more lightweight than platform threads
         // They are mounted on carrier threads from the ForkJoinPool when they need to run
         Thread thread=Thread.ofVirtual().unstarted(task);
         
         // Set as daemon thread - will not prevent JVM shutdown
         thread.setDaemon(true);
         
         // Give each thread a unique name for identification
         thread.setName("Thread"+i);

          // Start the virtual thread
          // The thread will be scheduled on the ForkJoinPool when it needs to run
          thread.start();
          System.out.println("Thread Number is:- " + i);
          
          // Keep track of the thread in our list
          threadList.add(thread);
      }
      // Note: At this point, we've created 100,000 virtual threads
      // This would be impossible with platform threads on most systems
      // but is easily handled by virtual threads due to their lightweight nature
    }
}
