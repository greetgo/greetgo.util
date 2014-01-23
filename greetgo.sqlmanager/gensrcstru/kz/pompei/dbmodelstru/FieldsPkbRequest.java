package kz.pompei.dbmodelstru;

import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsPkbRequest extends ModelParent {
  public Long contract;
  public String idNumber;
  public String reportCode;
  
  public FieldsPkbRequest assign(FieldsPkbRequest from) {
    if (from == null) return this;
    this.contract = from.contract;
    this.idNumber = from.idNumber;
    this.reportCode = from.reportCode;
    return this;
  }
}
