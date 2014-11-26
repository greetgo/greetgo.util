package kz.greepto.gpen.editors.gpen.model.visitor;

import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.Scene;
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class VisitorPaint implements FigureVisitor<Void> {
  private final GC gc;
  
  public VisitorPaint(final GC gc) {
    this.gc = gc;
  }
  
  public Void visitScene(final Scene scene) {
    Object _xblockexpression = null;
    {
      final Procedure1<IdFigure> _function = new Procedure1<IdFigure>() {
        public void apply(final IdFigure it) {
          it.<Void>visit(VisitorPaint.this);
        }
      };
      IterableExtensions.<IdFigure>forEach(scene.list, _function);
      _xblockexpression = null;
    }
    return ((Void)_xblockexpression);
  }
  
  public Void visitLabel(final Label label) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Void visitCombo(final Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Void visitButton(final Button button) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
