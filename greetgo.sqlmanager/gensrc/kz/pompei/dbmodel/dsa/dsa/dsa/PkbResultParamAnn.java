package kz.pompei.dbmodel.dsa.dsa.dsa;
import kz.greetgo.sqlmanager.gen.Dict;
import java.util.Objects;
import kz.pompei.dbmodelstru.dsa.dsa.dsa.FieldsPkbResultParamAnn;
import java.util.Arrays;
public class PkbResultParamAnn extends FieldsPkbResultParamAnn {
public long pkbResultParam1;
public String pkbResultParam2;
public String name;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {pkbResultParam1,pkbResultParam2,name, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
PkbResultParamAnn other = (PkbResultParamAnn)obj;
return Objects.equals(pkbResultParam1, other.pkbResultParam1)&& Objects.equals(pkbResultParam2, other.pkbResultParam2)&& Objects.equals(name, other.name);
}

public PkbResultParamAnn() {}

public PkbResultParamAnn(long pkbResultParam1, String pkbResultParam2, String name) {
this.pkbResultParam1 = pkbResultParam1;
this.pkbResultParam2 = pkbResultParam2;
this.name = name;
}
public Dict toDictionary() {
  return new Dict(pkbResultParam1, pkbResultParam2, name, type,hello,goodby);
}
}
