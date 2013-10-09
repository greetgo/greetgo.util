package kz.greetgo.smartgwt.share.model;

public final class Property<T> extends HasId {
  public T property;
  
  public Property() {}
  
  public Property(Long id, T property) {
    this.id = id;
    this.property = property;
  }
}
