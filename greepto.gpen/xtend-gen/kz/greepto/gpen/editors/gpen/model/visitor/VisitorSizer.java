package kz.greepto.gpen.editors.gpen.model.visitor;

import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.Scene;
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor;
import kz.greepto.gpen.editors.gpen.style.StyleCalc;
import kz.greepto.gpen.util.Rect;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class VisitorSizer implements FigureVisitor<Rect> {
  final GC gc;
  
  final StyleCalc styleCalc;
  
  public VisitorSizer(final GC gc, final StyleCalc styleCalc) {
    this.gc = gc;
    this.styleCalc = styleCalc;
  }
  
  public Rect visitScene(final Scene scene) {
    final Rect ret = Rect.zero();
    final Procedure1<IdFigure> _function = new Procedure1<IdFigure>() {
      public void apply(final IdFigure it) {
        Rect _elvis = it.<Rect>operator_elvis(VisitorSizer.this);
        ret.operator_add(_elvis);
      }
    };
    IterableExtensions.<IdFigure>forEach(scene.list, _function);
    return ret;
  }
  
  public Rect visitLabel(final Label label) {
    Point size = this.gc.textExtent(label.text);
    Point _point = label.getPoint();
    return Rect.pointSize(_point, size);
  }
  
  public Rect visitButton(final Button button) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Rect visitCombo(final Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
