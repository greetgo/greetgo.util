package kz.pompei.dbmodel.asd.asd;
import java.util.Objects;
import kz.pompei.dbmodelstru.asd.asd.FieldsNaturalPerson;
import java.util.Arrays;
public class NaturalPerson extends FieldsNaturalPerson {
public long naturalPerson;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {naturalPerson, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
NaturalPerson other = (NaturalPerson)obj;
return Objects.equals(naturalPerson, other.naturalPerson);
}

public NaturalPerson() {}

public NaturalPerson(long naturalPerson) {
this.naturalPerson = naturalPerson;
}
}
