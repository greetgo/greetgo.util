package kz.pompei.dbmodelstru.asd.asd;

import java.util.Date;
import kz.greetgo.sqlmanager.gen.ModelParent;

public abstract class FieldsContract extends ModelParent {
  public String nomer;
  public Double summa;
  public String productType;
  public Long client;
  public Long lastPkbRequest;
  public Date asd;
  
  public FieldsContract assign(FieldsContract from) {
    if (from == null) return this;
    this.nomer = from.nomer;
    this.summa = from.summa;
    this.productType = from.productType;
    this.client = from.client;
    this.lastPkbRequest = from.lastPkbRequest;
    this.asd = from.asd;
    return this;
  }
}
