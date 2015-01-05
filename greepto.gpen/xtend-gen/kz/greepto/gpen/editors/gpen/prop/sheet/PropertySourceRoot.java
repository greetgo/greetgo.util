package kz.greepto.gpen.editors.gpen.prop.sheet;

import com.google.common.base.Objects;
import java.util.Map;
import kz.greepto.gpen.editors.gpen.GpenSelection;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.PropOptions;
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
  
  private final GpenSelection selection;
  
  private GpenPropertyDescriptor[] propertyDescriptors = {};
  
  private PropList propList = PropList.empty();
  
  private final Map<Object, GpenPropertyDescriptor> descriptorMap = CollectionLiterals.<Object, GpenPropertyDescriptor>newHashMap();
  
  public PropertySourceRoot(final GpenSelection selection) {
    this.selection = selection;
    this.calcDescriptors();
  }
  
  public void calcDescriptors() {
    int _size = this.selection.ids.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return;
    }
    PropList _propList = this.selection.getPropList();
    this.propList = _propList;
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
    boolean _not = (!_isReadonly);
    if (_not) {
      Class<?> _type = pa.getType();
      boolean _equals = Objects.equal(_type, String.class);
      if (_equals) {
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
    }
    return new DescriptorRo(pa);
  }
}
