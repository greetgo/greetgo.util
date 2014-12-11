package kz.greepto.gpen.drawport

interface RectGeom {
  def RectGeom draw()

  def RectGeom fill()

  def RectGeom clip()

  def RectGeom drawOval()

  def RectGeom fillOval()

  def ArcGeom arc(int from, int angle)

  def RoundRectGeom round(Size arcSize)

  def RoundRectGeom round(int arcWidth, int arcHeight)

  def RoundRectGeom round(int arcWidthAndHeight)
}
