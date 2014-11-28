package kz.greepto.gpen.editors.gpen.style

class PaintStatus {
  public boolean hover
  public boolean active

  def static normal() {
    var ret = new PaintStatus
    ret.hover = false
    ret.active = false
    return ret
  }

  def static hover() {
    var ret = new PaintStatus
    ret.hover = true
    ret.active = false
    return ret
  }

}