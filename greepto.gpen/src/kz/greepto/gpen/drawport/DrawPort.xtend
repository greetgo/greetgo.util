package kz.greepto.gpen.drawport

interface DrawPort {
  def void dispose()

  def DrawPort copy()

  def FontDef font()

  def Geom from(int x, int y)

  def Geom from(Vec2 from)
}
