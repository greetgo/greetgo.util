package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.editors.gpen.action.Action
import kz.greepto.gpen.editors.gpen.action.ActionManager
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.visitor.Hit
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPaint
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPlacer
import kz.greepto.gpen.editors.gpen.prop.SceneWorker
import kz.greepto.gpen.editors.gpen.style.dev.DevStyleCalc
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.util.Handler
import kz.greepto.gpen.util.HandlerList
import org.eclipse.swt.SWT
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.events.MouseListener
import org.eclipse.swt.events.MouseMoveListener
import org.eclipse.swt.events.MouseTrackListener
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.graphics.GC
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite
import kz.greepto.gpen.editors.gpen.prop.PropFactory

class GpenCanvas extends Canvas implements MouseListener, MouseMoveListener, MouseTrackListener {

  private Scene originalScene = new Scene
  private Scene scene = new Scene

  final ColorManager colors = new ColorManager
  final FontManager fonts = new FontManager
  final DevStyleCalc styleCalc = new DevStyleCalc(fonts, colors)
  package final SelectionProvider selectionProvider = new SelectionProvider(this)
  final ActionManager actionManager = new ActionManager;

  final HandlerList changeSceneHandlerList = new HandlerList ;

  final SceneWorker sceneWorker = new SceneWorker() {
    override takeId(Object object) {
      return (object as IdFigure).id;
    }

    override sendAction(Action action) {
      actionManager.append(action)
      redraw
    }

    override addChangeHandler(Handler handler) {
      return changeSceneHandlerList.add(handler)
    }

  }

  private Point mouse = new Point(0, 0)

  def void setScene(Scene scene) {
    originalScene = scene
    this.scene = originalScene.copy

    actionManager.scene = this.scene
  }

  public new(Composite parent, int style) {
    super(parent, style);

    addPaintListener [ PaintEvent e |
      paintCanvas(e);
    ];

    addMouseListener(this);
    addMouseMoveListener(this);
    addMouseTrackListener(this);
  }

  var counter = 1

  def paintCanvas(PaintEvent e) {
    paintTmp(e)

    var placer = new VisitorPlacer(e.gc, styleCalc)
    var vp = new VisitorPaint(placer)
    vp.mouse = mouse
    scene => vp
  }

  override mouseDoubleClick(MouseEvent e) {

    //println("double " + e);
    counter++

  //redraw
  }

  override mouseDown(MouseEvent e) {
    mouse.x = e.x
    mouse.y = e.y

    var gc = new GC(this)
    try {
      var placer = new VisitorPlacer(gc, styleCalc)
      var selected = Hit.on(scene).with(placer).to(mouse)
      if (selected.size == 0) {
        selectionProvider.selection = new EmptySelection
      } else {
        var sel = PropFactory.parseObject(selected.get(0), sceneWorker)
        selectionProvider.selection = new PropSelection(sel)
      }

    } finally {
      gc.dispose
    }

  }

  override mouseUp(MouseEvent e) {
  }

  override mouseMove(MouseEvent e) {
    mouse.x = e.x
    mouse.y = e.y
    redraw

    if("a".equals("a")) return;

    var gc = new GC(this)
    gc.foreground = colors.rgb(200, 0, 0)
    gc.background = gc.foreground

    //gc.drawPoint(e.x, e.y)
    gc.fillOval(e.x - 2, e.y - 2, 5, 5)
    gc.dispose()
  }

  override mouseEnter(MouseEvent e) {
  }

  override mouseExit(MouseEvent e) {
  }

  override mouseHover(MouseEvent e) {
  }

  override dispose() {
    colors.dispose
    fonts.dispose
    super.dispose
  }

  def paintTmp(PaintEvent e) {
    if("a".equals("a")) return

    var rect = (e.widget as Canvas).bounds;
    e.gc.foreground = e.display.getSystemColor(SWT.COLOR_RED);
    e.gc.drawFocus(0, 0, rect.width - 1, rect.height - 1);

    var oldBG = e.gc.background

    e.gc.background = e.display.getSystemColor(SWT.COLOR_CYAN);
    e.gc.fillRectangle(50, 50, 600, 70);

    e.gc.background = oldBG
    e.gc.foreground = e.display.getSystemColor(SWT.COLOR_RED);

    e.gc.font = fonts.arial.height(30)

    var str = 'Отрисовка. Counter = ' + counter + (if(counter % 2 == 0) ", Чёт" else ", нечет")

    var r = e.gc.textExtent(str, SWT.DRAW_TRANSPARENT)

    var p = new Point(60, 60)

    e.gc.drawText(str, p.x, p.x, true);

    e.gc.foreground = e.display.getSystemColor(SWT.COLOR_BLUE);
    e.gc.drawRectangle(p.x, p.y, r.x, r.y)

    //e.gc.drawLine(60, 60, 60 + r.x, 60);
    //e.gc.drawLine(60, 60 + r.y, 60 + r.x, 60 + r.y);
    //e.gc.drawLine(60, 60, 60, 60 + r.y);
    //e.gc.drawLine(60 + r.x, 60, 60 + r.x, 60 + r.y);
    e.gc.foreground = e.display.getSystemColor(SWT.COLOR_RED);
    e.gc.drawText('Начинается новый день', 60, 180);
  }
}
