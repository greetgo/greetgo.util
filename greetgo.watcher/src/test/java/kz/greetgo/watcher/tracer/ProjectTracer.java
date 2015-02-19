package kz.greetgo.watcher.tracer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import kz.greetgo.watcher.io.RotateWriter;
import kz.greetgo.watcher.io.Util;

public abstract class ProjectTracer<T extends Event> extends Tracer<T> {
  private static final long DEFAULT_MAX_FILE_LENGTH = 0;
  private static final File DEFAULT_DIR = new File("build/trace");
  static {
    DEFAULT_DIR.mkdirs();
  }
  
  public static final TraceStamp STAMP = new TraceStamp();
  
  protected static final Writer defaultWriter(String prefix) {
    try {
      return new RotateWriter(DEFAULT_MAX_FILE_LENGTH, Util.fileSequence(DEFAULT_DIR, prefix,
          ".log", 1, 10));
    } catch (UnsupportedEncodingException e) {
      throw new Error(e);
    }
  }
}
