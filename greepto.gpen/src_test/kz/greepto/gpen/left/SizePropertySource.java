package kz.greepto.gpen.left;

import kz.greepto.gpen.editors.gpen.model.RectFigure;
import kz.greepto.gpen.util.Repainter;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class SizePropertySource implements IPropertySource {
  
  protected RectFigure element;
  protected Repainter repainter;
  
  public static String ID_WIDTH = "Width"; //$NON-NLS-1$
  public static String ID_HEIGHT = "Height"; //$NON-NLS-1$
  protected static IPropertyDescriptor[] descriptors;
  
  static {
    descriptors = new IPropertyDescriptor[] { //
    new TextPropertyDescriptor(ID_WIDTH, "width"),//
        new TextPropertyDescriptor(ID_HEIGHT, "height") //
    };
  }
  
  public SizePropertySource(RectFigure element, Repainter repainter) {
    this.element = element;
    this.repainter = repainter;
  }
  
  public Object getEditableValue() {
    return this;
  }
  
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return descriptors;
  }
  
  public Object getPropertyValue(Object propName) {
    if (ID_WIDTH.equals(propName)) {
      return "" + element.getWidth();
    }
    if (ID_HEIGHT.equals(propName)) {
      return "" + element.getHeight();
    }
    return null;
  }
  
  /**
   * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
   */
  public boolean isPropertySet(Object propName) {
    if (ID_WIDTH.equals(propName) || ID_HEIGHT.equals(propName)) return true;
    return false;
  }
  
  public void resetPropertyValue(Object propName) {}
  
  public void setPropertyValue(Object propName, Object value) {
    if (ID_WIDTH.equals(propName)) {
      Integer newInt = new Integer((String)value);
      element.setWidth(newInt.intValue());
      repainter.repaint();
      return;
    }
    if (ID_HEIGHT.equals(propName)) {
      Integer newInt = new Integer((String)value);
      element.setHeight(newInt.intValue());
      repainter.repaint();
      return;
    }
  }
  
}