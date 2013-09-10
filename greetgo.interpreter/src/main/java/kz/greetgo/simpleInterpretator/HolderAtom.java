package kz.greetgo.simpleInterpretator;

public class HolderAtom extends Holder {
  private final Atom atom;
  
  public HolderAtom(Atom atom) {
    this.atom = atom;
  }
  
  public Atom getAtom() {
    return atom;
  }
  
  @Override
  public String toString() {
    return "{" + getAtom().getValue() + "}";
  }
  
  public boolean isWord() {
    return atom.getType() == AtomType.WORD;
  }
  
  public boolean isOperator() {
    if (atom.getType() == AtomType.WORD) {
      return HelperOperator.isOperator(atom.getValue());
    }
    return atom.getType() == AtomType.SYMBOL;
  }
  
  public boolean isConstant() {
    switch (atom.getType()) {
    case DATE:
    case DECIMAL:
    case STRING:
      return true;
    }
    return false;
  }
}
