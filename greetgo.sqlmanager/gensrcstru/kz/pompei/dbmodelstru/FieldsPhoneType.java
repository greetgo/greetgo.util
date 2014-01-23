package kz.pompei.dbmodelstru;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsPhoneType extends ModelParent {
  public String name;
  
  public FieldsPhoneType assign(FieldsPhoneType from) {
    if (from == null) return this;
    this.name = from.name;
    return this;
  }
}
