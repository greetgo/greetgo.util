package kz.greepto.gpen.drawport

interface Geom {
  def Geom to(int x, int y)

  def Geom to(Vec2 point)

  def Geom line()

  def Geom size(int width, int height)

  def Geom size(Size size)

  def RectGeom rect()

  def StrGeom str(String str)
}
