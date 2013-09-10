package kz.greetgo.simpleInterpretator;

import java.util.HashMap;
import java.util.Map;

public class Context {
  private final Map<String, Object> vars = new HashMap<String, Object>();
  
  public void cleanValues() {
    vars.clear();
  }
  
  public void setValue(String name, Object value) {
    vars.put(name.toUpperCase(), value);
  }
  
  /**
   * Проверяет наличие переменной с
   * именем. Если такой не, то
   * генерируется ошибка
   * 
   * @param name
   *          имя переменной
   */
  public void checkVarExists(String name) throws InterpreterError {
    if (vars.containsKey(name.toUpperCase())) return;
    throw new InterpreterError("Неизвестная переменная: " + name);
  }
  
  /**
   * Получает значение переменной с
   * указанным именем
   * 
   * @param name
   *          имя переменной
   * @return её хначение
   */
  public Object getVarValue(String name) {
    return vars.get(name.toUpperCase());
  }
  
}
