package kz.greetgo.simpleInterpretator;

public class PlaceConstant extends Place {
  private final Atom atom;
  
  public PlaceConstant(Atom atom) {
    this.atom = atom;
  }
  
  public Atom getAtom() {
    return atom;
  }
  
  @Override
  public String toString() {
    switch (atom.getType()) {
    case DATE:
      return '{' + atom.getValue() + '}';
    case DECIMAL:
      return atom.getValue();
    case STRING:
      return '[' + atom.getValue() + ']';
    }
    return atom.toString();
  }
}
