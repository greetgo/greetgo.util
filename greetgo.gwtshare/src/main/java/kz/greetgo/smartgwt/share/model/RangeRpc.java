package kz.greetgo.smartgwt.share.model;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("rawtypes")
public final class RangeRpc<T extends Comparable> implements IsSerializable {
  private T lower;
  private T upper;
  
  public RangeRpc() {}
  
  public RangeRpc(T lower, T upper) {
    this.lower = lower;
    this.upper = upper;
  }
  
  public RangeRpc(Range<T> range) {
    this(range.lowerEndpoint(), range.upperEndpoint());
  }
  
  public final Range<T> closed() {
    return Ranges.closed(lower, upper);
  }
}
