package kz.greetgo.simpleInterpretator;

public class PlaceOper extends Place {
  private final Atom atom;
  
  public PlaceOper(Atom atom) {
    this.atom = atom;
  }
  
  public Atom getAtom() {
    return atom;
  }
  
  @Override
  public String toString() {
    return "<" + atom + '>';
  }
  
  public boolean isNotOper() {
    if (AtomType.WORD != atom.getType()) return false;
    String value = atom.getValue();
    if (value == null) return false;
    value = value.trim().toUpperCase();
    if ("НЕ".equals(value)) return true;
    if ("NOT".equals(value)) return true;
    return false;
  }
  
  public int priority() {
    return HelperOperator.priority(atom.getValue());
  }
  
  public boolean isTrippleComparator() {
    if (atom.getType() != AtomType.SYMBOL) return false;
    String v = atom.getValue();
    if ("<".equals(v)) return true;
    if ("<=".equals(v)) return true;
    return false;
  }
  
  public boolean isAnd() {
    if (atom.getType() != AtomType.WORD) return false;
    String value = atom.getValue();
    if (value == null) return false;
    value = value.trim().toUpperCase();
    if ("AND".equals(value)) return true;
    if ("И".equals(value)) return true;
    return false;
  }
  
  public boolean isOr() {
    if (atom.getType() != AtomType.WORD) return false;
    String value = atom.getValue();
    if (value == null) return false;
    value = value.trim().toUpperCase();
    if ("OR".equals(value)) return true;
    if ("ИЛИ".equals(value)) return true;
    return false;
  }
  
  public boolean isXor() {
    if (atom.getType() != AtomType.WORD) return false;
    String value = atom.getValue();
    if (value == null) return false;
    value = value.trim().toUpperCase();
    if ("XOR".equals(value)) return true;
    if ("ИИЛИ".equals(value)) return true;
    return false;
  }
}
