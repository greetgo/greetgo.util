package kz.greepto.gpen.editors.gpen

import org.eclipse.swt.SWT
import org.eclipse.swt.events.MouseMoveListener
import org.eclipse.swt.events.MouseTrackListener
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.events.MouseListener
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.graphics.GC
import kz.greepto.gpen.editors.gpen.model.Scene

class GpenCanvas extends Canvas implements MouseListener, MouseMoveListener, MouseTrackListener {

  package final Scene scene = new Scene

  public new(Composite parent, int style) {
    super(parent, style);

    addPaintListener [ PaintEvent e |
      paintCanvas(e);
    ];

    addMouseListener(this);
    addMouseMoveListener(this);
    addMouseTrackListener(this);
  }

  var paintStr = 'You can draw text directly on a canvas'

  def paintCanvas(PaintEvent e) {
    var rect = (e.widget as Canvas).bounds;
    e.gc.foreground = e.display.getSystemColor(SWT.COLOR_RED);
    e.gc.drawFocus(5, 5, rect.width - 10, rect.height - 10);
    e.gc.drawText(paintStr, 60, 60);
  }

  override mouseDoubleClick(MouseEvent e) {
    println("double " + e);
    paintStr += 'a'
    redraw
  }

  override mouseDown(MouseEvent e) {
  }

  override mouseUp(MouseEvent e) {
  }

  override mouseMove(MouseEvent e) {
    var gc = new GC(this)
    gc.drawPoint(e.x, e.y)
    gc.dispose()
  }

  override mouseEnter(MouseEvent e) {
  }

  override mouseExit(MouseEvent e) {
  }

  override mouseHover(MouseEvent e) {
  }

}
