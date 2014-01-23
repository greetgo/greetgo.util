package kz.pompei.dbmodel;

import java.util.Objects;
import java.util.Arrays;
import kz.pompei.dbmodelstru.FieldsPhoneType;

public class PhoneType extends FieldsPhoneType {
  public String phoneType;
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(new Object[] { phoneType, });
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PhoneType other = (PhoneType)obj;
    return Objects.equals(phoneType, other.phoneType);
  }
  
  public PhoneType() {}
  
  public PhoneType(String phoneType) {
    this.phoneType = phoneType;
  }
}
