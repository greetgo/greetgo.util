package kz.greetgo.sqlmanager.gen;

import java.net.URL;

import kz.greetgo.sqlmanager.parser.StruGenerator;

import org.testng.annotations.Test;

public class ParseTest {
  @Test
  public void parse() throws Exception {
    URL url = getClass().getResource("example.nf3");
    StruGenerator sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
  }
}
