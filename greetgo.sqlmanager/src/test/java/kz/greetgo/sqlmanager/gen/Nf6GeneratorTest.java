package kz.greetgo.sqlmanager.gen;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import kz.greetgo.sqlmanager.parser.StruShaper;

import org.testng.annotations.Test;

public class Nf6GeneratorTest {
  @Test
  public void postgres() throws Exception {
    URL url = getClass().getResource("example.nf3");
    StruShaper sg = new StruShaper();
    sg.printPStru = false;
    sg.parse(url);
    
    new File("build").mkdir();
    
    Conf conf = new Conf();
    conf.genOperTables = true;
    
    Nf6Generator nf6generator = new Nf6GeneratorPostgres(conf, sg);
    nf6generator.libType = LibType.GBATIS;
    PrintStream out = new PrintStream("build/ddl-postgres-nf6.sql", "UTF-8");
    PrintStream outP = new PrintStream("build/ddl-postgres-nf6-programs.sql", "UTF-8");
    PrintStream outComment = new PrintStream("build/ddl-postgres-nf6-comment.sql", "UTF-8");
    PrintStream outCopy = new PrintStream("build/ddl-oracle-copy.sql", "UTF-8");
    nf6generator.printSqls(out);
    nf6generator.printPrograms(outP);
    nf6generator.printHistToOperSqls(outCopy, ";; -- NAME -- ");
    nf6generator.printComment(outComment);
    out.close();
    outP.close();
    outComment.close();
    outCopy.close();
    
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
    StruShaper sg = new StruShaper();
    sg.printPStru = false;
    sg.parse(url);
    
    new File("build").mkdir();
    
    Conf conf = new Conf();
    conf.genOperTables = true;
    
    Nf6Generator nf6generator = new Nf6GeneratorOracle(conf, sg);
    PrintStream out = new PrintStream("build/ddl-oracle-nf6.sql", "UTF-8");
    PrintStream outCopy = new PrintStream("build/ddl-oracle-copy.sql", "UTF-8");
    PrintStream outP = new PrintStream("build/ddl-oracle-nf6-programs.sql", "UTF-8");
    nf6generator.printSqls(out);
    nf6generator.printPrograms(outP);
    nf6generator.printHistToOperSqls(outCopy, ";;-- NAME --");
    out.close();
    outP.close();
    outCopy.close();
    
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
