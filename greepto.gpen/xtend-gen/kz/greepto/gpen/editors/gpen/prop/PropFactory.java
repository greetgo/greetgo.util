package kz.greepto.gpen.editors.gpen.prop;

import com.google.common.base.Objects;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import kz.greepto.gpen.editors.gpen.action.Oper;
import kz.greepto.gpen.editors.gpen.action.OperGroup;
import kz.greepto.gpen.editors.gpen.action.OperModify;
import kz.greepto.gpen.editors.gpen.prop.NoGetter;
import kz.greepto.gpen.editors.gpen.prop.Polilines;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
import kz.greepto.gpen.editors.gpen.prop.SceneWorker;
import kz.greepto.gpen.editors.gpen.prop.SetOrderWeight;
import kz.greepto.gpen.editors.gpen.prop.Skip;
import kz.greepto.gpen.editors.gpen.prop.ValueGetter;
import kz.greepto.gpen.editors.gpen.prop.ValueSetter;
import kz.greepto.gpen.util.Handler;
import kz.greepto.gpen.util.HandlerKiller;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
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
    
    private int orderWeightForSet = 1000;
    
    public AccessorInfo(final String name, final Object object, final SceneWorker sceneWorker) {
      this.name = name;
      this.object = object;
      this.sceneWorker = sceneWorker;
    }
    
    private Class<?> type;
    
    public Class<?> getType() {
      return this.type;
    }
    
    public boolean compatibleWith(final PropAccessor with) {
      String _name = with.getName();
      boolean _notEquals = (!Objects.equal(this.name, _name));
      if (_notEquals) {
        return false;
      }
      Class<?> _type = with.getType();
      return PropFactory.AccessorInfo.compatibles(this.type, _type);
    }
    
    public static boolean compatibles(final Class<?> class1, final Class<?> class2) {
      Class<?> c1 = class1;
      Class<?> c2 = class2;
      boolean _isPrimitive = c1.isPrimitive();
      if (_isPrimitive) {
        Class<?> _wrap = PropFactory.AccessorInfo.wrap(c1);
        c1 = _wrap;
      }
      boolean _isPrimitive_1 = c2.isPrimitive();
      if (_isPrimitive_1) {
        Class<?> _wrap_1 = PropFactory.AccessorInfo.wrap(c2);
        c2 = _wrap_1;
      }
      return (c1 == c2);
    }
    
    public static Class<?> wrap(final Class<?> c) {
      boolean _isPrimitive = c.isPrimitive();
      boolean _not = (!_isPrimitive);
      if (_not) {
        throw new RuntimeException(("Wrapping can be only for primitive class: " + c));
      }
      boolean _tripleEquals = (c == Byte.TYPE);
      if (_tripleEquals) {
        return Byte.class;
      }
      boolean _tripleEquals_1 = (c == Short.TYPE);
      if (_tripleEquals_1) {
        return Short.class;
      }
      boolean _tripleEquals_2 = (c == Integer.TYPE);
      if (_tripleEquals_2) {
        return Integer.class;
      }
      boolean _tripleEquals_3 = (c == Long.TYPE);
      if (_tripleEquals_3) {
        return Long.class;
      }
      boolean _tripleEquals_4 = (c == Float.TYPE);
      if (_tripleEquals_4) {
        return Float.class;
      }
      boolean _tripleEquals_5 = (c == Double.TYPE);
      if (_tripleEquals_5) {
        return Double.class;
      }
      boolean _tripleEquals_6 = (c == Character.TYPE);
      if (_tripleEquals_6) {
        return Character.class;
      }
      boolean _tripleEquals_7 = (c == Boolean.TYPE);
      if (_tripleEquals_7) {
        return Boolean.class;
      }
      throw new RuntimeException(("Unknown primitive class: " + c));
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
    
    public ValueGetter getGetter() {
      return this.getter;
    }
    
    public ValueSetter getSetter() {
      return this.setter;
    }
    
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
      if (this.polilines) {
        sb.append(" POLILINES");
      }
      String _simpleName = this.type.getSimpleName();
      String _plus = (" : " + _simpleName);
      sb.append(_plus);
      return sb.toString();
    }
    
    public Object getValue() {
      return this.getter.getValue(this.object);
    }
    
    private boolean polilines = false;
    
    private final PropOptions options = new PropOptions() {
      public boolean isPolilines() {
        return AccessorInfo.this.polilines;
      }
      
      public boolean isReadonly() {
        return Objects.equal(AccessorInfo.this.setter, null);
      }
      
      public int orderWeightForSet() {
        return AccessorInfo.this.orderWeightForSet;
      }
      
      public PropOptions operator_add(final PropOptions a) {
        return this.operator_plus(a);
      }
      
      public PropOptions operator_plus(final PropOptions a) {
        return PropFactory.plusOptions(this, a);
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
      OperModify ret = new OperModify(this.setter, newValue, _takeId);
      PropList _setterList = this.coll.getSetterList();
      ret.modiPropList = _setterList;
      return ret;
    }
    
    private PropFactory.Collector coll;
    
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
    
    public PropAccessor operator_add(final PropAccessor a) {
      return this.operator_plus(a);
    }
    
    public PropAccessor operator_plus(final PropAccessor a) {
      return this.plusPropAccessor(this, a);
    }
    
    public PropAccessor plusPropAccessor(final PropAccessor x, final PropAccessor y) {
      boolean _compatibleWith = x.compatibleWith(y);
      boolean _not = (!_compatibleWith);
      if (_not) {
        throw new IllegalArgumentException("Plusing of uncompatible AccessorInfo");
      }
      return new PropAccessor() {
        public Class<?> getType() {
          return x.getType();
        }
        
        public String getName() {
          return x.getName();
        }
        
        public Object getValue() {
          Object _xblockexpression = null;
          {
            Object v = x.getValue();
            Object _xifexpression = null;
            Object _value = y.getValue();
            boolean _equals = Objects.equal(v, _value);
            if (_equals) {
              _xifexpression = v;
            } else {
              _xifexpression = PropAccessor.DIFF_VALUES;
            }
            _xblockexpression = _xifexpression;
          }
          return _xblockexpression;
        }
        
        public ValueGetter getGetter() {
          return x.getGetter();
        }
        
        public PropOptions getOptions() {
          PropOptions _options = x.getOptions();
          PropOptions _options_1 = y.getOptions();
          return _options.operator_plus(_options_1);
        }
        
        public void setValue(final Object value) {
          boolean _tripleEquals = (PropAccessor.DIFF_VALUES == value);
          if (_tripleEquals) {
            return;
          }
          Oper oper = this.getSettingOper(value);
          boolean _tripleEquals_1 = (oper == null);
          if (_tripleEquals_1) {
            return;
          }
          AccessorInfo.this.sceneWorker.applyOper(oper);
        }
        
        public Oper getSettingOper(final Object newValue) {
          Oper xsetter = x.getSettingOper(newValue);
          Oper ysetter = y.getSettingOper(newValue);
          boolean _tripleEquals = (xsetter == null);
          if (_tripleEquals) {
            return ysetter;
          }
          boolean _tripleEquals_1 = (ysetter == null);
          if (_tripleEquals_1) {
            return xsetter;
          }
          return new OperGroup(Collections.<Oper>unmodifiableList(CollectionLiterals.<Oper>newArrayList(xsetter, ysetter)), "Group2");
        }
        
        public ValueSetter getSetter() {
          return x.getSetter();
        }
        
        public HandlerKiller addChangeHandler(final Handler handler) {
          return AccessorInfo.this.sceneWorker.addChangeHandler(handler);
        }
        
        public boolean compatibleWith(final PropAccessor with) {
          boolean _and = false;
          boolean _compatibleWith = x.compatibleWith(with);
          if (!_compatibleWith) {
            _and = false;
          } else {
            boolean _compatibleWith_1 = y.compatibleWith(with);
            _and = _compatibleWith_1;
          }
          return _and;
        }
        
        public PropAccessor operator_add(final PropAccessor a) {
          return this.operator_plus(a);
        }
        
        public PropAccessor operator_plus(final PropAccessor a) {
          return AccessorInfo.this.plusPropAccessor(this, a);
        }
      };
    }
  }
  
  private static class Collector {
    private final HashMap<String, PropFactory.AccessorInfo> map = new HashMap<String, PropFactory.AccessorInfo>();
    
    public void put(final String name, final PropFactory.AccessorInfo info) {
      this.map.put(name, info);
      this.__setterList__ = null;
    }
    
    private PropList __setterList__ = null;
    
    public PropFactory.AccessorInfo get(final String name) {
      return this.map.get(name);
    }
    
    public PropList getSetterList() {
      boolean _tripleEquals = (this.__setterList__ == null);
      if (_tripleEquals) {
        Collection<PropFactory.AccessorInfo> _values = this.map.values();
        final Function1<PropFactory.AccessorInfo, Boolean> _function = new Function1<PropFactory.AccessorInfo, Boolean>() {
          public Boolean apply(final PropFactory.AccessorInfo it) {
            boolean _isReadonly = it.options.isReadonly();
            return Boolean.valueOf((!_isReadonly));
          }
        };
        Iterable<PropFactory.AccessorInfo> _filter = IterableExtensions.<PropFactory.AccessorInfo>filter(_values, _function);
        final Comparator<PropAccessor> _function_1 = new Comparator<PropAccessor>() {
          public int compare(final PropAccessor a, final PropAccessor b) {
            PropOptions _options = a.getOptions();
            int _orderWeightForSet = _options.orderWeightForSet();
            PropOptions _options_1 = b.getOptions();
            int _orderWeightForSet_1 = _options_1.orderWeightForSet();
            int sow = (_orderWeightForSet - _orderWeightForSet_1);
            if ((sow != 0)) {
              return sow;
            }
            String _name = a.getName();
            String _name_1 = b.getName();
            return _name.compareTo(_name_1);
          }
        };
        List<PropFactory.AccessorInfo> _sortWith = IterableExtensions.<PropFactory.AccessorInfo>sortWith(_filter, _function_1);
        final Function1<PropFactory.AccessorInfo, PropAccessor> _function_2 = new Function1<PropFactory.AccessorInfo, PropAccessor>() {
          public PropAccessor apply(final PropFactory.AccessorInfo it) {
            return ((PropAccessor) it);
          }
        };
        List<PropAccessor> _map = ListExtensions.<PropFactory.AccessorInfo, PropAccessor>map(_sortWith, _function_2);
        PropList _from = PropList.from(_map);
        this.__setterList__ = _from;
      }
      return this.__setterList__;
    }
  }
  
  public static PropList parseObject(final Object object, final SceneWorker sceneWorker) {
    final PropFactory.Collector collector = new PropFactory.Collector();
    Class<?> _class = object.getClass();
    Field[] _fields = _class.getFields();
    for (final Field f : _fields) {
      PropFactory.appendField(collector, f, object, sceneWorker);
    }
    Class<?> _class_1 = object.getClass();
    Method[] _methods = _class_1.getMethods();
    for (final Method m : _methods) {
      PropFactory.appendMethod(collector, m, object, sceneWorker);
    }
    Collection<PropFactory.AccessorInfo> _values = collector.map.values();
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
  
  public static <T extends Object> PropList parseObjectList(final Collection<T> list, final SceneWorker sceneWorker) {
    PropList ret = null;
    for (final T o : list) {
      {
        PropList u = PropFactory.parseObject(o, sceneWorker);
        boolean _tripleEquals = (ret == null);
        if (_tripleEquals) {
          ret = u;
        } else {
          ret.operator_add(u);
        }
      }
    }
    PropList _xifexpression = null;
    boolean _tripleEquals = (ret == null);
    if (_tripleEquals) {
      _xifexpression = PropList.empty();
    } else {
      _xifexpression = ret;
    }
    return _xifexpression;
  }
  
  private static void appendMethod(final PropFactory.Collector coll, final Method m, final Object object, final SceneWorker sceneWorker) {
    m.setAccessible(true);
    ValueGetter getter = PropFactory.createMethodValueGetter(m);
    boolean _notEquals = (!Objects.equal(getter, null));
    if (_notEquals) {
      String _name = getter.getName();
      PropFactory.AccessorInfo info = coll.get(_name);
      boolean _equals = Objects.equal(info, null);
      if (_equals) {
        String _name_1 = getter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        coll.put(info.name, info);
      }
      info.getter = getter;
      Class<?> _type = getter.getType();
      info.type = _type;
      PropFactory.readGetterOptions(info, m);
      return;
    }
    {
      Class<?> _class = object.getClass();
      ValueSetter setter = PropFactory.createMethodValueSetter(m, coll, _class);
      boolean _equals_1 = Objects.equal(setter, null);
      if (_equals_1) {
        return;
      }
      String _name_2 = setter.getName();
      PropFactory.AccessorInfo info_1 = coll.get(_name_2);
      boolean _equals_2 = Objects.equal(info_1, null);
      if (_equals_2) {
        String _name_3 = setter.getName();
        PropFactory.AccessorInfo _accessorInfo_1 = new PropFactory.AccessorInfo(_name_3, object, sceneWorker);
        info_1 = _accessorInfo_1;
        coll.put(info_1.name, info_1);
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
  
  private static void appendField(final PropFactory.Collector coll, final Field f, final Object object, final SceneWorker sceneWorker) {
    f.setAccessible(true);
    boolean needRead = true;
    {
      ValueGetter getter = PropFactory.createFieldValueGetter(f);
      boolean _equals = Objects.equal(getter, null);
      if (_equals) {
        return;
      }
      String _name = getter.getName();
      PropFactory.AccessorInfo info = coll.get(_name);
      boolean _equals_1 = Objects.equal(info, null);
      if (_equals_1) {
        String _name_1 = getter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        info.coll = coll;
        coll.put(info.name, info);
      }
      info.getter = getter;
      Class<?> _type = getter.getType();
      info.type = _type;
      PropFactory.readFieldOptions(info, f);
      needRead = false;
    }
    {
      Class<?> _class = object.getClass();
      ValueSetter setter = PropFactory.createFieldValueSetter(f, coll, _class);
      boolean _equals = Objects.equal(setter, null);
      if (_equals) {
        return;
      }
      String _name = setter.getName();
      PropFactory.AccessorInfo info = coll.get(_name);
      boolean _equals_1 = Objects.equal(info, null);
      if (_equals_1) {
        String _name_1 = setter.getName();
        PropFactory.AccessorInfo _accessorInfo = new PropFactory.AccessorInfo(_name_1, object, sceneWorker);
        info = _accessorInfo;
        coll.put(info.name, info);
      }
      info.setter = setter;
      Class<?> _type = setter.getType();
      info.type = _type;
      if (needRead) {
        PropFactory.readFieldOptions(info, f);
      }
    }
  }
  
  private static ValueSetter createMethodValueSetter(final Method method, final PropFactory.Collector coll, final Class<?> klass) {
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
          final PropFactory.AccessorInfo info = coll.get(propertyName);
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
  
  private static ValueSetter createFieldValueSetter(final Field field, final PropFactory.Collector coll, final Class<?> klass) {
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
          PropFactory.AccessorInfo info = coll.get(_name);
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
  
  private static void readSetterOptions(final PropFactory.AccessorInfo info, final Method method) {
    Skip _annotation = method.<Skip>getAnnotation(Skip.class);
    boolean _notEquals = (!Objects.equal(_annotation, null));
    info.skip = _notEquals;
    PropFactory.readPolilines(info, method, false);
    PropFactory.readSetOrderWieght(info, method);
  }
  
  private static void readFieldOptions(final PropFactory.AccessorInfo info, final Field field) {
    Skip _annotation = field.<Skip>getAnnotation(Skip.class);
    boolean _notEquals = (!Objects.equal(_annotation, null));
    info.skip = _notEquals;
    int _modifiers = field.getModifiers();
    boolean _isFinal = Modifier.isFinal(_modifiers);
    info.fin = _isFinal;
    PropFactory.readPolilines(info, field, true);
    PropFactory.readSetOrderWieght(info, field);
  }
  
  private static void readGetterOptions(final PropFactory.AccessorInfo info, final Method method) {
    Skip _annotation = method.<Skip>getAnnotation(Skip.class);
    boolean _notEquals = (!Objects.equal(_annotation, null));
    info.skip = _notEquals;
    PropFactory.readPolilines(info, method, true);
    PropFactory.readSetOrderWieght(info, method);
  }
  
  public static void readPolilines(final PropFactory.AccessorInfo info, final AccessibleObject ao, final boolean force) {
    boolean _and = false;
    boolean _isPolilines = info.options.isPolilines();
    if (!_isPolilines) {
      _and = false;
    } else {
      _and = (!force);
    }
    if (_and) {
      return;
    }
    Polilines _annotation = ao.<Polilines>getAnnotation(Polilines.class);
    boolean _tripleNotEquals = (_annotation != null);
    if (_tripleNotEquals) {
      info.polilines = true;
    }
  }
  
  public static void readSetOrderWieght(final PropFactory.AccessorInfo info, final AccessibleObject ao) {
    SetOrderWeight ann = ao.<SetOrderWeight>getAnnotation(SetOrderWeight.class);
    boolean _tripleEquals = (ann == null);
    if (_tripleEquals) {
      return;
    }
    int _value = ann.value();
    info.orderWeightForSet = _value;
  }
  
  private static PropOptions plusOptions(final PropOptions x, final PropOptions y) {
    return new PropOptions() {
      public boolean isPolilines() {
        boolean _or = false;
        boolean _isPolilines = x.isPolilines();
        if (_isPolilines) {
          _or = true;
        } else {
          boolean _isPolilines_1 = y.isPolilines();
          _or = _isPolilines_1;
        }
        return _or;
      }
      
      public boolean isReadonly() {
        boolean _or = false;
        boolean _isReadonly = x.isReadonly();
        if (_isReadonly) {
          _or = true;
        } else {
          boolean _isReadonly_1 = y.isReadonly();
          _or = _isReadonly_1;
        }
        return _or;
      }
      
      public PropOptions operator_add(final PropOptions a) {
        return this.operator_plus(a);
      }
      
      public PropOptions operator_plus(final PropOptions a) {
        return PropFactory.plusOptions(this, a);
      }
      
      public int orderWeightForSet() {
        int _orderWeightForSet = x.orderWeightForSet();
        int _orderWeightForSet_1 = y.orderWeightForSet();
        int _plus = (_orderWeightForSet + _orderWeightForSet_1);
        return (_plus / 2);
      }
    };
  }
}
