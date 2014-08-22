package kz.pompei.dbmodelstru.asd.asd;
import kz.greetgo.sqlmanager.gen.ModelParent;
public abstract class FieldsPhone extends ModelParent {
public String type;
public String value;
public Long smsTemplate;
public FieldsPhone assign(FieldsPhone from) {
if (from == null) return this;
this.type = from.type;
this.value = from.value;
this.smsTemplate = from.smsTemplate;
return this;
}
}
