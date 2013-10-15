package kz.greetgo.gwtshare.base;

public class ClientException extends Exception {
  private static final long serialVersionUID = 198781797530029855L;
  
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
