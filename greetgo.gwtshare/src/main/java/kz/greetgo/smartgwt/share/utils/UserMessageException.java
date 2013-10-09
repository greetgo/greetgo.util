package kz.greetgo.smartgwt.share.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserMessageException extends RuntimeException implements IsSerializable {
  public UserMessageException() {}
  
  public UserMessageException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public UserMessageException(String message) {
    super(message);
  }
  
  public UserMessageException(Throwable cause) {
    super(cause);
  }
}
