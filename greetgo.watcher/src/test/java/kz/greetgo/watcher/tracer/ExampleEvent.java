package kz.greetgo.watcher.tracer;

public class ExampleEvent extends ProjectEvent {
  public final String message;
  
  public ExampleEvent(String message) {
    this.message = message;
  }
  
  public String toString() {
    return super.toString() + ": " + message;
  }
}
