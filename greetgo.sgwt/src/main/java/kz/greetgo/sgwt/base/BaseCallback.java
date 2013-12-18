package kz.greetgo.sgwt.base;

import kz.greetgo.gwtshare.base.ClientException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;

public abstract class BaseCallback<R> implements AsyncCallback<R> {
  
  @Override
  public void onFailure(Throwable caught) {
    if (Window.Location.getParameter("hideerrors") == null) {
      boolean isSafe = caught instanceof ClientException
          || Window.Location.getParameter("showerrors") != null;
      SC.warn(isSafe ? caught.getMessage() :"Сбой сервера.");
    }
    caught.printStackTrace();
  }
}
