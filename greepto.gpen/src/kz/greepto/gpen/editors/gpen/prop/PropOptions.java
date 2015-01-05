package kz.greepto.gpen.editors.gpen.prop;

public interface PropOptions {
  boolean isReadonly();
  
  boolean isPolilines();
  
  PropOptions operator_plus(PropOptions a);
  
  PropOptions operator_add(PropOptions a);
}
