package kz.greetgo.smartgwt.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface HasKey<T> extends IsSerializable {
  T key();
}
