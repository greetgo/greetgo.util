package kz.greetgo.sgwt.base;

import java.util.ArrayList;

import kz.greetgo.gwtshare.base.ServiceAsync;

import com.google.gwt.http.client.Request;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.regexp.shared.SplitResult;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ServiceFake<A, R> implements ServiceAsync<A, R> {
  private static final int[] DEFAULT_DELAYS = new int[] { 500 };
  private static final RegExp SPLITTER = RegExp.compile("[^0-9]+");
  
  private static int[] DELAYS = DEFAULT_DELAYS;
  private static int SEQ = 0;
  
  public static final void setDelays(int... initDelays) {
    for (int i = 0; i < initDelays.length; i++) {
      initDelays[i] = Math.max(0, initDelays[i]);
    }
    DELAYS = initDelays.length == 0 ? DEFAULT_DELAYS : initDelays;
  }
  
  public static final void setDelays(String delaysStr) {
    SplitResult strs = SPLITTER.split(delaysStr);
    ArrayList<Integer> initDelays = new ArrayList<Integer>();
    for (int i = 0; i < strs.length(); i++) {
      try {
        initDelays.add(Integer.parseInt(strs.get(i)));
      } catch (NumberFormatException e) {}
    }
    int[] array = new int[initDelays.size()];
    for (int i = 0; i < array.length; i++) {
      array[i] = initDelays.get(i);
    }
    setDelays(array);
  }
  
  @Override
  public final Request invoke(final A action, final AsyncCallback<R> callback) {
    String qualified = this.getClass().toString();
    final String className = qualified.substring(qualified.lastIndexOf('.') + 1);
    final int seq = SEQ++;
    final int currentDelay = DELAYS[seq % DELAYS.length];
    final boolean trace = !"PingServiceFake".equals(className);
    final Timer timer = new Timer() {
      {
        trace("<<", action);
      }
      
      @Override
      public void run() {
        R result;
        try {
          result = fakeInvoke(action);
        } catch (Throwable caught) {
          trace("!!", caught);
          callback.onFailure(caught);
          return;
        }
        trace(">>", result);
        callback.onSuccess(result);
      }
      
      private final void trace(String dir, Object obj) {
        if (trace) {
          System.out.println("##" + seq + " " + currentDelay + " " + className + " " + dir + " "
              + obj);
        }
      }
    };
    timer.schedule(currentDelay);
    
    return new Request() {
      public void cancel() {
        timer.cancel();
      }
      
      public boolean isPending() {
        return true;
      }
    };
  }
  
  protected abstract R fakeInvoke(A action);
}
