package kz.pompei.dbmodelstru.asd.asd;
import kz.greetgo.sqlmanager.gen.ModelParent;
public abstract class FieldsProductType extends ModelParent {
public Long pkbId;
public String name;
public FieldsProductType assign(FieldsProductType from) {
if (from == null) return this;
this.pkbId = from.pkbId;
this.name = from.name;
return this;
}
}
