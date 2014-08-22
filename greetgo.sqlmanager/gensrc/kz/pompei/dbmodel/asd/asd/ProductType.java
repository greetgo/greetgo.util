package kz.pompei.dbmodel.asd.asd;
import java.util.Objects;
import kz.pompei.dbmodelstru.asd.asd.FieldsProductType;
import java.util.Arrays;
public class ProductType extends FieldsProductType {
public String productType;
@Override public int hashCode() {
return Arrays.hashCode(new Object[] {productType, });
}
@Override public boolean equals(Object obj) {
if (this == obj) return true;
if (obj == null) return false;
if (getClass() != obj.getClass()) return false;
ProductType other = (ProductType)obj;
return Objects.equals(productType, other.productType);
}

public ProductType() {}

public ProductType(String productType) {
this.productType = productType;
}
}
