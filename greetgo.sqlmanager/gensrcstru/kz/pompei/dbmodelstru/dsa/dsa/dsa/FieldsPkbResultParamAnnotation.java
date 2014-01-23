package kz.pompei.dbmodelstru.dsa.dsa.dsa;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import java.util.Date;
import kz.greetgo.sqlmanager.gen.HelloEnum;
import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsPkbResultParamAnnotation extends ModelParent {
  public String valueStr;
  public Integer valueInt;
  public AnnotationType type;
  public HelloEnum hello;
  public Date goodby;
  
  public FieldsPkbResultParamAnnotation assign(FieldsPkbResultParamAnnotation from) {
    if (from == null) return this;
    this.valueStr = from.valueStr;
    this.valueInt = from.valueInt;
    this.type = from.type;
    this.hello = from.hello;
    this.goodby = from.goodby;
    return this;
  }
}
