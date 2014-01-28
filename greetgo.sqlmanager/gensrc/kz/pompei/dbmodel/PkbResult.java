package kz.pompei.dbmodel;

import java.util.Objects;
import java.util.Arrays;
import kz.pompei.dbmodelstru.FieldsPkbResult;

public class PkbResult extends FieldsPkbResult {
  public long pkbResult;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { pkbResult, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PkbResult other = (PkbResult)obj;
    return Objects.equals(pkbResult, other.pkbResult);
  }
  
  public PkbResult() {}
  
  public PkbResult(long pkbResult) {
    this.pkbResult = pkbResult;
  }
}
