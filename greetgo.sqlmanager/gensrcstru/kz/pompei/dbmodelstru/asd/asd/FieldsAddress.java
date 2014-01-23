package kz.pompei.dbmodelstru.asd.asd;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsAddress extends ModelParent {
  public String street;
  public String flat;
  
  public FieldsAddress assign(FieldsAddress from) {
    if (from == null) return this;
    this.street = from.street;
    this.flat = from.flat;
    return this;
  }
}
