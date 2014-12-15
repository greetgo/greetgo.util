package kz.greepto.gpen.drawport

import java.util.List
import java.util.ArrayList

abstract class AbstractGeom implements Geom {
  protected Vec2 from

  protected Size size

  protected val List<Vec2> toList = new ArrayList

  override to(int x, int y) {
    to(new Vec2(x, y))
  }

  override to(Vec2 point) {
    if(point == null) throw new NullPointerException
    toList += point
    this
  }

  override line() {
    if (size != null) {
      drawLine(from, from + size)

      move
      return this
    }

    drawLine(from, toList.get(0))
    for (var i = 1, var C = toList.size; i < C; i++) {
      drawLine(toList.get(i - 1), toList.get(i))
    }

    move
    this
  }

  override move() {
    if (size != null) {
      from = from + size
      size = null
      toList.clear
      return this
    }

    from = toList.get(toList.size - 1)
    toList.clear

    this
  }

  def abstract void drawLine(Vec2 from, Vec2 to)

  override size(int width, int height) {
    size(Size.from(width, height))
  }

  override size(Size size) {
    this.size = size
    this
  }

  override shift(Vec2 offset) {
    if (toList.size === 0) {
      toList.add(from + offset)
    } else {
      toList.add(toList.last + offset)
    }
    this
  }

  override shift(int dx, int dy) { shift(Vec2.from(dx, dy)) }

  override last() {
    if(size == null && toList.size == 0) return from
    if(size != null) return from + size
    return toList.last
  }

  override dashLine(double offset, double skvaj, double length) {
    var nx = toList.get(0).x as double
    var ny = toList.get(0).y as double
    var px = from.x as double
    var py = from.y as double

    var step = Math.sqrt(nx * nx + ny * ny)
    if(step < 0.1) throw new IllegalArgumentException('step < 0.1')
    nx /= step
    ny /= step

    var lineLen = step * skvaj

    var double left = length //осталось дорисовать
    var ofs = offset
    while (ofs < 0) {
      ofs += step
    }

    while (true) {
      while (ofs >= step) {
        ofs -= step
      }

      if(left <= 0) return ofs

      if (lineLen > ofs) {
        var drawLen = lineLen - ofs
        if(drawLen > left) drawLen = left
        var tox = px + drawLen * nx
        var toy = py + drawLen * ny
        drawLineWith(px, py, tox, toy)
        px = tox
        py = toy
        left -= drawLen
        ofs += drawLen
      }

      if(left <= 0) return ofs

      {
        var moveLen = step - ofs
        if (moveLen > left) moveLen = left
        px += moveLen * nx
        py += moveLen * ny
        left -= moveLen
        ofs += moveLen
      }

    }

  }

  def void drawLineWith(double x1, double y1, double x2, double y2) {
    var from = Vec2.from(x1 as int, y1 as int)
    var to = Vec2.from(x2 as int, y2 as int)
    drawLine(from, to)
  }

}
