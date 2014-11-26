package kz.greepto.gpen.util

import java.lang.reflect.Method
import java.util.HashMap
import java.util.Map
import org.eclipse.xtend.lib.annotations.Data

class PropManager {
  private Object object

  new() {
  }

  new(Object object) {
    this.object = object
  }

  Map<String, PropGetter> getterMap = null
  Map<String, PropSetter> setterMap = null
  Map<String, Class<?>> classMap = null

  public def void setObject(Object object) {
    this.object = object

    getterMap = null
    setterMap = null
    classMap = null
  }

  public def Map<String, Class<?>> meta() {
    if(classMap == null) prepare
    return classMap.immutableCopy
  }

  public def Object get(String name) {
    if(classMap == null) prepare
    getterMap.get(name).get
  }

  public def void set(String name, Object value) {
    if(classMap == null) prepare
    val setter = setterMap.get(name)
    if(setter == null) throw new NoProperty(name)
    setter.set(value)
  }

  @Data
  public static class NameType {
    String name
    Class<?> type
  }

  def prepare() {
    if(object == null) throw new NullPointerException('object == null')

    classMap = new HashMap
    getterMap = new HashMap
    setterMap = new HashMap

    for (field : object.class.fields) {
      classMap.put(field.name, field.type)
      getterMap.put(field.name) [field.get(object)]
      setterMap.put(field.name) [field.set(object, it)]
    }

    for (method : object.class.methods) {
      comformMethod(method)
    }

  }

  def comformMethod(Method method) {
    {
      var nt = getGetterNameType(method);
      if (nt != null) {
        classMap.put(nt.name, nt.type)
        getterMap.put(nt.name) [method.invoke(object)]
        return;
      }
    }
    {
      var nt = getSetterNameType(method);
      if (nt != null) {
        classMap.put(nt.name, nt.type)
        setterMap.put(nt.name) [method.invoke(object, it)]
        return;
      }
    }
  }

  def static NameType getGetterNameType(Method method) {
    if (method.parameterTypes.size > 0) return null
    if(!method.name.startsWith("get")) return null
    var name = method.name.substring(3).toFirstLower
    return new NameType(name, method.returnType)
  }

  def static NameType getSetterNameType(Method method) {
    if (method.parameterTypes.size != 0) return null
    var type = method.parameterTypes.get(0)
    if(!method.name.startsWith("set")) return null
    var name = method.name.substring(3).toFirstLower
    return new NameType(name, type)
  }

}
