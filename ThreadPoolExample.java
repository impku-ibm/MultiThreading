package MultithreadingInJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class demonstrates different types of Thread Pools in Java using ExecutorService.
 * Thread pools manage a pool of worker threads and provide a way to decouple task submission
 * from task execution.
 * 
 * Benefits of Thread Pools:
 * 1. Better resource management
 * 2. Enhanced performance by reducing thread creation overhead
 * 3. Controlled concurrent execution
 * 4. Task queuing and management
 * 
 * This example demonstrates four types of thread pools:
 * 1. Single Thread Executor - Uses a single worker thread
 * 2. Fixed Thread Pool - Uses a fixed number of threads
 * 3. Cached Thread Pool - Creates new threads as needed, reuses idle ones
 * 4. Scheduled Thread Pool - Can schedule tasks with delays or periodic execution
 */
public class ThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
      // 1. Single Thread Executor
      // - Uses a single worker thread with an unbounded queue
      // - Guarantees sequential execution of tasks
      // - Useful for scenarios requiring sequential task processing
      ExecutorService executorService= Executors.newSingleThreadExecutor();
      for(int i=1;i<=5;i++){
         final int taskId=i;
         // Submit task to the executor
         executorService.execute(()->{
            System.out.println("Single Thread Task " + taskId + "executed by thread "  + Thread.currentThread().getName());
         });
      }
      executorService.shutdown();

      // 2. Fixed Thread Pool
      // - Creates a thread pool with a fixed number of threads (3 in this case)
      // - If all threads are busy, new tasks wait in queue
      // - Good for limiting resource usage and when you know optimal thread count
      ExecutorService executorService1= Executors.newFixedThreadPool(3);
      for(int i=1;i<=5;i++){
         final int taskId=i;
         // Tasks will be distributed among the 3 threads
         executorService1.execute(()->{
            System.out.println("Fixed Thread Task " + taskId + "executed by thread "  + Thread.currentThread().getName());
         });
      }
      executorService1.shutdown();

   // 3. Cached Thread Pool
   // - Creates new threads as needed and reuses idle ones
   // - Threads that remain idle for 60 seconds are terminated
   // - Good for many short-lived asynchronous tasks
   ExecutorService executorService2=Executors.newCachedThreadPool();
   for(int i=1;i<=5;i++){
      final int task=i;
      // Each task might be executed by a new thread if all existing ones are busy
      executorService2.execute(()->{
         System.out.println("Cached thread Pool task " + task + " executed by thread " + Thread.currentThread().getName());
      });
   }
   executorService2.shutdown();

   // 4. Scheduled Thread Pool
   // - Designed for scheduling tasks to run after a delay or periodically
   // - Creates a thread pool with specified number of threads (3 here)
   // - Useful for tasks that need to run at specific times or intervals
   ExecutorService executorService3=Executors.newScheduledThreadPool(3);
   for (int i=1;i<=5;i++){
      final int task=i;
      // In this example, we're just executing tasks immediately
      // Can also use schedule(), scheduleAtFixedRate(), or scheduleWithFixedDelay()
      executorService3.execute(()->{
         System.out.println("Scheduled thread Pool task " + task + " executed by thread " + Thread.currentThread().getName());
      });
   }
   // Initiate an orderly shutdown of the executor
   // - No new tasks will be accepted
   // - Previously submitted tasks will be executed
   executorService3.shutdown();
   }
    
   // Note: Each type of thread pool has its own use case:
   // - SingleThreadExecutor: Sequential task processing
   // - FixedThreadPool: Limited resources, known workload
   // - CachedThreadPool: Many short-lived tasks, dynamic workload
   // - ScheduledThreadPool: Delayed or periodic task execution
}
