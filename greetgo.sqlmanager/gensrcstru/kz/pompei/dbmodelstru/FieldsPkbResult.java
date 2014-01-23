package kz.pompei.dbmodelstru;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsPkbResult extends ModelParent {
  public Boolean isOK;
  public String xmlContent;
  
  public FieldsPkbResult assign(FieldsPkbResult from) {
    if (from == null) return this;
    this.isOK = from.isOK;
    this.xmlContent = from.xmlContent;
    return this;
  }
  
  public int getIsOKInt() {
    return (isOK != null && isOK) ? 1 :0;
  }
}
