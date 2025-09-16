package MultithreadingInJava;

import java.util.concurrent.atomic.AtomicInteger;

//This issue of race condition can be fixed by using AtomicInteger .
class SharedCounter{
    //  private int counter;
    private AtomicInteger counter= new AtomicInteger(0);

    public int getCount(){
       // return counter;
       return counter.get();
    }

    public void increment(){
      // counter++;
      counter.incrementAndGet();
    }
}

public class RaceCondition {

    public static void main(String [] args) throws InterruptedException{
      SharedCounter count=new SharedCounter();
Thread t1=new Thread(()->{
        System.out.println("Thread 1 is started.");
     for(int i=0;i<50000;i++){
        count.increment();
     }
      });

      Thread t2= new Thread(()->{
        System.out.println("Thread 2 is started.");
        for(int i=0;i<50000;i++){
          count.increment();
        }
    });
     t1.start();
     t2.start();
     t1.join();
     t2.join();
     
    System.out.println(count.getCount());

    }
   

  
}
