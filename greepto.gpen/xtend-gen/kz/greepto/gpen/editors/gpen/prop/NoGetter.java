package kz.greepto.gpen.editors.gpen.prop;

@SuppressWarnings("all")
public class NoGetter extends RuntimeException {
  public final String propertyName;
  
  public final Class<?> klass;
  
  public NoGetter(final String propertyName, final Class<?> klass) {
    super(((("propertyName = " + propertyName) + ", klass = ") + klass));
    this.propertyName = propertyName;
    this.klass = klass;
  }
}
