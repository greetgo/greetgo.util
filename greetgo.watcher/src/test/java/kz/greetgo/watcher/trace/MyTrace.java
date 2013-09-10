package kz.greetgo.watcher.trace;

public class MyTrace extends TopTrace {
  
  public MyTrace() {
    super(new LinkedMessageOuter());
    LinkedMessageOuter asd = (LinkedMessageOuter)outer;
    asd.outDir = "out/trace";
    asd.extention = ".mylog";
    asd.indexLength = 4;
    asd.maxFilesCount = 30;
    asd.maxFileSize = 10000;
    asd.prefix = "wow";
  }
  
  public void info(String message) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendProcessId(sb);
    appendThreadID(sb);
    appendTraceID(sb);
    appendHost(sb);
    append(sb, "INFO", message);
    out(sb);
  }
}
