package kz.greetgo.watcher.outer;

import kz.greetgo.watcher.trace.LinkedMessageOuter;

public class MessageOuterTest {
  public static void main(String[] args) throws Exception {
    LinkedMessageOuter outer = new LinkedMessageOuter();
    outer.outDir = "build/out/traces";
    
    for (int i = 0; i < 10000; i++) {
      outer.outMessage("Message " + i);
    }
    
    Thread.sleep(10);
    
    outer.close();
    System.out.println("Finish");
  }
}
