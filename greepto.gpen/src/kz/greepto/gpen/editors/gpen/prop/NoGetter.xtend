package kz.greepto.gpen.editors.gpen.prop

class NoGetter extends RuntimeException {

  public val String propertyName
  public val Class<?> klass

  new(String propertyName, Class<?> klass) {
    super('propertyName = ' + propertyName + ', klass = ' + klass)
    this.propertyName = propertyName
    this.klass = klass
  }

}
