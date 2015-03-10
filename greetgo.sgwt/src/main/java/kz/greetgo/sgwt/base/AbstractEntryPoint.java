package kz.greetgo.sgwt.base;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.DateUtil;

public abstract class AbstractEntryPoint implements EntryPoint {
  
  public static final boolean debug() {
    return Window.Location.getParameter("debug") != null;
  }
  
  @Override
  public final void onModuleLoad() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable e) {
        e.printStackTrace();
      }
    });
    
    DateUtil.setNormalDateDisplayFormatter(DateUtil.TOEUROPEANSHORTDATE);
    DateUtil.setShortDateDisplayFormatter(DateUtil.TOEUROPEANSHORTDATE);
    DateUtil.setShortDatetimeDisplayFormatter(DateUtil.TOEUROPEANSHORTDATETIME);
    
    start();
    
    try {
      RootPanel.get("loading").setVisible(false);
    } catch (Exception e) {}
  }
  
  protected abstract void start();
}
