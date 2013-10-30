package kz.greetgo.gwtshare.base;

import com.google.gwt.http.client.Request;
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
  Request invoke(A arg, AsyncCallback<R> callback);
}
