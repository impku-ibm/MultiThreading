package MultithreadingInJava;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockFairness {
      //Unfair lock
  // Lock lock = new ReentrantLock();

   //Fair Lock
   Lock lock=new ReentrantLock(true);

   public void accessResource(){
      lock.lock();
      try{
         System.out.println(Thread.currentThread().getName() + " aquiring the lock");
         try {
            Thread.sleep(1000);
         }catch (Exception e){
            Thread.currentThread().interrupt();
         }
      }finally {
         System.out.println(Thread.currentThread().getName() + "  unlocking the lock.");
         lock.unlock();
      }
   }

   public static void main(String[] args) {
      LockFairness reentrantLockExample = new LockFairness();
    Runnable task= reentrantLockExample::accessResource;

      Thread t1 = new Thread(task, "Pankaj ");
      Thread t2 = new Thread(task, "Pradeep");
      Thread t3 = new Thread(task, "Shyam");
      t1.start();
      t2.start();
      t3.start();
   }
}
