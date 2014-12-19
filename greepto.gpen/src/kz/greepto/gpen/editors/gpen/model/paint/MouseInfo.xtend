package kz.greepto.gpen.editors.gpen.model.paint

import kz.greepto.gpen.drawport.Kursor
import kz.greepto.gpen.drawport.Vec2
import org.eclipse.xtend.lib.annotations.Data

@Data
class MouseInfo {
  public Vec2 mouse
  public Kursor kursor
  public Object changeType
}
