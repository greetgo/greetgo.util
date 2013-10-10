package kz.greetgo.gwtshare.base;

/**
 * Asynchronous call.
 * 
 * @author den
 * 
 * @param <A>
 *          Argument type.
 * @param <R>
 *          Result type.
 */
public interface Async<A, R> {
  void invoke(A a, Sync<R> sync);
}
