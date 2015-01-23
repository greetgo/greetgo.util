package kz.greepto.gpen.drawport

interface StrGeom {
  def void draw(int x, int y)

  def void draw(Vec2 point)

  def Size size()
}
