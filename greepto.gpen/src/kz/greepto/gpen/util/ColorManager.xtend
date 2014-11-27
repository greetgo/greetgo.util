package kz.greepto.gpen.util

import java.util.Map
import org.eclipse.swt.graphics.RGB
import org.eclipse.swt.graphics.Color
import java.util.HashMap
import org.eclipse.swt.widgets.Display

class ColorManager {

  private Map<RGB, Color> fColorTable = new HashMap(10);

  public def dispose() {
    for (Color color : fColorTable.values) {
      color.dispose
    }
  }

  public def Color fromRGB(RGB rgb) {
    var color = fColorTable.get(rgb);
    if (color == null) {
      color = new Color(Display.current, rgb);
      fColorTable.put(rgb, color);
    }
    return color;
  }

  public def Color rgb(int red, int green, int blue) {
    return fromRGB(new RGB(red, green, blue))
  }
}