package kz.pompei.dbmodelstru.asd.asd;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import kz.greetgo.sqlmanager.gen.ModelParent;
public abstract class FieldsSmsTemplate extends ModelParent {
public String templateContent;
public Long phone1;
public AnnotationType phone2;
public FieldsSmsTemplate assign(FieldsSmsTemplate from) {
if (from == null) return this;
this.templateContent = from.templateContent;
this.phone1 = from.phone1;
this.phone2 = from.phone2;
return this;
}
}
