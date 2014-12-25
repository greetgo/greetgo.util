package kz.greepto.gpen.editors.gpen.prop.sheet;

import java.util.ArrayList;
import kz.greepto.gpen.editors.gpen.GpenSelection;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.prop.PropAccessor;
import kz.greepto.gpen.editors.gpen.prop.PropFactory;
import kz.greepto.gpen.editors.gpen.prop.PropList;
import kz.greepto.gpen.editors.gpen.prop.sheet.DescriptorRo;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class PropertySourceRoot implements IPropertySource {
  public Object getEditableValue() {
    return this;
  }
  
  private final GpenSelection selection;
  
  private PropertyDescriptor[] propertyDescriptors = {};
  
  private PropList propList = PropList.empty();
  
  public PropertySourceRoot(final GpenSelection selection) {
    this.selection = selection;
    this.calcDescriptors();
  }
  
  public void calcDescriptors() {
    String id = IterableExtensions.<String>head(this.selection.ids);
    boolean _tripleEquals = (id == null);
    if (_tripleEquals) {
      return;
    }
    IdFigure figure = this.selection.scene.findByIdOrDie(id);
    PropList _parseObject = PropFactory.parseObject(figure, this.selection.sceneWorker);
    this.propList = _parseObject;
    ArrayList<DescriptorRo> list = CollectionLiterals.<DescriptorRo>newArrayList();
    for (final PropAccessor pa : this.propList) {
      String _name = pa.getName();
      String _name_1 = pa.getName();
      DescriptorRo _descriptorRo = new DescriptorRo(_name, _name_1);
      list.add(_descriptorRo);
    }
    final ArrayList<DescriptorRo> _converted_list = (ArrayList<DescriptorRo>)list;
    this.propertyDescriptors = ((PropertyDescriptor[])Conversions.unwrapArray(_converted_list, PropertyDescriptor.class));
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return this.propertyDescriptors;
  }
  
  public Object getPropertyValue(final Object id) {
    PropAccessor _byName = this.propList.byName(((String) id));
    return _byName.getValue();
  }
  
  public boolean isPropertySet(final Object id) {
    return false;
  }
  
  public void resetPropertyValue(final Object id) {
  }
  
  public void setPropertyValue(final Object id, final Object value) {
  }
}
