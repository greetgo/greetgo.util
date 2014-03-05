package kz.greetgo.gbatis.futurecall;

public class CannotFindParamException extends RuntimeException {
  
  public CannotFindParamException() {}
  
  public CannotFindParamException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public CannotFindParamException(String message) {
    super(message);
  }
  
  public CannotFindParamException(Throwable cause) {
    super(cause);
  }
  
}
