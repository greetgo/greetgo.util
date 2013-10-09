package kz.greetgo.smartgwt.share.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.gwt.user.client.rpc.IsSerializable;

public final class PageProgress implements IsSerializable {
  public int current;
  public int total;
  
  public PageProgress() {}
  
  public PageProgress(int current, int total) {
    Preconditions.checkArgument(total > 0);
    Preconditions.checkElementIndex(current - 1, total);
    
    this.current = current;
    this.total = total;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(current, total);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof PageProgress)) {
      return false;
    }
    PageProgress that = (PageProgress)obj;
    return current == that.current && total == that.total;
  }
  
  @Override
  public String toString() {
    return Objects.toStringHelper("PageProgress").add("current", current).add("total", total)
        .toString();
  }
}
