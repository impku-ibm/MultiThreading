package MultithreadingInJava;
public class VisibilityProblem {

    //By using volatile keyword we can resolve the visibility problem.
private volatile boolean flag;


  // Getter
    public boolean isFlag() {
        return flag;
    }
    // Setter
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public static void main(String[] args){
        VisibilityProblem vsb= new VisibilityProblem();
        Thread t1 = new Thread(
            () -> {
           try{
            Thread.sleep(1000);
           }catch(InterruptedException  ex){
            
           }
            vsb.setFlag(true);
            
        });

      Thread t2 = new Thread(()->{
         while(!vsb.isFlag()){
         
         }
         System.out.println("Thread 2 is now completed.");
      });

        t1.start();
        t2.start();
    }
    
}
