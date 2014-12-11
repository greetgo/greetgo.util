package kz.greepto.gpen.drawport

class FontDef {

  private boolean italic = false
  private boolean bold = false
  private String family = 'Arial'
  private int height = 12

  override hashCode() {
    var prime = 31
    var result = 1
    result = prime * result + if(bold) 1231 else 1237
    result = prime * result + family?.hashCode
    result = prime * result + height
    result = prime * result + if(italic) 1231 else 1237
    return result
  }

  override equals(Object obj) {
    if(obj === null) return false
    if(this === obj) return true
    if(!(obj instanceof FontDef)) return false
    var a = obj as FontDef
    if(family != a.family) return false
    if(height !== a.height) return false
    if(bold && !a.bold || !bold && a.bold) return false
    if(italic && !a.italic || !italic && a.italic) return false
    return true
  }

  def void assign(FontDef other) {
    family = other.family
    bold = other.bold
    italic = other.italic
    height = other.height
  }

  def FontDef copy() {
    var ret = new FontDef
    ret.assign(this)
    return ret
  }

  def void setFamily(String family) {
    this.family = family
  }

  def String getFamily() { family }

  def void setItalic(boolean italic) {
    this.italic = italic
  }

  def boolean isItalic() { italic }

  def void setBold(boolean bold) {
    this.bold = bold
  }

  def boolean isBold() { bold }

  def void setHeight(int height) {
    this.height = height
  }

  def int getHeight() { height }

  def FontDef norm() {
    bold = false
    italic = false
    this
  }

  def FontDef i() {
    italic = true
    this
  }

  def FontDef b() {
    bold = true
    this
  }

  def FontDef f(String family) {
    this.family = family
    this
  }

  def FontDef h(int height) {
    this.height = height
    this
  }

  def static FontDef arial() {
    (new FontDef).f('Arial')
  }

  def static FontDef timesNewRoman() {
    (new FontDef).f('Times New Roman')
  }
}
