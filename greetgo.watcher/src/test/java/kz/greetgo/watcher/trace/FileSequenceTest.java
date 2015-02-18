package kz.greetgo.watcher.trace;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FileSequenceTest {
  private static final Iterable<File> FILES = FileSequence.fileSequence(new File("build/tmp"), "p",
      ".txt", 9, 11);
  private static final File FILE9 = new File("build/tmp/p09.txt");
  private static final File FILE10 = new File("build/tmp/p10.txt");
  private static final File FILE11 = new File("build/tmp/p11.txt");
  
  @BeforeTest
  public void prepare() {
    new File("build/tmp").mkdirs();
  }
  
  @Test
  public void iterator() {
    assertThat(FILES).containsExactly(FILE9, FILE10, FILE11);
  }
  
  @Test
  public void rotate() throws IOException {
    for (File file : FILES) {
      file.createNewFile();
      PrintStream ps = new PrintStream(file);
      ps.print(file.getName());
      ps.close();
    }
    FileSequence.rotate(FILES);
    Iterator<File> it = FILES.iterator();
    assertThat(it.next()).doesNotExist();
    assertThat(it.next()).exists().hasContent("p09.txt");
    assertThat(it.next()).exists().hasContent("p10.txt");
  }
  
  @Test
  public void rotate_hole() throws IOException {
    for (File file : FILES) {
      file.createNewFile();
      PrintStream ps = new PrintStream(file);
      ps.print(file.getName());
      ps.close();
    }
    FILE10.delete();
    FileSequence.rotate(FILES);
    Iterator<File> it = FILES.iterator();
    assertThat(it.next()).doesNotExist();
    assertThat(it.next()).exists().hasContent("p09.txt");
    assertThat(it.next()).exists().hasContent("p11.txt");
  }
  
  public static void main(String[] args) throws IOException {
    //    RotatingWriter writer = new RotatingWriter(new File("/home/den/tmp/rot"), "test", ".txt", 10,
    //        20);
    //    PrintWriter p = new PrintWriter(writer, true);
    //    p.println("Hello all 1");
    //    p.println("Hello all 2");
    //    p.println("Hello all 3");
    //    p.println("Hello all 4");
    //    p.close();
    //    
    //    FilterOutputStream f;
  }
}
