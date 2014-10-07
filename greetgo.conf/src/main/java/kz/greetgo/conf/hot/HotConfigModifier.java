package kz.greetgo.conf.hot;

/**
 * Предоставляет возможность динамически менять конфигурационные переметры
 * 
 * @author pompei
 */
public interface HotConfigModifier {
  /**
   * Активирует/деактивирует работу метода {@link HotConfigFactory#reset()}
   * 
   * @param resetEnabled
   *          <code>true</code> - активировать, иначе - деактивировать
   */
  void setResetEnabled(boolean resetEnabled);
  
  /**
   * Устанавливает значение параметра
   * 
   * @param classs
   *          конфигурационный интерфейс
   * @param name
   *          имя параметра
   * @param value
   *          значение параметра
   */
  void set(Class<?> classs, String name, Object value);
}
