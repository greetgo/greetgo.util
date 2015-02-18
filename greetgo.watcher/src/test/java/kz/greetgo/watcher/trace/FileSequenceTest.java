package kz.greetgo.watcher.trace;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
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
  
  @Test
  public void lazyOutputStream() throws IOException {
    for (File file : FILES) {
      file.delete();
    }
    
    LazyOutputStream los = new LazyOutputStream() {
      public OutputStream newOut() throws IOException {
        FileSequence.rotate(FILES);
        return new FileOutputStream(FILES.iterator().next());
      }
    };
    PrintStream ps = new PrintStream(los);
    ps.println("Hello 1");
    los.reset();
    ps.println("Hello 2");
    los.reset();
    ps.println("Hello 3");
    los.reset();
    ps.println("Hello 4");
    ps.close();
    
    Iterator<File> it = FILES.iterator();
    assertThat(it.next()).exists().hasContent("Hello 4");
    assertThat(it.next()).exists().hasContent("Hello 3");
    assertThat(it.next()).exists().hasContent("Hello 2");
  }
  
  @Test
  public void rotateWriter() throws IOException {
    for (File file : FILES) {
      file.delete();
    }
    RotateWriter rw = new RotateWriter(10, FILES);
    PrintWriter ps = new PrintWriter(rw);
    for (int i = 1; i <= 8; i++) {
      ps.println("Hello " + i);
    }
    ps.close();
    
    Iterator<File> it = FILES.iterator();
    assertThat(it.next()).exists().hasContent("Hello 7\nHello 8\n");
    assertThat(it.next()).exists().hasContent("Hello 5\nHello 6\n");
    assertThat(it.next()).exists().hasContent("Hello 3\nHello 4\n");
  }
}
