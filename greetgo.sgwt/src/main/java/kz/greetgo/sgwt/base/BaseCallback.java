package kz.greetgo.sgwt.base;

import kz.greetgo.gwtshare.base.ClientException;
import kz.greetgo.gwtshare.base.SgwtException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.smartgwt.client.util.SC;

public abstract class BaseCallback<R> implements AsyncCallback<R> {
  
  private static final String SHOW_ERROR = "showerrors";
  private static final String SHOW_ALL_ERROR = "showallerrors";
  
  @Override
  public void onFailure(Throwable caught) {
    caught.printStackTrace();
    
    if ((caught instanceof StatusCodeException && ((StatusCodeException)caught).getStatusCode() == 401)
        || (caught.getMessage() != null && caught.getMessage().contains("j_spring_security_check"))) {
      Window.Location.reload();
      return;
    }
    
    if (Window.Location.getParameter(SHOW_ERROR) != null) {
      if (caught instanceof ClientException || caught instanceof SgwtException) {
        SC.warn(caught.getClass() + ": " +caught.getMessage());
        return;
      }
    } 
    
    if (Window.Location.getParameter(SHOW_ALL_ERROR) != null) {
      SC.warn(caught.getClass() + ": " +caught.getMessage());
      return;
    }
    
    if (caught instanceof ClientException) {
      SC.warn(caught.getMessage());
    }
  }
}
