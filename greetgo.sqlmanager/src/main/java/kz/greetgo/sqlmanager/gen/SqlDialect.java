package kz.greetgo.sqlmanager.gen;

import kz.greetgo.sqlmanager.model.SimpleType;

public interface SqlDialect {
  
  String sqlType(SimpleType simpleType);
  
  String procType(SimpleType simpleType);
  
}
