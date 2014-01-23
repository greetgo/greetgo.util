package kz.pompei.dbmodelstru.asd.asd;

import java.util.Date;
import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsNaturalPerson extends ModelParent {
  public String surname;
  public String name;
  public Date birthdate;
  public String iin;
  
  public FieldsNaturalPerson assign(FieldsNaturalPerson from) {
    if (from == null) return this;
    this.surname = from.surname;
    this.name = from.name;
    this.birthdate = from.birthdate;
    this.iin = from.iin;
    return this;
  }
}
