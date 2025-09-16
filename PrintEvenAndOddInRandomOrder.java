package MultithreadingInJava;

public class PrintEvenAndOddInRandomOrder {

    public static void main(String[] args) {
        Thread t1=new Thread(()->{
            for(int i=0;i<=20;i+=2){
                System.out.println(i+" ");
            }
        });

        Thread t2=new Thread(()->{
            for(int i=1;i<=20;i+=2){
                System.out.println(i+" ");
            }
        });

        t1.setName("Even");
        t2.setName("Odd");
         t1.start();
         t2.start();
    }
    
}