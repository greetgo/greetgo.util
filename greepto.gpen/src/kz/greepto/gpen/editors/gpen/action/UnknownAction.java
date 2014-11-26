package kz.greepto.gpen.editors.gpen.action;

public class UnknownAction extends RuntimeException {
  public UnknownAction(String action) {
    super(action);
  }
}
