package kz.greetgo.interpreter;

import static junit.framework.Assert.assertEquals;
import kz.greetgo.simpleInterpretator.Atom;
import kz.greetgo.simpleInterpretator.AtomType;
import kz.greetgo.simpleInterpretator.Stacker;

import org.testng.annotations.Test;

public class StackerTest {
  @Test
  public void asd() {
    Stacker c = new Stacker();
    {
      append(c, "SYMBOL:-");
      append(c, "DECIMAL:23.45");
      append(c, "SYMBOL:+");
      append(c, "WORD:НИЖНИЙ");
      append(c, "WORD:Предел");
      append(c, "SYMBOL:/");
      append(c, "SYMBOL:(");
      append(c, "DECIMAL:1");
      append(c, "SYMBOL:-");
      append(c, "WORD:Верхний");
      append(c, "WORD:предел");
      append(c, "SYMBOL:^");
      append(c, "SYMBOL:(");
      append(c, "SYMBOL:-");
      append(c, "DECIMAL:2");
      append(c, "SYMBOL:)");
      append(c, "SYMBOL:)");
    }
    c.complete();
    
    assertEquals("({-}, {23.45}, {+}, {НИЖНИЙ}, {Предел},"
        + " {/}, ({1}, {-}, {Верхний}, {предел}," + " {^}, ({-}, {2})))", c.sequence().toString());
  }
  
  private void append(Stacker c, String info) {
    String[] split = info.split(":");
    Atom a = new Atom();
    a.setType(AtomType.valueOf(split[0]));
    a.setValue(split[1]);
    c.append(a);
  }
}
