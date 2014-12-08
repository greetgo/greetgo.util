package kz.greepto.gpen.editors.gpen.prop

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.HashMap
import java.util.List
import java.util.Map
import kz.greepto.gpen.editors.gpen.action.ActionModify
import kz.greepto.gpen.util.Handler

class PropFactory {

  static class AccessorInfo implements PropAccessor, Comparable<AccessorInfo> {
    val String name
    val Object object
    val SceneWorker sceneWorker

    boolean skip = false
    boolean fin = false

    new(String name, Object object, SceneWorker sceneWorker) {
      this.name = name
      this.object = object
      this.sceneWorker = sceneWorker
    }

    var Class<?> type

    override getType() {
      return type
    }

    override getName() {
      return name
    }

    ValueGetter getter
    ValueSetter setter

    override toString() {
      var StringBuilder sb = new StringBuilder
      sb.append(name)
      if (setter == null) {
        sb.append(' READONLY')
      }
      if (skip) {
        sb.append(' SKIPPED')
      }
      sb.append(' : ' + type.simpleName)
      return sb.toString
    }

    override getValue() {
      return getter.getValue(object)
    }

    val PropOptions options = new PropOptions() {
      override isBig() {
        false;
      }

      override isReadonly() {
        return setter == null
      }
    }

    override getOptions() {
      return options
    }

    override setValue(Object value) {
      var action = getSettingAction(value)
      if(action == null) return;
      sceneWorker.sendAction(action)
    }

    override getSettingAction(Object newValue) {
      if(setter == null) return null
      if(getter == null) throw new NoGetter(name, object.class)
      var curValue = getter.getValue(object)
      if(newValue == curValue) return null
      return new ActionModify(setter, newValue, sceneWorker.takeId(object))
    }

    override compareTo(AccessorInfo o) {
      return name.compareTo(o.name)
    }

    override addChangeHandler(Handler handler) {
      return sceneWorker.addChangeHandler(handler)
    }

    def void postInit() {
      if(fin) setter = null
    }
  }

  def static List<PropAccessor> parseObject(Object object, SceneWorker sceneWorker) {
    val infoMap = new HashMap<String, AccessorInfo>

    for (f : object.class.fields) {
      appendField(infoMap, f, object, sceneWorker)
    }

    for (m : object.class.methods) {
      appendMethod(infoMap, m, object, sceneWorker)
    }

    return infoMap.values.filter[!skip].sort.map[postInit; it]
  }

  private def static appendMethod(Map<String, AccessorInfo> infoMap, Method m, Object object, SceneWorker sceneWorker) {
    m.accessible = true

    var getter = createMethodValueGetter(m)
    if (getter != null) {

      var info = infoMap.get(getter.name)
      if (info == null) {
        info = new AccessorInfo(getter.name, object, sceneWorker)
        infoMap.put(info.name, info)
      }

      info.getter = getter
      info.type = getter.type
      readGetterOptions(info, m)

      return;
    }

    {
      var setter = createMethodValueSetter(m, infoMap, object.class)
      if(setter == null) return;

      var info = infoMap.get(setter.name)
      if (info == null) {
        info = new AccessorInfo(setter.name, object, sceneWorker)
        infoMap.put(info.name, info)
      }
      if(info.skip) return;

      info.setter = setter
      if (info.type == null) {
        info.type = setter.type
      }

      readSetterOptions(info, m)
    }
  }

  private def static appendField(Map<String, AccessorInfo> infoMap, Field f, Object object, SceneWorker sceneWorker) {
    f.accessible = true

    var needRead = true

    {
      var getter = createFieldValueGetter(f)
      if(getter == null) return;

      var info = infoMap.get(getter.name)
      if (info == null) {
        info = new AccessorInfo(getter.name, object, sceneWorker)
        infoMap.put(info.name, info)
      }

      info.getter = getter
      info.type = getter.type

      readFieldOptions(info, f)
      needRead = false
    }

    {
      var setter = createFieldValueSetter(f, infoMap, object.class)
      if(setter == null) return;

      var info = infoMap.get(setter.name)
      if (info == null) {
        info = new AccessorInfo(setter.name, object, sceneWorker)
        infoMap.put(info.name, info)
      }

      info.setter = setter
      info.type = setter.type

      if(needRead) readFieldOptions(info, f)
    }
  }

  private def static ValueSetter createMethodValueSetter(Method method, Map<String, AccessorInfo> infoMap,
    Class<?> klass) {

    if(method.name.length > 3 && "set" != method.name.substring(0, 3)) return null
    if(method.parameterTypes.length != 1) return null

    val propertyName = method.name.substring(3).toFirstLower

    return new ValueSetter() {
      override setValue(Object object, Object value) {
        val info = infoMap.get(propertyName)
        if(info == null) throw new NoGetter(propertyName, klass)
        val getter = info.getter
        if(getter == null) throw new NoGetter(propertyName, klass)
        var oldValue = getter.getValue(object)
        method.invoke(object, value)
        return oldValue
      }

      override getType() {
        return method.parameterTypes.get(0)
      }

      override getName() {
        return propertyName
      }
    }
  }

  private def static ValueGetter createMethodValueGetter(Method method) {
    if(method.parameterTypes.length > 0) return null
    var String name = null
    if (method.name.length > 3 && "get" == method.name.substring(0, 3)) {
      name = method.name.substring(3).toFirstLower
    }
    if (name == null && method.name.length > 2 && "is" == method.name.substring(0, 2)) {
      if (method.returnType == Boolean || method.returnType == Boolean.TYPE) {
        name = method.name.substring(2).toFirstLower
      }
    }
    if(name == null) return null;
    val propertyName = name
    return new ValueGetter() {
      override getType() {
        return method.returnType
      }

      override getName() {
        return propertyName
      }

      override getValue(Object object) {
        return method.invoke(object)
      }
    }
  }

  private def static ValueGetter createFieldValueGetter(Field field) {
    return new ValueGetter() {
      override getValue(Object object) {
        return field.get(object)
      }

      override getType() {
        return field.type
      }

      override getName() {
        return field.name
      }
    }
  }

  private def static ValueSetter createFieldValueSetter(Field field, Map<String, AccessorInfo> infoMap, Class<?> klass) {
    return new ValueSetter() {
      override getType() {
        return field.type
      }

      override getName() {
        return field.name
      }

      override setValue(Object object, Object value) {
        var info = infoMap.get(field.name)
        if(info == null) throw new NoGetter(field.name, klass)
        var getter = info.getter
        if(getter == null) throw new NoGetter(field.name, klass)
        var oldValue = getter.getValue(object)
        field.set(object, value)
        return oldValue
      }
    }
  }

  private def static readSetterOptions(AccessorInfo info, Method method) {
    info.skip = method.getAnnotation(Skip) != null
  }

  private def static readFieldOptions(AccessorInfo info, Field field) {
    info.skip = field.getAnnotation(Skip) != null

    info.fin = Modifier.isFinal(field.modifiers)

  }

  private def static readGetterOptions(AccessorInfo info, Method method) {
    info.skip = method.getAnnotation(Skip) != null
  }
}
