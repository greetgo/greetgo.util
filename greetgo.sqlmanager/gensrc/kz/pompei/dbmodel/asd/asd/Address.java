package kz.pompei.dbmodel.asd.asd;
import kz.pompei.dbmodelstru.asd.asd.FieldsAddress;
import java.util.Objects;
import java.util.Arrays;
public class Address extends FieldsAddress {
public long address;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {address, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
Address other = (Address)obj;
return Objects.equals(address, other.address);
}

public Address() {}

public Address(long address) {
this.address = address;
}
}
