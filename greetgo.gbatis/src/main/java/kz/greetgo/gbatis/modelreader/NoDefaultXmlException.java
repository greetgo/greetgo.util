package kz.greetgo.gbatis.modelreader;

public class NoDefaultXmlException extends RuntimeException {
  
  public NoDefaultXmlException() {}
  
  public NoDefaultXmlException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public NoDefaultXmlException(String message) {
    super(message);
  }
  
  public NoDefaultXmlException(Throwable cause) {
    super(cause);
  }
  
}
