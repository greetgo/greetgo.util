package kz.greepto.gpen.editors.gpen.model.paint;

import kz.greepto.gpen.editors.gpen.model.IdFigure;

/**
 * Проверяет выделенность вигуры
 * 
 * @author pompei
 * 
 */
public interface SelChecker {
  /**
   * Проверяет выделенность фигуры
   * 
   * @param figure
   *          проверяемая фигура
   * @return признак выделенности: <code>true</code> - значит выделена, иначе - не выделена
   */
  boolean isSelected(IdFigure figure);
}
