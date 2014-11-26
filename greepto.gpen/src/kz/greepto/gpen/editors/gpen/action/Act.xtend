package kz.greepto.gpen.editors.gpen.action

import kz.greepto.gpen.editors.gpen.model.IdFigure
import kz.greepto.gpen.editors.gpen.model.Label
import kz.greepto.gpen.editors.gpen.model.Combo
import kz.greepto.gpen.editors.gpen.model.Button

class Act {
  public static def Action p(String act, String ...params) {
    switch act {
      case "append": append(params.get(0), params.get(1), params.subList(2, params.size))
      default: throw new UnknownAction(act)
    }
  }

  def static Action append(String fig, String id, String ...params) {
    return new ActionAppend(createFigure(fig, id, params))
  }


  def static createNewFigure(String fig, String id) {
    return switch fig {
      case 'Label': new Label(id)
      case 'Combo': new Combo(id)
      case 'Button': new Button(id)
      default: throw new UnknownFigure(fig)
    }
  }

  def static IdFigure createFigure(String fig, String id, String[] params) {
    var ret = createNewFigure(fig, id);

    return ret
  }

}