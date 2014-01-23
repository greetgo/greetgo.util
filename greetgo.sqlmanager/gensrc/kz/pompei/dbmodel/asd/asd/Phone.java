package kz.pompei.dbmodel.asd.asd;

import kz.pompei.dbmodelstru.dsa.dsa.dsa.AnnotationType;
import kz.pompei.dbmodelstru.asd.asd.FieldsPhone;
import java.util.Objects;
import java.util.Arrays;

public class Phone extends FieldsPhone {
  public long person;
  public AnnotationType annType;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { person, annType, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Phone other = (Phone)obj;
    return Objects.equals(person, other.person) && Objects.equals(annType, other.annType);
  }
  
  public Phone() {}
  
  public Phone(long person, AnnotationType annType) {
    this.person = person;
    this.annType = annType;
  }
}
