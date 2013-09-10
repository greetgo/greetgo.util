package kz.greetgo.simpleInterpretator;

import java.util.Map;
import java.util.Map.Entry;

public class Interpreter {
  private final Context context = new Context();
  private Eval eval;
  
  public void compileString(String string) {
    Atomizer a = new Atomizer();
    Stacker stacker = new Stacker();
    a.setAtomHandler(stacker);
    for (int i = 0, N = string.length(); i < N; i++) {
      a.append(string.charAt(i));
    }
    a.complete();
    
    eval = stacker.sequence().compile(context);
  }
  
  public void addValues(Map<String, Object> values) {
    for (Entry<String, Object> e : values.entrySet()) {
      context.setValue(e.getKey(), e.getValue());
    }
  }
  
  public void addValue(String name, Object value) {
    context.setValue(name, value);
  }
  
  public void cleanValues() {
    context.cleanValues();
  }
  
  public Object evaluate() {
    return eval.evaluate();
  }
  
  @Override
  public String toString() {
    return "" + eval;
  }
}
