package kz.pompei.dbmodel;

import java.util.Objects;
import kz.pompei.dbmodelstru.FieldsPkbResultParam;
import java.util.Arrays;

public class PkbResultParam extends FieldsPkbResultParam {
  public long pkbResult;
  public String name;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { pkbResult, name, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PkbResultParam other = (PkbResultParam)obj;
    return Objects.equals(pkbResult, other.pkbResult) && Objects.equals(name, other.name);
  }
  
  public PkbResultParam() {}
  
  public PkbResultParam(long pkbResult, String name) {
    this.pkbResult = pkbResult;
    this.name = name;
  }
}
