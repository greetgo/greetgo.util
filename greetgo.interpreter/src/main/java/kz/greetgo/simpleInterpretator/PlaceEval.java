package kz.greetgo.simpleInterpretator;

public abstract class PlaceEval extends Place {
  private final Eval eval;
  
  public PlaceEval(Eval eval) {
    this.eval = eval;
  }
  
  public Eval getEval() {
    return eval;
  }
  
  @Override
  public String toString() {
    return HelperCompile.simpleName(getClass().getName()) + "[" + eval + "]";
  }
}
