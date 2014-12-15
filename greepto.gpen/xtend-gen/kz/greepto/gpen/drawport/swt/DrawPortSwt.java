package kz.greepto.gpen.drawport.swt;

import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.Set;
import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.FontDef;
import kz.greepto.gpen.drawport.Geom;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.RectGeom;
import kz.greepto.gpen.drawport.Size;
import kz.greepto.gpen.drawport.StrGeom;
import kz.greepto.gpen.drawport.Style;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.drawport.swt.FontPreparator;
import kz.greepto.gpen.drawport.swt.GcAlreadyDisposed;
import kz.greepto.gpen.drawport.swt.GcSource;
import kz.greepto.gpen.drawport.swt.SwtGeom;
import kz.greepto.gpen.drawport.swt.SwtRectGeom;
import kz.greepto.gpen.drawport.swt.SwtStrGeom;
import kz.greepto.gpen.drawport.swt.SwtStyle;
import kz.greepto.gpen.util.FontInfo;
import kz.greepto.gpen.util.FontManager;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class DrawPortSwt implements DrawPort, FontPreparator {
  private final GcSource gcSource;
  
  private final Set<GC> gcSet;
  
  private final boolean top;
  
  private final GC gc;
  
  private final SwtStyle style;
  
  public static DrawPort fromGcCreator(final GcSource gcSource) {
    HashSet<GC> gcSet = new HashSet<GC>();
    GC gc = gcSource.createGC();
    return new DrawPortSwt(gcSource, gcSet, true, gc);
  }
  
  private DrawPortSwt(final GcSource gcSource, final Set<GC> gcSet, final boolean top, final GC gc) {
    this.gcSource = gcSource;
    this.gcSet = gcSet;
    this.top = top;
    this.gc = gc;
    SwtStyle _swtStyle = new SwtStyle(gc, gcSource);
    this.style = _swtStyle;
  }
  
  public void dispose() {
    this.gc.dispose();
    if (this.top) {
      final Procedure1<GC> _function = new Procedure1<GC>() {
        public void apply(final GC it) {
          it.dispose();
        }
      };
      IterableExtensions.<GC>forEach(this.gcSet, _function);
      this.gcSet.clear();
    }
  }
  
  public DrawPort copy() {
    boolean _isDisposed = this.gc.isDisposed();
    if (_isDisposed) {
      throw new GcAlreadyDisposed();
    }
    GC newGC = this.gcSource.createGC();
    DrawPortSwt ret = new DrawPortSwt(this.gcSource, this.gcSet, false, newGC);
    DrawPortSwt.copyParams(this, ret);
    return ret;
  }
  
  private static void copyParams(final DrawPortSwt from, final DrawPortSwt to) {
    Color _background = from.gc.getBackground();
    to.gc.setBackground(_background);
    Color _foreground = from.gc.getForeground();
    to.gc.setForeground(_foreground);
    Font _font = from.gc.getFont();
    to.gc.setFont(_font);
    to.font.assign(from.font);
    int _alpha = from.gc.getAlpha();
    to.gc.setAlpha(_alpha);
    Pattern _backgroundPattern = from.gc.getBackgroundPattern();
    to.gc.setBackgroundPattern(_backgroundPattern);
    Pattern _foregroundPattern = from.gc.getForegroundPattern();
    to.gc.setForegroundPattern(_foregroundPattern);
    boolean _isClipped = from.gc.isClipped();
    if (_isClipped) {
      Rectangle _clipping = from.gc.getClipping();
      to.gc.setClipping(_clipping);
    } else {
      to.gc.setClipping(((Rectangle) null));
    }
  }
  
  private final FontDef font = new FontDef();
  
  private FontDef appliedFont = null;
  
  public FontDef font() {
    return this.font;
  }
  
  public void prepareFont() {
    boolean _equals = Objects.equal(this.font, this.appliedFont);
    if (_equals) {
      return;
    }
    FontDef _copy = this.font.copy();
    this.appliedFont = _copy;
    FontManager _fm = this.gcSource.fm();
    String _family = this.appliedFont.getFamily();
    int _height = this.appliedFont.getHeight();
    boolean _isBold = this.appliedFont.isBold();
    boolean _isItalic = this.appliedFont.isItalic();
    FontInfo _create = FontInfo.create(_family, _height, _isBold, _isItalic);
    Font _byFontInfo = _fm.byFontInfo(_create);
    this.gc.setFont(_byFontInfo);
  }
  
  public Geom from(final int x, final int y) {
    Vec2 _vec2 = new Vec2(x, y);
    return this.from(_vec2);
  }
  
  public Geom from(final Vec2 from) {
    return new SwtGeom(this.gc, from);
  }
  
  public RectGeom from(final Rect rect) {
    Vec2 _point = rect.getPoint();
    Size _size = rect.getSize();
    return new SwtRectGeom(this.gc, _point, _size);
  }
  
  public void setFont(final FontDef font) {
    this.font.assign(font);
  }
  
  public StrGeom str(final String str) {
    return new SwtStrGeom(this.gc, str, this);
  }
  
  public Style style() {
    return this.style;
  }
  
  public void clearClipping() {
    this.gc.setClipping(((Rectangle) null));
  }
}
