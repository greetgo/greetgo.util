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
    if (size == null && toList.size == 0) return from
    if (size != null) return from + size
    return toList.last
  }
}
