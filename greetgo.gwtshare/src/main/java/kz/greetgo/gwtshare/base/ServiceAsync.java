package kz.greetgo.gwtshare.base;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchronous part of the remote service.
 * 
 * @author den
 * 
 * @param <A>
 *          Argument type.
 * @param <R>
 *          Result type.
 */
public interface ServiceAsync<A, R> {
  void invoke(A arg, AsyncCallback<R> callback);
}
