package kz.greepto.gpen.util

import org.eclipse.xtend.lib.annotations.Data
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.SWT

@Data
class FontInfo {
  public String name
  public int height
  public boolean bold
  public boolean italic

  public static def FontInfo create(String name, int height, boolean bold, boolean italic) {
    return new FontInfo(name, height, bold, italic)
  }

  public static def FontInfo normal(String name, int height) {
    return create(name, height, false, false)
  }

  public static def FontInfo bold(String name, int height) {
    return create(name, height, true, false)
  }

  public static def FontInfo italic(String name, int height) {
    return create(name, height, false, true)
  }

  public static def FontInfo boldItalic(String name, int height) {
    return create(name, height, true, true)
  }

  def FontData getFontData() {
    var style = SWT.NORMAL
    if (bold) style += SWT.BOLD
    if (italic) style += SWT.ITALIC
    return new FontData(name, height, style)
  }
}
