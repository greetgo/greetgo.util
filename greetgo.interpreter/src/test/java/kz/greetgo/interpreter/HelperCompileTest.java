package kz.greetgo.interpreter;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.simpleInterpretator.Atom;
import kz.greetgo.simpleInterpretator.Context;
import kz.greetgo.simpleInterpretator.Eval;
import kz.greetgo.simpleInterpretator.HelperCompile;
import kz.greetgo.simpleInterpretator.Place;
import kz.greetgo.simpleInterpretator.PlaceEvalHolded;
import kz.greetgo.simpleInterpretator.PlaceVar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HelperCompileTest {
  private Context context;
  
  @BeforeMethod
  public void prepareContext() {
    context = new Context();
  }
  
  @Test
  public void functionsToEval() {
    List<Place> places = new ArrayList<Place>();
    places.add(new PlaceVar(Atom.word("МОдулЬ")));
    places.add(new PlaceEvalHolded(tmpEval(13)));
    places.add(new PlaceVar(Atom.word("МОдулЬ")));
    places.add(new PlaceEvalHolded(tmpEval(17)));
    
    HelperCompile.functionsToEval(places, context);
    
    assertEquals("[PlaceEvalOther[f~МОдулЬ(tmp{13})]," + " PlaceEvalOther[f~МОдулЬ(tmp{17})]]",
        places.toString());
  }
  
  private Eval tmpEval(final int ret) {
    return new Eval() {
      @Override
      public Object evaluate() {
        return ret;
      }
      
      @Override
      public String toString() {
        return "tmp{" + ret + "}";
      }
    };
  }
}
