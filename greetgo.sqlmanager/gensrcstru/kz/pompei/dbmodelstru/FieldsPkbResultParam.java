package kz.pompei.dbmodelstru;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsPkbResultParam extends ModelParent {
  public String value;
  
  public FieldsPkbResultParam assign(FieldsPkbResultParam from) {
    if (from == null) return this;
    this.value = from.value;
    return this;
  }
}
