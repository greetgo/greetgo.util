package kz.pompei.dbmodel.asd.asd;
import java.util.Objects;
import kz.pompei.dbmodelstru.asd.asd.FieldsPerson;
import java.util.Arrays;
public class Person extends FieldsPerson {
public long person;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {person, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
Person other = (Person)obj;
return Objects.equals(person, other.person);
}

public Person() {}

public Person(long person) {
this.person = person;
}
}
