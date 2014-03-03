package kz.greetgo.sqlmanager.gen;

public class Conf {
  public String separator = ";;";
  public String tabPrefix = "m_";
  public String seqPrefix = "s_";
  public String vPrefix = "v_";
  public String withPrefix = "x_";
  public String ts = "ts";
  public String cre = "createdAt";
  public String bigQuote = "big_quote";
  public String _ins_ = "ins_";
  public String _p_ = "p_";
  public String _value_ = "__value__";
  public String daoSuffix = "Dao";
  
  public String javaGenDir;
  public String modelPackage;
  public String javaGenStruDir;
  public String modelStruPackage;
  public String daoPackage;
  public String modelStruExtends;
  public String modelStruImplements;
}
