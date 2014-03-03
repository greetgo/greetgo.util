package kz.greetgo.gbatis.model;

import java.util.Date;

public interface FutureCall<T> {
  T last();
  
  T on(Date at);
  
  T lastPaged(int offset, int pageSize);
  
  T onPaged(Date at, int offset, int pageSize);
}
