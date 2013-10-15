package kz.greetgo.watcher.trace;

public class AbstractTraceTest {
  public static void main(String[] args) throws Exception {
    final MyTrace trace = new MyTrace();
    
    final int Nthreads = 1000;
    final int Nlines = 1000;
    
    for (int t = 1; t <= Nthreads; t++) {
      final String T = "T" + t;
      final int finalT = t - 1;
      new Thread(new Runnable() {
        @Override
        public void run() {
          for (int i = 1; i <= Nlines; i++) {
            trace
                .info("Message THREAD " + T + " -- Line " + i + " ## SUM " + (finalT * Nlines + i));
            
            if (i == Nlines / 2) {
              try {
                Thread.sleep(100);
              } catch (InterruptedException e) {}
            }
            
          }
          
          System.out.println("End thread " + T);
        }
      }).start();
    }
    
    Thread.sleep(10);
    
    trace.info("Hello");
    
    trace.close();
    
    System.out.println("\n\n\n----------------------------------------> Completed\n\n\n");
  }
}
