package kz.pompei.dbmodel.dsa.dsa.dsa;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.FieldsAsd;
import java.util.Objects;
import java.util.Arrays;

public class Asd extends FieldsAsd {
  public String asd;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { asd, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Asd other = (Asd)obj;
    return Objects.equals(asd, other.asd);
  }
  
  public Asd() {}
  
  public Asd(String asd) {
    this.asd = asd;
  }
}
