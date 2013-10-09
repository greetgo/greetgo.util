package kz.greetgo.smartgwt.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PageAction<T> implements IsSerializable {
  public int current;
  public int size;
  public T content;
  
  public PageAction() {}
  
  public PageAction(int current, int size, T content) {
    this.current = current;
    this.size = size;
    this.content = content;
  }
}
