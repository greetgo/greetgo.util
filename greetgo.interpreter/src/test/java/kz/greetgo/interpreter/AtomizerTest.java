package kz.greetgo.interpreter;

import kz.greetgo.simpleInterpretator.Atom;
import kz.greetgo.simpleInterpretator.AtomHandler;
import kz.greetgo.simpleInterpretator.Atomizer;

import org.testng.annotations.Test;

public class AtomizerTest {
  @Test
  public void asd() {
    Atomizer a = new Atomizer();
    a.setAtomHandler(new AtomHandler() {
      @Override
      public void complete() {
        System.out.println("COMPLETE");
      }
      
      @Override
      public void append(Atom atom) {
        System.out.println(atom);
      }
    });
    appendAll(a, "- a + - d + (WOW - XXX + 'Qwerty \"---\" ");
    appendAll(a, " UIOP' asd `DSAD` asd {2011-20-32})");
    a.complete();
  }
  
  private void appendAll(Atomizer a, String string) {
    for (int i = 0, N = string.length(); i < N; i++) {
      a.append(string.charAt(i));
    }
  }
}
