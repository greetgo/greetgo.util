package kz.greetgo.watcher.trace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class RotatingWriterTest {
  public static void main(String[] args) throws FileNotFoundException {
    RotatingWriter writer = new RotatingWriter(new File("/home/den/tmp/rot"), "test", ".txt", 10,
        20);
    PrintWriter p = new PrintWriter(writer, true);
    p.println("Hello all 1");
    p.println("Hello all 2");
    p.println("Hello all 3");
    p.println("Hello all 4");
    p.close();
  }
}
