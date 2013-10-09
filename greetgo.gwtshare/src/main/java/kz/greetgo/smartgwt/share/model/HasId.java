package kz.greetgo.smartgwt.share.model;

import com.google.common.base.Objects;
import com.google.gwt.user.client.rpc.IsSerializable;

public class HasId implements IsSerializable, HasKey<Long> {
  public Long id;
  
  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof HasId)) {
      return false;
    }
    HasId r = (HasId)obj;
    return Objects.equal(this.id, r.id);
  }
  
  @Override
  public String toString() {
    return Objects.toStringHelper("HasId").add("id", id).toString();
  }
  
  @Override
  public Long key() {
    return id;
  }
}
