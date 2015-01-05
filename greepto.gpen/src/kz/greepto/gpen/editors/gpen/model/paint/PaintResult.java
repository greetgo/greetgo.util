package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Vec2;

/**
 * Результат отрисовки фигуры
 */
public interface PaintResult extends DraggingThing {
  
  /**
   * Возвращает положение фигуры
   * 
   * @return положение фигуры
   */
  Rect getPlace();
  
  /**
   * Проверяет наличие операции по изменению фигуры, т.е. возможность вызвать метод
   * {@link #createOper(Vec2)}
   * 
   * @return признак наличия операции по изменению фигуры
   */
  boolean isHasOper();
  
}
