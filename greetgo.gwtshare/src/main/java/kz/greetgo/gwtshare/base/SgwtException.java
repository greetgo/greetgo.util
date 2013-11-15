package kz.greetgo.gwtshare.base;

public class SgwtException extends RuntimeException {
  public SgwtException() {}
  
  public SgwtException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public SgwtException(String message) {
    super(message);
  }
  
  public SgwtException(Throwable cause) {
    super(cause);
  }
}
