package kz.greepto.gpen.util

import java.lang.reflect.Method
import java.util.HashMap
import java.util.Map
import org.eclipse.xtend.lib.annotations.Data

class PropManagerObject implements PropManager {
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

  override def Map<String, Class<?>> meta() {
    if(classMap == null) prepare
    return classMap.immutableCopy
  }

  override def Object get(String name) {
    if(classMap == null) prepare
    getterMap.get(name).get
  }

  override def void set(String name, Object value) {
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

  private def prepare() {
    if(object == null) throw new NullPointerException('object == null')

    classMap = new HashMap
    getterMap = new HashMap
    setterMap = new HashMap

    for (field : object.class.fields) {
      classMap.put(field.name, field.type)
      getterMap.put(field.name)[field.get(object)]
      setterMap.put(field.name)[field.set(object, it)]
    }

    for (method : object.class.methods) {
      comformMethod(method)
    }

  }

  private def comformMethod(Method method) {
    {
      var nt = getGetterNameType(method);
      if (nt != null) {
        classMap.put(nt.name, nt.type)
        getterMap.put(nt.name)[method.invoke(object)]
        return;
      }
    }
    {
      var nt = getSetterNameType(method);
      if (nt != null) {
        classMap.put(nt.name, nt.type)
        setterMap.put(nt.name)[method.invoke(object, it)]
        return;
      }
    }
  }

  private def static NameType getGetterNameType(Method method) {
    if(method.parameterTypes.size > 0) return null
    if(!method.name.startsWith("get")) return null
    var name = method.name.substring(3).toFirstLower
    return new NameType(name, method.returnType)
  }

  private def static NameType getSetterNameType(Method method) {
    if(method.parameterTypes.size != 1) return null
    var type = method.parameterTypes.get(0)
    if(!method.name.startsWith("set")) return null
    var name = method.name.substring(3).toFirstLower
    return new NameType(name, type)
  }

  override def void setAsStr(String name, String strValue) {
    if(classMap == null) prepare
    var cl = classMap.get(name)
    if(cl == null) throw new NoProperty(name)
    if (cl == String) {
      set(name, strValue)
      return;
    }
    if (cl == Integer.TYPE) {
      set(name, if(strValue.isNullOrEmpty) 0 else Integer.parseInt(strValue))
      return
    }
    if (cl == Integer) {
      set(name, if(strValue.isNullOrEmpty) null else Integer.valueOf(strValue))
      return;
    }
  }

  override def String getAsStr(String name) {
    var object = get(name)
    if (object == null) return null
    if (object instanceof String) return object as String
    if (object instanceof Integer) return "" + object
    throw new CannotConvertToStr(name, object)
  }

}
