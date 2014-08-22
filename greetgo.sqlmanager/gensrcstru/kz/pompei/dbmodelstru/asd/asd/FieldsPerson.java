package kz.pompei.dbmodelstru.asd.asd;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.Person_type;
import kz.greetgo.sqlmanager.gen.ModelParent;
public abstract class FieldsPerson extends ModelParent {
public Person_type type;
public Long address;
public FieldsPerson assign(FieldsPerson from) {
if (from == null) return this;
this.type = from.type;
this.address = from.address;
return this;
}
}
