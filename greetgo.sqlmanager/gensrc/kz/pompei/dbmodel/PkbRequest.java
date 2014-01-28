package kz.pompei.dbmodel;
import kz.pompei.dbmodelstru.FieldsPkbRequest;
import java.util.Objects;
import java.util.Arrays;
public class PkbRequest extends FieldsPkbRequest {
public long pkbRequest;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {pkbRequest, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
PkbRequest other = (PkbRequest)obj;
return Objects.equals(pkbRequest, other.pkbRequest);
}

public PkbRequest() {}

public PkbRequest(long pkbRequest) {
this.pkbRequest = pkbRequest;
}
}
