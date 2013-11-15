package kz.greetgo.gwtshare.base;

public class ClientException extends SgwtException {
  public ClientException() {}
  
  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public ClientException(String message) {
    super(message);
  }
  
  public ClientException(Throwable cause) {
    super(cause);
  }
}
