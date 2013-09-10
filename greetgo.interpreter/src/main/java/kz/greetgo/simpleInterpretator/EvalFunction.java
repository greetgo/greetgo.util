package kz.greetgo.simpleInterpretator;

class EvalFunction extends Eval {
  private final String name;
  private final Eval arg;
  
  public EvalFunction(String name, Eval arg) {
    this.name = name;
    this.arg = arg;
  }
  
  @Override
  public Object evaluate() {
    if ("МОДУЛЬ".equalsIgnoreCase(name)) {
      return Functions.abs(arg.evaluate());
    }
    throw new InterpreterError(ErrorCode.UNKNOWN_FUNCTION, "Неизвестная функция '" + name + "'");
  }
  
  @Override
  public String toString() {
    return "f~" + name + "(" + arg + ")";
  }
}
