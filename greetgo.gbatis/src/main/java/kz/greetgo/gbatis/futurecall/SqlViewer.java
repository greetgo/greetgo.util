package kz.greetgo.gbatis.futurecall;

import java.util.List;

/**
 * Интерфейс для получения выполняемых SQL-ей
 * 
 * <p>
 * Данный интерфейс можно использовать для логирования работа с БД
 * <p>
 * 
 * @author pompei
 */
public interface SqlViewer {
  /**
   * Получения признака необходимости получения выполняемого SQL-я
   * 
   * @return признак необходимости получения выполняемого SQL-я
   */
  boolean needView();
  
  /**
   * Вызывается перед выполнением SQL-я, но только в том случае, если метод {@link #needView()}
   * вернул <code>true</code>
   * 
   * @param sql
   *          выполняемый SQL
   * @param params
   *          список значений, подставляемых на места (?) в выполняемом SQL-е
   * @param delay
   *          время выполнения запроса
   */
  void view(String sql, List<Object> params, long delay);
}
