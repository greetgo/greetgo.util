package kz.greepto.gpen.editors.gpen.style

import kz.greepto.gpen.drawport.Size

class Padding {
  public int left = 0
  public int right = 0
  public int top = 0
  public int bottom = 0

  def Size apply(Size size) { Size.from(size.width + left + right, size.height + top + bottom) }
}
