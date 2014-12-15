package kz.greepto.gpen.editors.gpen.style

class PaintStatus {
  public boolean hover
  public boolean selected

  def static normal() {
    var ret = new PaintStatus
    ret.hover = false
    ret.selected = false
    return ret
  }

  def static hover() {
    var ret = new PaintStatus
    ret.hover = true
    ret.selected = false
    return ret
  }

  def static selHover(boolean selected) {
    var ret = new PaintStatus
    ret.hover = true
    ret.selected = selected
    return ret
  }

  def static sel(boolean selected) {
    var ret = new PaintStatus
    ret.hover = false
    ret.selected = selected
    return ret
  }

  def static from(boolean hover, boolean selected) {
    var ret = new PaintStatus
    ret.hover = hover
    ret.selected = selected
    return ret
  }

}