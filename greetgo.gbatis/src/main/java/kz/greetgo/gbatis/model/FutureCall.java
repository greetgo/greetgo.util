package kz.greetgo.gbatis.model;

import java.util.Date;

public interface FutureCall<T> {
  T last();
  
  T last(int offset, int pageSize);
  
  T at(Date at);
  
  T at(Date at, int offset, int pageSize);
}
