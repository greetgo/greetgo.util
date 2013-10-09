package kz.greetgo.smartgwt.share.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionServiceAsync<A, R> {
  void invoke(A action, AsyncCallback<R> callback);
}
