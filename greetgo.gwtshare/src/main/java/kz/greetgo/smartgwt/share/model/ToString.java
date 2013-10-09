package kz.greetgo.smartgwt.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ToString implements IsSerializable {
  public String string;
  
  public ToString() {}
  
  public ToString(String string) {
    this.string = string;
  }
}
