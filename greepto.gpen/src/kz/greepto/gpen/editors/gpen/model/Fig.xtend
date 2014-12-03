package kz.greepto.gpen.editors.gpen.model

import java.util.ArrayList
import kz.greepto.gpen.util.UtilParam
import kz.greepto.gpen.util.PropManagerObject

class Fig {

  def static IdFigure c(String fig, String id, String... params) {
    var ret = createNewFigure(fig, id);
    var pm = new PropManagerObject(ret)

    val list = new ArrayList<String>

    params.forEach[list += UtilParam.parsParam(it)]

    for (var i = 0, var C = list.size; i < C; i += 2) {
      pm.setAsStr(list.get(i + 0), list.get(i + 1))
    }

    return ret
  }

  private def static createNewFigure(String fig, String id) {
    return switch fig {
      case 'Label': new Label(id)
      case 'Combo': new Combo(id)
      case 'Button': new Button(id)
      default: throw new UnknownFigure(fig)
    }
  }

}
