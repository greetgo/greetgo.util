package kz.pompei.dbmodel.asd.asd;

import kz.pompei.dbmodelstru.asd.asd.FieldsContract;
import kz.greetgo.sqlmanager.gen.Dict;
import java.util.Objects;
import java.util.Arrays;

public class Contract extends FieldsContract {
  public long contract;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { contract, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Contract other = (Contract)obj;
    return Objects.equals(contract, other.contract);
  }
  
  public Contract() {}
  
  public Contract(long contract) {
    this.contract = contract;
  }
  
  public Dict toDictionary() {
    return new Dict(contract, client, asd);
  }
}
