package kz.greetgo.smartgwt.share.model;

import java.util.HashSet;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PageResult<T> implements IsSerializable {
  public PageProgress pageProgress;
  public HashSet<T> content;
  
  public PageResult() {}
  
  public PageResult(PageProgress pageProgress, HashSet<T> content) {
    this.pageProgress = pageProgress;
    this.content = content;
  }
}
