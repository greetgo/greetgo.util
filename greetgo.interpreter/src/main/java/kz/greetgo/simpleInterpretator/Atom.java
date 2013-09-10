package kz.greetgo.simpleInterpretator;

public class Atom {
  private AtomType type;
  private String value;
  
  @Override
  public String toString() {
    return type + "[" + value + "]";
  }
  
  public AtomType getType() {
    return type;
  }
  
  public void setType(AtomType type) {
    this.type = type;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  public boolean isOpenExpression() {
    return type == AtomType.SYMBOL && "(".equals(value);
  }
  
  public boolean isCloseExpression() {
    return type == AtomType.SYMBOL && ")".equals(value);
  }
  
  public static Atom word(String value) {
    Atom ret = new Atom();
    ret.setType(AtomType.WORD);
    ret.setValue(value);
    return ret;
  }
}
