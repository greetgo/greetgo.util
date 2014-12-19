package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.Kursor;
import kz.greepto.gpen.drawport.Rect;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.action.Oper;

/**
 * Результат отрисовки фигуры
 */
public interface PaintResult {
  
  /**
   * Возвращает положение фигуры
   * 
   * @return положение фигуры
   */
  Rect getPlace();
  
  /**
   * Возвращает курсор по месту мыши, может быть <code>null</code>
   * 
   * @return курсор по месту мыши или <code>null</code>
   */
  Kursor getKursor();
  
  /**
   * Проверяет наличие операции по изменению фигуры, т.е. возможность вызвать метод
   * {@link #createOper(Vec2)}
   * 
   * @return признак наличия операции по изменению фигуры
   */
  boolean isHasOper();
  
  /**
   * Создаёт и возвращает операцию, которая производится с фигурой, при переносе мыши на указанную
   * позицию. Если такой операции произвести нельзя генерируется ошибка. Предварительно нужно
   * проверить наличие возможности создания операции вызовом метода {@link #isHasOper()}
   * 
   * @param mouseMovedTo
   *          позиция, на которую переместился указатель мыши
   * @return операция по изменению фигуры. Может быть <code>null</code> - это значит, что никакого
   *         изменения не нужно
   */
  Oper createOper(Vec2 mouseMovedTo);
}
