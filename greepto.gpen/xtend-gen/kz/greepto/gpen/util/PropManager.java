package kz.greepto.gpen.util;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kz.greepto.gpen.util.NoProperty;
import kz.greepto.gpen.util.PropGetter;
import kz.greepto.gpen.util.PropSetter;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class PropManager {
  @Data
  public static class NameType {
    private final String name;
    
    private final Class<?> type;
    
    public NameType(final String name, final Class<?> type) {
      super();
      this.name = name;
      this.type = type;
    }
    
    @Override
    @Pure
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
      result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
      return result;
    }
    
    @Override
    @Pure
    public boolean equals(final Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PropManager.NameType other = (PropManager.NameType) obj;
      if (this.name == null) {
        if (other.name != null)
          return false;
      } else if (!this.name.equals(other.name))
        return false;
      if (this.type == null) {
        if (other.type != null)
          return false;
      } else if (!this.type.equals(other.type))
        return false;
      return true;
    }
    
    @Override
    @Pure
    public String toString() {
      ToStringBuilder b = new ToStringBuilder(this);
      b.add("name", this.name);
      b.add("type", this.type);
      return b.toString();
    }
    
    @Pure
    public String getName() {
      return this.name;
    }
    
    @Pure
    public Class<?> getType() {
      return this.type;
    }
  }
  
  private Object object;
  
  public PropManager() {
  }
  
  public PropManager(final Object object) {
    this.object = object;
  }
  
  private Map<String, PropGetter> getterMap = null;
  
  private Map<String, PropSetter> setterMap = null;
  
  private Map<String, Class<?>> classMap = null;
  
  public void setObject(final Object object) {
    this.object = object;
    this.getterMap = null;
    this.setterMap = null;
    this.classMap = null;
  }
  
  public Map<String, Class<?>> meta() {
    boolean _equals = Objects.equal(this.classMap, null);
    if (_equals) {
      this.prepare();
    }
    return ImmutableMap.<String, Class<?>>copyOf(this.classMap);
  }
  
  public Object get(final String name) {
    Object _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.classMap, null);
      if (_equals) {
        this.prepare();
      }
      PropGetter _get = this.getterMap.get(name);
      _xblockexpression = _get.get();
    }
    return _xblockexpression;
  }
  
  public void set(final String name, final Object value) {
    boolean _equals = Objects.equal(this.classMap, null);
    if (_equals) {
      this.prepare();
    }
    final PropSetter setter = this.setterMap.get(name);
    boolean _equals_1 = Objects.equal(setter, null);
    if (_equals_1) {
      throw new NoProperty(name);
    }
    setter.set(value);
  }
  
  public void prepare() {
    boolean _equals = Objects.equal(this.object, null);
    if (_equals) {
      throw new NullPointerException("object == null");
    }
    HashMap<String, Class<?>> _hashMap = new HashMap<String, Class<?>>();
    this.classMap = _hashMap;
    HashMap<String, PropGetter> _hashMap_1 = new HashMap<String, PropGetter>();
    this.getterMap = _hashMap_1;
    HashMap<String, PropSetter> _hashMap_2 = new HashMap<String, PropSetter>();
    this.setterMap = _hashMap_2;
    Class<?> _class = this.object.getClass();
    Field[] _fields = _class.getFields();
    for (final Field field : _fields) {
      {
        String _name = field.getName();
        Class<?> _type = field.getType();
        this.classMap.put(_name, _type);
        String _name_1 = field.getName();
        final PropGetter _function = new PropGetter() {
          public Object get() {
            try {
              return field.get(PropManager.this.object);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        this.getterMap.put(_name_1, _function);
        String _name_2 = field.getName();
        final PropSetter _function_1 = new PropSetter() {
          public void set(final Object it) {
            try {
              field.set(PropManager.this.object, it);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        this.setterMap.put(_name_2, _function_1);
      }
    }
    Class<?> _class_1 = this.object.getClass();
    Method[] _methods = _class_1.getMethods();
    for (final Method method : _methods) {
      this.comformMethod(method);
    }
  }
  
  public void comformMethod(final Method method) {
    {
      PropManager.NameType nt = PropManager.getGetterNameType(method);
      boolean _notEquals = (!Objects.equal(nt, null));
      if (_notEquals) {
        this.classMap.put(nt.name, nt.type);
        final PropGetter _function = new PropGetter() {
          public Object get() {
            try {
              return method.invoke(PropManager.this.object);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        this.getterMap.put(nt.name, _function);
        return;
      }
    }
    {
      PropManager.NameType nt = PropManager.getSetterNameType(method);
      boolean _notEquals = (!Objects.equal(nt, null));
      if (_notEquals) {
        this.classMap.put(nt.name, nt.type);
        final PropSetter _function = new PropSetter() {
          public void set(final Object it) {
            try {
              method.invoke(PropManager.this.object, it);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        };
        this.setterMap.put(nt.name, _function);
        return;
      }
    }
  }
  
  public static PropManager.NameType getGetterNameType(final Method method) {
    Class<?>[] _parameterTypes = method.getParameterTypes();
    int _size = ((List<Class<?>>)Conversions.doWrapArray(_parameterTypes)).size();
    boolean _greaterThan = (_size > 0);
    if (_greaterThan) {
      return null;
    }
    String _name = method.getName();
    boolean _startsWith = _name.startsWith("get");
    boolean _not = (!_startsWith);
    if (_not) {
      return null;
    }
    String _name_1 = method.getName();
    String _substring = _name_1.substring(3);
    String name = StringExtensions.toFirstLower(_substring);
    Class<?> _returnType = method.getReturnType();
    return new PropManager.NameType(name, _returnType);
  }
  
  public static PropManager.NameType getSetterNameType(final Method method) {
    Class<?>[] _parameterTypes = method.getParameterTypes();
    int _size = ((List<Class<?>>)Conversions.doWrapArray(_parameterTypes)).size();
    boolean _notEquals = (_size != 1);
    if (_notEquals) {
      return null;
    }
    Class<?>[] _parameterTypes_1 = method.getParameterTypes();
    Class<?> type = _parameterTypes_1[0];
    String _name = method.getName();
    boolean _startsWith = _name.startsWith("set");
    boolean _not = (!_startsWith);
    if (_not) {
      return null;
    }
    String _name_1 = method.getName();
    String _substring = _name_1.substring(3);
    String name = StringExtensions.toFirstLower(_substring);
    return new PropManager.NameType(name, type);
  }
}
