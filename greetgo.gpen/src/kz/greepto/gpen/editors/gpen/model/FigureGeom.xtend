package kz.greepto.gpen.editors.gpen.model

import org.eclipse.xtend.lib.annotations.Data

@Data
class FigureGeom {
  String figureId

  int x
  int y
  int width
  int height

  boolean fixedWidth
  boolean fixedHeight

  override String toString() {
    var W = '' + width
    if(fixedWidth) W = '[' + W + ']'
    var H = '' + height
    if(fixedHeight) H = '[' + H + ']'
    return 'FigureGeom ' + figureId + ' ' + W + 'x' + H + ' @ (' + x + ', ' + y + ')';
  }
}
