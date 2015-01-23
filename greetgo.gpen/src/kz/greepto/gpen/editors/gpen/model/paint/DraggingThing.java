package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.drawport.Kursor;
import kz.greepto.gpen.drawport.Vec2;
import kz.greepto.gpen.editors.gpen.action.Oper;

public interface DraggingThing {
  /**
   * Возвращает курсор по месту мыши, может быть <code>null</code>
   * 
   * @return курсор по месту мыши или <code>null</code>
   */
  Kursor getKursor();
  
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
  
  /**
   * Прорисовывает то, что должно отображаться при драгинге
   * 
   * @param dp
   *          куда рисуем
   * @param mouseMovedTo
   *          куда сдвинулась мышь
   */
  void paintDrag(DrawPort dp, Vec2 mouseMovedTo);
}
