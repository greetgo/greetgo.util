package kz.greepto.gpen.util;

public class CannotConvertToStr extends RuntimeException {
  
  public final String fieldName;
  public final Object object;
  
  CannotConvertToStr(String fieldName, Object object) {
    super("fieldName = " + fieldName + ", object = " + object);
    this.fieldName = fieldName;
    this.object = object;
  }
  
}
