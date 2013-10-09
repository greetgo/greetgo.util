package kz.greetgo.smartgwt.share.base;

public interface Async<A, R> {
  void invoke(A a, Sync<R> sync);
}
