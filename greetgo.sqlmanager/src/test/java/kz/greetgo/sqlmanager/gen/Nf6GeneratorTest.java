package kz.greetgo.sqlmanager.gen;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import kz.greetgo.sqlmanager.parser.StruGenerator;

import org.testng.annotations.Test;

public class Nf6GeneratorTest {
  @Test
  public void convert_generate() throws Exception {
    URL url = getClass().getResource("example.nf3");
    StruGenerator sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
    
    new File("build").mkdir();
    
    Nf6Generator nf6generator = new Nf6GeneratorPostgres(sg);
    PrintStream out = new PrintStream("build/ddl-postgres-nf6.sql", "UTF-8");
    PrintStream outP = new PrintStream("build/ddl-postgres-nf6-programs.sql", "UTF-8");
    nf6generator.convertTo(out, outP);
    out.close();
    outP.close();
    
    nf6generator.javaGenDir = "gensrc";
    nf6generator.modelPackage = "kz.pompei.dbmodel";
    nf6generator.daoPackage = "kz.pompei.dao";
    
    nf6generator.javaGenStruDir = "gensrcstru";
    nf6generator.modelStruPackage = "kz.pompei.dbmodelstru";
    nf6generator.modelStruExtends = "kz.greetgo.sqlmanager.gen.ModelParent";
    //nf6generator.modelStruImplements = "kz.greetgo.sqlmanager.gen.ModelParent";
    
    nf6generator.generateJava();
  }
}
