package kz.greepto.gpen.drawport

interface DrawPort {
  def void dispose()

  def DrawPort copy()

  def FontDef font()

  def void setFont(FontDef font)

  def Geom from(int x, int y)

  def Geom from(Vec2 from)

  def RectGeom from(Rect rect)

  def Style style()

  def StrGeom str(String str)

  def void clearClipping()

}
