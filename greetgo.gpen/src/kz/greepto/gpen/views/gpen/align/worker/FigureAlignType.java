package kz.greepto.gpen.views.gpen.align.worker;

public enum FigureAlignType {
  ToLeft, ToRight, EqualHoriz, //
  ToTop, ToBottom, EqualVert, //
  
  ;
  
  public final AlignWorker worker;
  
  private FigureAlignType() {
    try {
      System.out.println("name = " + name());
      String cl = getClass().getPackage().getName() + ".AlignWorker" + name();
      this.worker = (AlignWorker)Class.forName(cl).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
