package kz.greepto.gpen.editors.gpen.prop;

import com.google.common.base.Objects;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.editors.gpen.action.OperModify;
import kz.greepto.gpen.editors.gpen.prop.NoGetter;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
import kz.greepto.gpen.editors.gpen.prop.SceneWorker;
import kz.greepto.gpen.editors.gpen.prop.Skip;
import kz.greepto.gpen.editors.gpen.prop.ValueGetter;
import kz.greepto.gpen.editors.gpen.prop.ValueSetter;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class PropFactory {
  public static class AccessorInfo implements PropAccessor, Comparable<PropFactory.AccessorInfo> {
    private final String name;
    
    private final Object object;
    
    private final SceneWorker sceneWorker;
    
    private boolean skip = false;
    
    private boolean fin = false;
    
    public AccessorInfo(final String name, final Object object, final SceneWorker sceneWorker) {
      this.name = name;
      this.object = object;
      this.sceneWorker = sceneWorker;
    }
    
    private Class<?> type;
    
    public Class<?> getType() {
      return this.type;
    }
    
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      int _hashCode = 0;
      if (this.name!=null) {
        _hashCode=this.name.hashCode();
      }
      int _plus = ((prime * result) + _hashCode);
      result = _plus;
      int _hashCode_1 = 0;
      if (this.type!=null) {
        _hashCode_1=this.type.hashCode();
      }
      int _plus_1 = ((prime * result) + _hashCode_1);
      result = _plus_1;
      return result;
    }
    
    public boolean equals(final Object obj) {
      boolean _tripleEquals = (this == obj);
      if (_tripleEquals) {
        return true;
      }
      boolean _tripleEquals_1 = (obj == null);
      if (_tripleEquals_1) {
        return false;
      }
      if ((!(obj instanceof PropFactory.AccessorInfo))) {
        return false;
      }
      PropFactory.AccessorInfo a = ((PropFactory.AccessorInfo) obj);
      boolean _notEquals = (!Objects.equal(this.name, a.name));
      if (_notEquals) {
        return false;
      }
      boolean _notEquals_1 = (!Objects.equal(this.type, a.type));
      if (_notEquals_1) {
        return false;
      }
      return true;
    }
    
    public String getName() {
      return this.name;
    }
    
    private ValueGetter getter;
    
    private ValueSetter setter;
    
    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.name);
      boolean _equals = Objects.equal(this.setter, null);
      if (_equals) {
        sb.append(" READONLY");
      }
      if (this.skip) {
        sb.append(" SKIPPED");
      }
      String _simpleName = this.type.getSimpleName();
      String _plus = (" : " + _simpleName);
      sb.append(_plus);
      return sb.toString();
    }
    
    public Object getValue() {
      return this.getter.getValue(this.object);
    }
    
    private final PropOptions options = new PropOptions() {
      public boolean isBig() {
        return false;
      }
      
      public boolean isReadonly() {
        return Objects.equal(AccessorInfo.this.setter, null);
      }
    };
    
    public PropOptions getOptions() {
      return this.options;
    }
    
    public void setValue(final Object value) {
      Oper oper = this.getSettingOper(value);
      boolean _equals = Objects.equal(oper, null);
      if (_equals) {
        return;
      }
      this.sceneWorker.applyOper(oper);
    }
    
    public Oper getSettingOper(final Object newValue) {
      boolean _equals = Objects.equal(this.setter, null);
      if (_equals) {
        return null;
      }
      boolean _equals_1 = Objects.equal(this.getter, null);
      if (_equals_1) {
        Class<?> _class = this.object.getClass();
        throw new NoGetter(this.name, _class);
      }
      Object curValue = this.getter.getValue(this.object);
      boolean _equals_2 = Objects.equal(newValue, curValue);
      if (_equals_2) {
        return null;
      }
      String _takeId = this.sceneWorker.takeId(this.object);
      return new OperModify(this.setter, newValue, _takeId);
    }
    
    public int compareTo(final PropFactory.AccessorInfo o) {
      return this.name.compareTo(o.name);
    }
    
    public HandlerKiller addChangeHandler(final Handler handler) {
      return this.sceneWorker.addChangeHandler(handler);
    }
    
    public void postInit() {
      if (this.fin) {
        this.setter = null;
      }
    }
  }
  
  public static PropList parseObject(final Object object, final SceneWorker sceneWorker) {
    final HashMap<String, PropFactory.AccessorInfo> infoMap = new HashMap<String, PropFactory.AccessorInfo>();
    Class<?> _class = object.getClass();
    Field[] _fields = _class.getFields();
    for (final Field f : _fields) {
      PropFactory.appendField(infoMap, f, object, sceneWorker);
    }
    Class<?> _class_1 = object.getClass();
    Method[] _methods = _class_1.getMethods();
    for (final Method m : _methods) {
      PropFactory.appendMethod(infoMap, m, object, sceneWorker);
    }
    Collection<PropFactory.AccessorInfo> _values = infoMap.values();
    final Function1<PropFactory.AccessorInfo, Boolean> _function = new Function1<PropFactory.AccessorInfo, Boolean>() {
      public Boolean apply(final PropFactory.AccessorInfo it) {
        return Boolean.valueOf((!it.skip));
      }
    };
    Iterable<PropFactory.AccessorInfo> _filter = IterableExtensions.<PropFactory.AccessorInfo>filter(_values, _function);
    List<PropFactory.AccessorInfo> _sort = IterableExtensions.<PropFactory.AccessorInfo>sort(_filter);
    final Function1<PropFactory.AccessorInfo, PropAccessor> _function_1 = new Function1<PropFactory.AccessorInfo, PropAccessor>() {
      public PropAccessor apply(final PropFactory.AccessorInfo it) {
        PropFactory.AccessorInfo _xblockexpression = null;
        {
          it.postInit();
          _xblockexpression = it;
        }
        return _xblockexpression;
      }
    };
    List<PropAccessor> _map = ListExtensions.<PropFactory.AccessorInfo, PropAccessor>map(_sort, _function_1);
    return PropList.from(_map);
  }
  
  private static void appendMethod(final Map<String, PropFactory.AccessorInfo> infoMap, final Method m, final Object object, final SceneWorker sceneWorker) {
    m.setAccessible(true);
    ValueGetter getter = PropFactory.createMethodValueGetter(m);
    boolean _notEquals = (!Objects.equal(getter, null));
    if (_notEquals) {
      String _name = getter.getName();
      PropFactory.AccessorInfo info = infoMap.get(_name);
      boolean _equals = Objects.equal(info, null);
      if (_equals) {
        String _name_1 = getter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        infoMap.put(info.name, info);
      }
      info.getter = getter;
      Class<?> _type = getter.getType();
      info.type = _type;
      PropFactory.readGetterOptions(info, m);
      return;
    }
    {
      Class<?> _class = object.getClass();
      ValueSetter setter = PropFactory.createMethodValueSetter(m, infoMap, _class);
      boolean _equals_1 = Objects.equal(setter, null);
      if (_equals_1) {
        return;
      }
      String _name_2 = setter.getName();
      PropFactory.AccessorInfo info_1 = infoMap.get(_name_2);
      boolean _equals_2 = Objects.equal(info_1, null);
      if (_equals_2) {
        String _name_3 = setter.getName();
        PropFactory.AccessorInfo _accessorInfo_1 = new PropFactory.AccessorInfo(_name_3, object, sceneWorker);
        info_1 = _accessorInfo_1;
        infoMap.put(info_1.name, info_1);
      }
      if (info_1.skip) {
        return;
      }
      info_1.setter = setter;
      boolean _equals_3 = Objects.equal(info_1.type, null);
      if (_equals_3) {
        Class<?> _type_1 = setter.getType();
        info_1.type = _type_1;
      }
      PropFactory.readSetterOptions(info_1, m);
    }
  }
  
  private static void appendField(final Map<String, PropFactory.AccessorInfo> infoMap, final Field f, final Object object, final SceneWorker sceneWorker) {
    f.setAccessible(true);
    boolean needRead = true;
    {
      ValueGetter getter = PropFactory.createFieldValueGetter(f);
      boolean _equals = Objects.equal(getter, null);
      if (_equals) {
        return;
      }
      String _name = getter.getName();
      PropFactory.AccessorInfo info = infoMap.get(_name);
      boolean _equals_1 = Objects.equal(info, null);
      if (_equals_1) {
        String _name_1 = getter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        infoMap.put(info.name, info);
      }
      info.getter = getter;
      Class<?> _type = getter.getType();
      info.type = _type;
      PropFactory.readFieldOptions(info, f);
      needRead = false;
    }
    {
      Class<?> _class = object.getClass();
      ValueSetter setter = PropFactory.createFieldValueSetter(f, infoMap, _class);
      boolean _equals = Objects.equal(setter, null);
      if (_equals) {
        return;
      }
      String _name = setter.getName();
      PropFactory.AccessorInfo info = infoMap.get(_name);
      boolean _equals_1 = Objects.equal(info, null);
      if (_equals_1) {
        String _name_1 = setter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        infoMap.put(info.name, info);
      }
      info.setter = setter;
      Class<?> _type = setter.getType();
      info.type = _type;
      if (needRead) {
        PropFactory.readFieldOptions(info, f);
      }
    }
  }
  
  private static ValueSetter createMethodValueSetter(final Method method, final Map<String, PropFactory.AccessorInfo> infoMap, final Class<?> klass) {
    boolean _and = false;
    String _name = method.getName();
    int _length = _name.length();
    boolean _greaterThan = (_length > 3);
    if (!_greaterThan) {
      _and = false;
    } else {
      String _name_1 = method.getName();
      String _substring = _name_1.substring(0, 3);
      boolean _notEquals = (!Objects.equal("set", _substring));
      _and = _notEquals;
    }
    if (_and) {
      return null;
    }
    Class<?>[] _parameterTypes = method.getParameterTypes();
    int _length_1 = _parameterTypes.length;
    boolean _notEquals_1 = (_length_1 != 1);
    if (_notEquals_1) {
      return null;
    }
    String _name_2 = method.getName();
    String _substring_1 = _name_2.substring(3);
    final String propertyName = StringExtensions.toFirstLower(_substring_1);
    return new ValueSetter() {
      public Object setValue(final Object object, final Object value) {
        try {
          final PropFactory.AccessorInfo info = infoMap.get(propertyName);
          boolean _equals = Objects.equal(info, null);
          if (_equals) {
            throw new NoGetter(propertyName, klass);
          }
          final ValueGetter getter = info.getter;
          boolean _equals_1 = Objects.equal(getter, null);
          if (_equals_1) {
            throw new NoGetter(propertyName, klass);
          }
          Object oldValue = getter.getValue(object);
          method.invoke(object, value);
          return oldValue;
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
      
      public Class<?> getType() {
        Class<?>[] _parameterTypes = method.getParameterTypes();
        return _parameterTypes[0];
      }
      
      public String getName() {
        return propertyName;
      }
    };
  }
  
  private static ValueGetter createMethodValueGetter(final Method method) {
    Class<?>[] _parameterTypes = method.getParameterTypes();
    int _length = _parameterTypes.length;
    boolean _greaterThan = (_length > 0);
    if (_greaterThan) {
      return null;
    }
    String name = null;
    boolean _and = false;
    String _name = method.getName();
    int _length_1 = _name.length();
    boolean _greaterThan_1 = (_length_1 > 3);
    if (!_greaterThan_1) {
      _and = false;
    } else {
      String _name_1 = method.getName();
      String _substring = _name_1.substring(0, 3);
      boolean _equals = Objects.equal("get", _substring);
      _and = _equals;
    }
    if (_and) {
      String _name_2 = method.getName();
      String _substring_1 = _name_2.substring(3);
      String _firstLower = StringExtensions.toFirstLower(_substring_1);
      name = _firstLower;
    }
    boolean _and_1 = false;
    boolean _and_2 = false;
    boolean _equals_1 = Objects.equal(name, null);
    if (!_equals_1) {
      _and_2 = false;
    } else {
      String _name_3 = method.getName();
      int _length_2 = _name_3.length();
      boolean _greaterThan_2 = (_length_2 > 2);
      _and_2 = _greaterThan_2;
    }
    if (!_and_2) {
      _and_1 = false;
    } else {
      String _name_4 = method.getName();
      String _substring_2 = _name_4.substring(0, 2);
      boolean _equals_2 = Objects.equal("is", _substring_2);
      _and_1 = _equals_2;
    }
    if (_and_1) {
      boolean _or = false;
      Class<?> _returnType = method.getReturnType();
      boolean _equals_3 = Objects.equal(_returnType, Boolean.class);
      if (_equals_3) {
        _or = true;
      } else {
        Class<?> _returnType_1 = method.getReturnType();
        boolean _equals_4 = Objects.equal(_returnType_1, Boolean.TYPE);
        _or = _equals_4;
      }
      if (_or) {
        String _name_5 = method.getName();
        String _substring_3 = _name_5.substring(2);
        String _firstLower_1 = StringExtensions.toFirstLower(_substring_3);
        name = _firstLower_1;
      }
    }
    boolean _equals_5 = Objects.equal(name, null);
    if (_equals_5) {
      return null;
    }
    final String propertyName = name;
    return new ValueGetter() {
      public Class<?> getType() {
        return method.getReturnType();
      }
      
      public String getName() {
        return propertyName;
      }
      
      public Object getValue(final Object object) {
        try {
          return method.invoke(object);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
  }
  
  private static ValueGetter createFieldValueGetter(final Field field) {
    return new ValueGetter() {
      public Object getValue(final Object object) {
        try {
          return field.get(object);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
      
      public Class<?> getType() {
        return field.getType();
      }
      
      public String getName() {
        return field.getName();
      }
    };
  }
  
  private static ValueSetter createFieldValueSetter(final Field field, final Map<String, PropFactory.AccessorInfo> infoMap, final Class<?> klass) {
    return new ValueSetter() {
      public Class<?> getType() {
        return field.getType();
      }
      
      public String getName() {
        return field.getName();
      }
      
      public Object setValue(final Object object, final Object value) {
        try {
          String _name = field.getName();
          PropFactory.AccessorInfo info = infoMap.get(_name);
          boolean _equals = Objects.equal(info, null);
          if (_equals) {
            String _name_1 = field.getName();
            throw new NoGetter(_name_1, klass);
          }
          ValueGetter getter = info.getter;
          boolean _equals_1 = Objects.equal(getter, null);
          if (_equals_1) {
            String _name_2 = field.getName();
            throw new NoGetter(_name_2, klass);
          }
          Object oldValue = getter.getValue(object);
          field.set(object, value);
          return oldValue;
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    };
  }
  
  private static boolean readSetterOptions(final PropFactory.AccessorInfo info, final Method method) {
    Skip _annotation = method.<Skip>getAnnotation(Skip.class);
    boolean _notEquals = (!Objects.equal(_annotation, null));
    return info.skip = _notEquals;
  }
  
  private static boolean readFieldOptions(final PropFactory.AccessorInfo info, final Field field) {
    boolean _xblockexpression = false;
    {
      Skip _annotation = field.<Skip>getAnnotation(Skip.class);
      boolean _notEquals = (!Objects.equal(_annotation, null));
      info.skip = _notEquals;
      int _modifiers = field.getModifiers();
      boolean _isFinal = Modifier.isFinal(_modifiers);
      _xblockexpression = info.fin = _isFinal;
    }
    return _xblockexpression;
  }
  
  private static boolean readGetterOptions(final PropFactory.AccessorInfo info, final Method method) {
    Skip _annotation = method.<Skip>getAnnotation(Skip.class);
    boolean _notEquals = (!Objects.equal(_annotation, null));
    return info.skip = _notEquals;
  }
}
