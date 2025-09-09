package MultithreadingInJava;

public class DeadlockExample {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized(lock1) {
                System.out.println("Thread 1 is holding lock 1...");
                try {
                    Thread.sleep(1000);
                    System.out.println("Waiting for lock2 to be released");
                    synchronized(lock2) {
                        System.out.println("Thread 1 is holding lock 2...");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t2=new Thread(() ->{
       synchronized(lock2){
        System.out.println("Thread 2 is holding lock 2...");
        try {
            Thread.sleep(1000);
            System.out.println("Waiting for lock1 to be released");
            synchronized(lock1){
                System.out.println("Thread 2 is holding lock 1...");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       }
        });
        t1.setName("Thread 1");
        t2.setName("Thread 2");
        t1.start();
        t2.start();
    
    }
    
}
