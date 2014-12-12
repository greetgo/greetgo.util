package kz.greepto.gpen.drawport

interface Geom {
  def Geom to(int x, int y)

  def Geom to(Vec2 point)

  def Geom shift(Vec2 offset)

  def Geom shift(int dx, int dy)

  def Geom line()

  def Geom move()

  def Geom size(int width, int height)

  def Geom size(Size size)

  def RectGeom rect()

  def Vec2 last()
}
