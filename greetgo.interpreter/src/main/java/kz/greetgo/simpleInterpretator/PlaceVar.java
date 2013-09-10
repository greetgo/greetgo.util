package kz.greetgo.simpleInterpretator;

import java.util.ArrayList;
import java.util.List;

public class PlaceVar extends Place {
  private final List<Atom> atoms = new ArrayList<Atom>();
  
  public List<Atom> getAtoms() {
    return atoms;
  }
  
  public PlaceVar(Atom atom) {
    atoms.add(atom);
  }
  
  public boolean isFunction() {
    return HelperFunction.isFunction(name());
  }
  
  public String name() {
    StringBuilder sb = new StringBuilder();
    for (Atom atom : atoms) {
      if (sb.length() > 0) sb.append(' ');
      sb.append(atom.getValue());
    }
    return sb.toString();
  }
  
  @Override
  public String toString() {
    return HelperCompile.simpleName(getClass().getName()) + "[" + name() + "]";
  }
}
