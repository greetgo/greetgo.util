package kz.greetgo.watcher.tracer;

import static kz.greetgo.watcher.io.Util.fileSequence;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import kz.greetgo.watcher.io.RotateWriter;

public abstract class ProjectTracer {
  private static final long MAX_FILE_LENGTH = 60;
  private static final File DIR = new File("build/trace");
  static {
    DIR.mkdirs();
  }
  
  public static final Stamp STAMP = new Stamp();
  
  protected static final Writer defaultFileWriter(String prefix) {
    try {
      return new RotateWriter(MAX_FILE_LENGTH, fileSequence(DIR, prefix, ".log", 1, 10));
    } catch (UnsupportedEncodingException e) {
      throw new Error(e);
    }
  }
}
