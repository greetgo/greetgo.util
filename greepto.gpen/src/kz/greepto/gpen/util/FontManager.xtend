package kz.greepto.gpen.util

import java.util.HashMap
import java.util.Map
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.widgets.Display

import static extension kz.greepto.gpen.util.Exts.*

class FontManager {

  private final Map<FontInfo, Font> data = new HashMap

  def Font byFontInfo(FontInfo fi) {
    var ret = data.get(fi)

    if (ret == null) {
      ret = new Font(Display.current, fi.fontData)
      data << (fi -> ret) << (fi -> ret)
    }

    return ret
  }

  public def HavingName name(String name) {
    new HavingName(this, name)
  }

  public def HavingName arial() {
    name('Arial')
  }

  public static class HavingName {
    private final FontManager owner
    private final String name

    private boolean boldField
    private boolean italicField

    private new(FontManager owner, String name) {
      this.owner = owner
      this.name = name
    }

    public def HavingName b() {
      boldField = true
      return this
    }

    public def HavingName i() {
      italicField = true
      return this
    }

    public def HavingName bold(boolean value) {
      boldField = value
      return this
    }

    public def HavingName italic(boolean value) {
      italicField = value
      return this
    }

    public def Font height(int height) {
      return owner.byFontInfo(new FontInfo(name, height, boldField, italicField))
    }

  }

  def void dispose() {
    data.values.forEach[dispose]
    data.clear
  }

}
