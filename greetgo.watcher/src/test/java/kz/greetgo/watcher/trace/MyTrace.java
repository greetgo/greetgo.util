package kz.greetgo.watcher.trace;

public class MyTrace extends TopTrace {
  
  public MyTrace() {
    super(new LinkedMessageOuter());
    LinkedMessageOuter asd = (LinkedMessageOuter)outer;
    asd.outDir = "build/out/mytrace";
    asd.extention = ".mylog";
    asd.indexLength = 4;
    asd.maxFilesCount = 3000;
    asd.maxFileSize = 10000000;
    asd.prefix = "wow";
  }
  
  public void info(String message) {
    StringBuilder sb = new StringBuilder();
    appendTime(sb);
    appendProcessId(sb);
    appendThreadID(sb);
    appendTraceID(sb);
    appendHost(sb);
    append(sb, "INFO ###", message);
    out(sb);
  }
}
