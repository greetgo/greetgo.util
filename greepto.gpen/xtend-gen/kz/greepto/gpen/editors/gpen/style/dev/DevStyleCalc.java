package kz.greepto.gpen.editors.gpen.style.dev;

import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import kz.greepto.gpen.util.ColorManager;
import kz.greepto.gpen.util.FontManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

@SuppressWarnings("all")
public class DevStyleCalc implements StyleCalc {
  private final FontManager fm;
  
  private final ColorManager cm;
  
  public DevStyleCalc(final FontManager fm, final ColorManager cm) {
    this.fm = fm;
    this.cm = cm;
  }
  
  public LabelStyle calcForLabel(final Label label, final PaintStatus ps) {
    final LabelStyle ret = new LabelStyle();
    if (ps.hover) {
      Color _rgb = this.cm.rgb(255, 0, 0);
      ret.color = _rgb;
    } else {
      Color _rgb_1 = this.cm.rgb(0, 0, 0);
      ret.color = _rgb_1;
    }
    FontManager.HavingName _arial = this.fm.arial();
    Font _height = _arial.height(30);
    ret.font = _height;
    return ret;
  }
}
