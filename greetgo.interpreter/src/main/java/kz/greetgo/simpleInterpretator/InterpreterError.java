package kz.greetgo.simpleInterpretator;

@SuppressWarnings("serial")
public class InterpreterError extends RuntimeException {
  
  public InterpreterError() {
    code = ErrorCode.UNKNOWN;
  }
  
  public InterpreterError(String message, Throwable cause) {
    super(message, cause);
    code = ErrorCode.UNKNOWN;
  }
  
  private final ErrorCode code;
  
  public InterpreterError(String message) {
    super(message);
    code = ErrorCode.UNKNOWN;
  }
  
  public InterpreterError(ErrorCode code, String message) {
    super(message);
    this.code = code;
  }
  
  public InterpreterError(Throwable cause) {
    super(cause);
    code = ErrorCode.UNKNOWN;
  }
  
  public ErrorCode getCode() {
    return code;
  }
}
