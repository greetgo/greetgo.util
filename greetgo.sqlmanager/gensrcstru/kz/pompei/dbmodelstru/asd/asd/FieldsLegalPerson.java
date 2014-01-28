package kz.pompei.dbmodelstru.asd.asd;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsLegalPerson extends ModelParent {
  public String name;
  public String fullname;
  public String bin;
  
  public FieldsLegalPerson assign(FieldsLegalPerson from) {
    if (from == null) return this;
    this.name = from.name;
    this.fullname = from.fullname;
    this.bin = from.bin;
    return this;
  }
}
