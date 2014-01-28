package kz.greetgo.sqlmanager.gen;

import java.util.HashMap;
import java.util.Map;

import kz.greetgo.sqlmanager.model.SimpleType;

public class SqlDialectOracle implements SqlDialect {
  
  private final Map<SimpleType, String> simpleTypeMap = new HashMap<>();
  {
    simpleTypeMap.put(SimpleType.tbool, "int");
    simpleTypeMap.put(SimpleType.tfloat, "double precision");
    simpleTypeMap.put(SimpleType.tint, "int");
    simpleTypeMap.put(SimpleType.tline, "varchar2(300)");
    simpleTypeMap.put(SimpleType.tlong, "number");
    simpleTypeMap.put(SimpleType.tlongline, "varchar2(1000)");
    simpleTypeMap.put(SimpleType.ttext, "clob");
    simpleTypeMap.put(SimpleType.ttime, "timestamp");
    simpleTypeMap.put(SimpleType.tword, "varchar2(100)");
  }
  
  @Override
  public String sqlType(SimpleType simpleType) {
    String ret = simpleTypeMap.get(simpleType);
    if (ret == null) throw new RuntimeException("No SQL type for " + simpleType);
    return ret;
  }
  
}
