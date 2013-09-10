package kz.greetgo.simpleInterpretator;

import java.util.ArrayList;
import java.util.List;

public class HolderSequence extends Holder {
  private final List<Holder> holders = new ArrayList<Holder>();
  
  public List<Holder> getHolders() {
    return holders;
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('(');
    boolean first = true;
    for (Holder h : getHolders()) {
      if (first) {
        first = false;
      } else {
        sb.append(", ");
      }
      sb.append(h.toString());
    }
    sb.append(')');
    return sb.toString();
  }
  
  public Eval compile(Context context) {
    List<Place> places = HelperCompile.preparePlaces(holders, context);
    HelperCompile.functionsToEval(places, context);
    
    HelperCompile.notOperToEval(places, context);
    // теперь в places есть только PlaceEval и PlaceOper
    
    return HelperCompile.operateEval(places);
  }
}
