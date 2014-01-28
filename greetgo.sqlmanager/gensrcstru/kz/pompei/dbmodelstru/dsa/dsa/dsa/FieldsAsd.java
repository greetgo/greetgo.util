package kz.pompei.dbmodelstru.dsa.dsa.dsa;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsAsd extends ModelParent {
  public String name;
  public Long wow1;
  public String wow2;
  public String wow3;
  
  public FieldsAsd assign(FieldsAsd from) {
    if (from == null) return this;
    this.name = from.name;
    this.wow1 = from.wow1;
    this.wow2 = from.wow2;
    this.wow3 = from.wow3;
    return this;
  }
}
