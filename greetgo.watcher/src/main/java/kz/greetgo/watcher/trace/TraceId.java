package kz.greetgo.watcher.trace;

import java.util.UUID;

public final class TraceId {
  public final UUID run;
  public final long number;
  
  public TraceId(UUID runId, long number) {
    this.run = runId;
    this.number = number;
  }
  
  @Override
  public String toString() {
    String runStr = Long.toHexString(run.getLeastSignificantBits()).toUpperCase();
    
    return String.format("TRACE-%s-%08d", runStr, number);
  }
  
}
