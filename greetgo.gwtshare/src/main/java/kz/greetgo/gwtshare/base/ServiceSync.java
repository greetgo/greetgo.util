package kz.greetgo.gwtshare.base;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Synchronous part of the remote service.
 * 
 * @author den
 * 
 * @param <A>
 *          Argument type.
 * @param <R>
 *          Result type.
 */
public interface ServiceSync<A, R> extends RemoteService {
  R invoke(A arg) throws Exception;
}
