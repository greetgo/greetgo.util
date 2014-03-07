package kz.greetgo.sqlmanager.gen;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import kz.greetgo.sqlmanager.parser.StruGenerator;

import org.testng.annotations.Test;

public class Nf6GeneratorTest {
  @Test
  public void postgres() throws Exception {
    URL url = getClass().getResource("example.nf3");
    StruGenerator sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
    
    new File("build").mkdir();
    
    Nf6Generator nf6generator = new Nf6GeneratorPostgres(sg);
    nf6generator.libType = LibType.GBATIS;
    PrintStream out = new PrintStream("build/ddl-postgres-nf6.sql", "UTF-8");
    PrintStream outP = new PrintStream("build/ddl-postgres-nf6-programs.sql", "UTF-8");
    nf6generator.printSqls(out);
    nf6generator.printPrograms(outP);
    out.close();
    outP.close();
    
    nf6generator.conf.javaGenDir = "gensrc";
    nf6generator.conf.modelPackage = "kz.pompei.dbmodel";
    nf6generator.conf.daoPackage = "kz.pompei.dao";
    
    nf6generator.conf.javaGenStruDir = "gensrcstru";
    nf6generator.conf.modelStruPackage = "kz.pompei.dbmodelstru";
    nf6generator.conf.modelStruExtends = "kz.greetgo.sqlmanager.gen.ModelParent";
    //nf6generator.conf.modelStruImplements = "kz.greetgo.sqlmanager.gen.ModelParent";
    
    nf6generator.generateJava();
  }
  
  @Test
  public void oracle() throws Exception {
    URL url = getClass().getResource("example.nf3");
    StruGenerator sg = new StruGenerator();
    sg.printPStru = false;
    sg.parse(url);
    
    new File("build").mkdir();
    
    Nf6Generator nf6generator = new Nf6GeneratorOracle(sg);
    PrintStream out = new PrintStream("build/ddl-oracle-nf6.sql", "UTF-8");
    PrintStream outP = new PrintStream("build/ddl-oracle-nf6-programs.sql", "UTF-8");
    nf6generator.printSqls(out);
    nf6generator.printPrograms(outP);
    out.close();
    outP.close();
    
    nf6generator.conf.javaGenDir = "gensrc";
    nf6generator.conf.modelPackage = "kz.pompei.dbmodel";
    nf6generator.conf.daoPackage = "kz.pompei.dao";
    
    nf6generator.conf.javaGenStruDir = "gensrcstru";
    nf6generator.conf.modelStruPackage = "kz.pompei.dbmodelstru";
    nf6generator.conf.modelStruExtends = "kz.greetgo.sqlmanager.gen.ModelParent";
    //nf6generator.conf.modelStruImplements = "kz.greetgo.sqlmanager.gen.ModelParent";
    
    //nf6generator.generateJava();
  }
}
