package DeadlockExample;

public class RemovingDeadlockUsingLockOrdering {
        
   private static final Object lock1 = new Object();
	 private static final Object lock2 = new Object();
	 
    public static void main(String[] args) {

	 Thread t1 = new Thread(()->{
	 synchronized(lock1){
	 System.out.println("Thread 1 is aquiring lock1.");
	 try{
	 Thread.sleep(1000);
	 }catch(InterruptedException ex){
	 throw new RuntimeException();
	 }
	 synchronized(lock2){
	 System.out.println("Thread 1 is now aquiring lock2.");
	 }
	 }
	 
	 });
	 
	 Thread t2 = new Thread(()->{
	 synchronized(lock2){
	 System.out.println("Thread 2 is aquiring lock1 .");
	 try{
	 Thread.sleep(1000);
	 }catch(InterruptedException ex){
       throw new RuntimeException();	 
	 }
	 synchronized(lock1){
	 System.out.println("Thread 2 is aquiring lock2.");}
	 }
	 });

     t1.setName("Thread 1");
     t2.setName("Thread 2");    
        t1.start();
        t2.start();
    }
    
}
