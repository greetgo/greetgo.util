package kz.greetgo.smartgwt.share.remote;

import com.google.common.base.Objects;
import com.google.gwt.user.client.rpc.IsSerializable;

public interface SetService<A, T> extends ActionService<SetService.Entry<A, T>, T> {
  public static final class Entry<A, T> implements IsSerializable {
    public A key;
    public T value;
    
    public Entry() {}
    
    public Entry(A key, T value) {
      this.key = key;
      this.value = value;
    }
    
    @Override
    public String toString() {
      return Objects.toStringHelper("SetService.Entry").add("key", key).add("value", value)
          .toString();
    }
  }
}
