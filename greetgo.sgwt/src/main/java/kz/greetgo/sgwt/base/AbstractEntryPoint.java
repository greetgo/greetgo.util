package kz.greetgo.sgwt.base;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.types.DateDisplayFormat;
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
    
    DateUtil.setNormalDateDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
    DateUtil.setShortDateDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
    DateUtil.setShortDatetimeDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
    
    start();
    
    try {
      RootPanel.get("loading").setVisible(false);
    } catch (Exception e) {}
  }
  
  protected abstract void start();
}
