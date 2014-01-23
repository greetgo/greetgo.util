package kz.pompei.dbmodel.asd.asd;

import java.util.Objects;
import kz.pompei.dbmodelstru.asd.asd.FieldsLegalPerson;
import java.util.Arrays;

public class LegalPerson extends FieldsLegalPerson {
  public long legalPerson;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { legalPerson, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    LegalPerson other = (LegalPerson)obj;
    return Objects.equals(legalPerson, other.legalPerson);
  }
  
  public LegalPerson() {}
  
  public LegalPerson(long legalPerson) {
    this.legalPerson = legalPerson;
  }
}
