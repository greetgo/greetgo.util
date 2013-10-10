package kz.greetgo.gwtshare.base;

/**
 * Asynchronous call which can be forced to return result to callback.
 * 
 * @author den
 * 
 * @param <A>
 *          Argument type.
 * @param <R>
 *          Result type.
 */
public interface Forcible<A, R> extends Async<A, R> {
  void force();
}
