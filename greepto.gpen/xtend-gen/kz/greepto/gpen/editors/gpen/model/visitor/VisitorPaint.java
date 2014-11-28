package kz.greepto.gpen.editors.gpen.model.visitor;

import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.Scene;
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor;
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorSizer;
import kz.greepto.gpen.editors.gpen.style.LabelStyle;
import kz.greepto.gpen.editors.gpen.style.PaintStatus;
import kz.greepto.gpen.util.Rect;
import org.eclipse.swt.graphics.Point;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class VisitorPaint implements FigureVisitor<Void> {
  private final VisitorSizer sizer;
  
  public Point mouse;
  
  public VisitorPaint(final VisitorSizer sizer) {
    this.sizer = sizer;
  }
  
  public Void visitScene(final Scene scene) {
    Object _xblockexpression = null;
    {
      final Procedure1<IdFigure> _function = new Procedure1<IdFigure>() {
        public void apply(final IdFigure it) {
          it.<Void>operator_elvis(VisitorPaint.this);
        }
      };
      IterableExtensions.<IdFigure>forEach(scene.list, _function);
      _xblockexpression = null;
    }
    return ((Void)_xblockexpression);
  }
  
  public Void visitLabel(final Label label) {
    Object _xblockexpression = null;
    {
      Rect size = label.<Rect>operator_elvis(this.sizer);
      PaintStatus _xifexpression = null;
      boolean _contains = size.contains(this.mouse);
      if (_contains) {
        _xifexpression = PaintStatus.hover();
      } else {
        _xifexpression = PaintStatus.normal();
      }
      PaintStatus ps = _xifexpression;
      LabelStyle calc = this.sizer.styleCalc.calcForLabel(label, ps);
      this.sizer.gc.setForeground(calc.color);
      this.sizer.gc.setFont(calc.font);
      int _x = label.getX();
      int _y = label.getY();
      this.sizer.gc.drawText(label.text, _x, _y, true);
      _xblockexpression = null;
    }
    return ((Void)_xblockexpression);
  }
  
  public Void visitCombo(final Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Void visitButton(final Button button) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
