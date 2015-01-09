package kz.greepto.gpen.editors.gpen.prop.sheet;

import java.util.Map;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorInt;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorPolilies;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorRo;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorStr;
import kz.greepto.gpen.editors.gpen.prop.sheet.GpenPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PropertySourceRoot implements IPropertySource {
  public Object getEditableValue() {
    return this;
  }
  
  private GpenPropertyDescriptor[] propertyDescriptors = {};
  
  private final PropList propList;
  
  private final Map<Object, GpenPropertyDescriptor> descriptorMap = CollectionLiterals.<Object, GpenPropertyDescriptor>newHashMap();
  
  public PropertySourceRoot(final PropList propList) {
    this.propList = propList;
    this.calcDescriptors();
  }
  
  public void calcDescriptors() {
    final Function1<PropAccessor, GpenPropertyDescriptor> _function = new Function1<PropAccessor, GpenPropertyDescriptor>() {
      public GpenPropertyDescriptor apply(final PropAccessor it) {
        return PropertySourceRoot.descriptorFor(it);
      }
    };
    Iterable<GpenPropertyDescriptor> _map = IterableExtensions.<PropAccessor, GpenPropertyDescriptor>map(this.propList, _function);
    this.propertyDescriptors = ((GpenPropertyDescriptor[])Conversions.unwrapArray(_map, GpenPropertyDescriptor.class));
    for (final GpenPropertyDescriptor gpd : this.propertyDescriptors) {
      Object _id = gpd.getId();
      this.descriptorMap.put(_id, gpd);
    }
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return this.propertyDescriptors;
  }
  
  public Object getPropertyValue(final Object id) {
    GpenPropertyDescriptor _get = this.descriptorMap.get(id);
    return _get.getValue();
  }
  
  public boolean isPropertySet(final Object id) {
    GpenPropertyDescriptor _get = this.descriptorMap.get(id);
    return _get.isPropertySet();
  }
  
  public void resetPropertyValue(final Object id) {
    GpenPropertyDescriptor _get = this.descriptorMap.get(id);
    _get.resetPropertyValue();
  }
  
  public void setPropertyValue(final Object id, final Object value) {
    GpenPropertyDescriptor _get = this.descriptorMap.get(id);
    _get.setValue(value);
  }
  
  private static GpenPropertyDescriptor descriptorFor(final PropAccessor pa) {
    PropOptions _options = pa.getOptions();
    boolean _isReadonly = _options.isReadonly();
    if (_isReadonly) {
      return new DescriptorRo(pa);
    }
    Class<?> _type = pa.getType();
    boolean _tripleEquals = (_type == String.class);
    if (_tripleEquals) {
      PropOptions _options_1 = pa.getOptions();
      boolean _isPolilines = _options_1.isPolilines();
      if (_isPolilines) {
        return new DescriptorPolilies(pa);
      }
      return new DescriptorStr(pa);
    }
    PropOptions _options_2 = pa.getOptions();
    boolean _isPolilines_1 = _options_2.isPolilines();
    if (_isPolilines_1) {
      throw new RuntimeException("Polilines may be only for string field");
    }
    boolean _or = false;
    Class<?> _type_1 = pa.getType();
    boolean _tripleEquals_1 = (_type_1 == Integer.class);
    if (_tripleEquals_1) {
      _or = true;
    } else {
      Class<?> _type_2 = pa.getType();
      boolean _tripleEquals_2 = (_type_2 == Integer.TYPE);
      _or = _tripleEquals_2;
    }
    if (_or) {
      return new DescriptorInt(pa, false);
    }
    boolean _or_1 = false;
    Class<?> _type_3 = pa.getType();
    boolean _tripleEquals_3 = (_type_3 == Long.class);
    if (_tripleEquals_3) {
      _or_1 = true;
    } else {
      Class<?> _type_4 = pa.getType();
      boolean _tripleEquals_4 = (_type_4 == Long.TYPE);
      _or_1 = _tripleEquals_4;
    }
    if (_or_1) {
      return new DescriptorInt(pa, true);
    }
    return new DescriptorRo(pa);
  }
}
