package kz.greetgo.gwtshare.base;

/**
 * Synchronous callback.
 * 
 * @author den
 * 
 * @param <R>
 *          Result type.
 */
public interface Sync<R> {
  void invoke(R result);
}
