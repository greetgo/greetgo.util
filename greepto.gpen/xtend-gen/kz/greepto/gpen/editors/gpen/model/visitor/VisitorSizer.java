package kz.greepto.gpen.editors.gpen.model.visitor;

import kz.greepto.gpen.editors.gpen.model.Button;
import kz.greepto.gpen.editors.gpen.model.Combo;
import kz.greepto.gpen.editors.gpen.model.IdFigure;
import kz.greepto.gpen.editors.gpen.model.Label;
import kz.greepto.gpen.editors.gpen.model.Scene;
import kz.greepto.gpen.editors.gpen.model.visitor.FigureVisitor;
import kz.greepto.gpen.util.Rect;
import org.eclipse.swt.graphics.GC;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class VisitorSizer implements FigureVisitor<Rect> {
  private final GC gc;
  
  public final Rect rect = Rect.zero();
  
  public VisitorSizer(final GC gc) {
    this.gc = gc;
  }
  
  public Rect visitScene(final Scene scene) {
    Rect _xblockexpression = null;
    {
      final Procedure1<IdFigure> _function = new Procedure1<IdFigure>() {
        public void apply(final IdFigure it) {
          it.<Rect>visit(VisitorSizer.this);
        }
      };
      IterableExtensions.<IdFigure>forEach(scene.list, _function);
      _xblockexpression = this.rect;
    }
    return _xblockexpression;
  }
  
  public Rect visitLabel(final Label label) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Rect visitCombo(final Combo combo) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Rect visitButton(final Button button) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
