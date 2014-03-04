package kz.greetgo.gbatis.modelreader;

import java.net.URL;

import kz.greetgo.sqlmanager.gen.Nf6Generator;
import kz.greetgo.sqlmanager.gen.Nf6GeneratorPostgres;
import kz.greetgo.sqlmanager.parser.StruGenerator;

public class CreatePgSql {
  public static void main(String[] args) throws Exception {
    new CreatePgSql().run();
    
  }
  
  private void run() throws Exception {
    URL url = getClass().getResource("stru.nf3");
    StruGenerator sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
    
    Nf6Generator nf6generator = new Nf6GeneratorPostgres(sg);
    {
      nf6generator.printSqls(System.out);
      nf6generator.printPrograms(System.out);
    }
  }
}
