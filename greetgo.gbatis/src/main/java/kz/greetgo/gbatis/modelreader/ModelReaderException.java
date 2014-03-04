package kz.greetgo.gbatis.modelreader;

public class ModelReaderException extends RuntimeException {
  
  public ModelReaderException() {}
  
  public ModelReaderException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public ModelReaderException(String message) {
    super(message);
  }
  
  public ModelReaderException(Throwable cause) {
    super(cause);
  }
  
}
