package kz.greepto.gpen.editors.gpen

import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.swt.DrawPortSwt
import kz.greepto.gpen.drawport.swt.DrawableGcSource
import kz.greepto.gpen.editors.gpen.action.Oper
import kz.greepto.gpen.editors.gpen.action.UndoableOperation
import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.Scene
import kz.greepto.gpen.editors.gpen.model.visitor.Hit
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPaint
import kz.greepto.gpen.editors.gpen.model.visitor.VisitorPlacer
import kz.greepto.gpen.editors.gpen.prop.PropFactory
import kz.greepto.gpen.editors.gpen.prop.SceneWorker
import kz.greepto.gpen.editors.gpen.style.dev.DevStyleCalc
import kz.greepto.gpen.util.ColorManager
import kz.greepto.gpen.util.FontManager
import kz.greepto.gpen.util.Handler
import kz.greepto.gpen.util.HandlerList
import org.eclipse.core.commands.operations.IUndoContext
import org.eclipse.core.commands.operations.OperationHistoryFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.events.MouseEvent
import org.eclipse.swt.events.MouseListener
import org.eclipse.swt.events.MouseMoveListener
import org.eclipse.swt.events.MouseTrackListener
import org.eclipse.swt.events.PaintEvent
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite

class GpenCanvas extends Canvas implements MouseListener, MouseMoveListener, MouseTrackListener {

  Scene originalScene = new Scene
  Scene scene = new Scene

  val ColorManager colors = new ColorManager
  val FontManager fonts = new FontManager
  val DevStyleCalc styleCalc = new DevStyleCalc()
  package val SelectionProvider selectionProvider = new SelectionProvider(this)

  val HandlerList changeSceneHandlerList = new HandlerList

  val SceneWorker sceneWorker = new SceneWorker() {
    override takeId(Object object) {
      return (object as IdFigure).id
    }

    override applyOper(Oper oper) {
      var op = new UndoableOperation(oper, scene) [
        redraw
        changeSceneHandlerList.fire
      ]
      op.addContext(undoContext)

      //var ophist = PlatformUI.getWorkbench().operationSupport.operationHistory
      var ophist = OperationHistoryFactory.getOperationHistory()
      ophist.execute(op, null, null)

      //actionManager.append(action)
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
  }

  val IUndoContext undoContext

  public new(Composite parent, IUndoContext undoContext) {
    super(parent, SWT.NONE);

    this.undoContext = undoContext

    addPaintListener [ PaintEvent e |
      paintCanvas(e);
    ];

    addMouseListener(this);
    addMouseMoveListener(this);
    addMouseTrackListener(this);
  }

  def DrawPort createDP() {
    DrawPortSwt.fromGcCreator(new DrawableGcSource(fonts, colors, this))
  }

  def paintCanvas(PaintEvent e) {
    var dp = createDP
    try {
      var placer = new VisitorPlacer(dp, styleCalc)
      var vp = new VisitorPaint(placer)
      vp.mouse = mouse
      scene => vp
    } finally {
      dp.dispose
    }
  }

  override mouseDoubleClick(MouseEvent e) {
    println("double " + e);
  }

  override mouseDown(MouseEvent e) {
    mouse.x = e.x
    mouse.y = e.y

    if (Mouse.Ctrl_LMB(e)) {

      var dp = createDP
      try {
        var placer = new VisitorPlacer(dp, styleCalc)
        Hit.on(scene).with(placer).to(mouse).forEach[sel = !sel]
      } finally {
        dp.dispose
      }

      updateSelectionProvider
      return
    }

    if (Mouse.LMB(e)) {
      var dp = createDP
      try {
        var placer = new VisitorPlacer(dp, styleCalc)
        scene.list.forEach[sel = false]
        Hit.on(scene).with(placer).to(mouse).forEach[sel = true]
      } finally {
        dp.dispose
      }

      updateSelectionProvider
    }
  }

  def IdFigure getTopSelected() {
    for (f : scene.list.reverse.filter[sel]) {
      return f
    }
    return null
  }

  def void updateSelectionProvider() {
    var sel = topSelected
    if (sel == null) {
      selectionProvider.selection = new EmptySelection
    } else {
      var props = PropFactory.parseObject(sel, sceneWorker)
      selectionProvider.selection = new PropSelection(props, sel.id)
    }
    redraw
  }

  override mouseUp(MouseEvent e) {}

  override mouseMove(MouseEvent e) {
    mouse.x = e.x
    mouse.y = e.y
    redraw
  }

  override mouseEnter(MouseEvent e) {}

  override mouseExit(MouseEvent e) {}

  override mouseHover(MouseEvent e) {}

  override dispose() {
    colors.dispose
    fonts.dispose
    super.dispose
  }
}
