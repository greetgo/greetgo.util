package kz.greepto.gpen.editors.gpen;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class GpenCanvas extends Canvas implements MouseListener, MouseMoveListener, MouseTrackListener {
  public GpenCanvas(final Composite parent, final int style) {
    super(parent, style);
    final PaintListener _function = new PaintListener() {
      public void paintControl(final PaintEvent e) {
        GpenCanvas.this.paintCanvas(e);
      }
    };
    this.addPaintListener(_function);
    this.addMouseListener(this);
    this.addMouseMoveListener(this);
    this.addMouseTrackListener(this);
  }
  
  private String paintStr = "You can draw text directly on a canvas";
  
  public void paintCanvas(final PaintEvent e) {
    Rectangle rect = ((Canvas) e.widget).getBounds();
    Color _systemColor = e.display.getSystemColor(SWT.COLOR_RED);
    e.gc.setForeground(_systemColor);
    e.gc.drawFocus(5, 5, (rect.width - 10), (rect.height - 10));
    e.gc.drawText(this.paintStr, 60, 60);
  }
  
  public void mouseDoubleClick(final MouseEvent e) {
    InputOutput.<String>println(("double " + e));
    String _paintStr = this.paintStr;
    this.paintStr = (_paintStr + "a");
    this.redraw();
  }
  
  public void mouseDown(final MouseEvent e) {
  }
  
  public void mouseUp(final MouseEvent e) {
  }
  
  public void mouseMove(final MouseEvent e) {
    GC gc = new GC(this);
    gc.drawPoint(e.x, e.y);
    gc.dispose();
  }
  
  public void mouseEnter(final MouseEvent e) {
  }
  
  public void mouseExit(final MouseEvent e) {
  }
  
  public void mouseHover(final MouseEvent e) {
  }
}
