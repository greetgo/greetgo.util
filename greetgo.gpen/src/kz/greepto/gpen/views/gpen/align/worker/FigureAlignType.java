package kz.greepto.gpen.views.gpen.align.worker;

public enum FigureAlignType {
  ToRightFromBottom, ToLeftFromBottom, ToBottomFromRight, ToBottomFromLeft, //
  ToRightFromTop, ToLeftFromTop, ToTopFromRight, ToTopFromLeft, //
  WidthLikeBottom, CenterVert, CenterHoriz, EqualHoriz, //
  WidthLikeTop, EqualVert,
  
  ;
  
  public final AlignWorker worker;
  
  private FigureAlignType() {
    try {
      String cl = getClass().getPackage().getName() + ".AlignWorker" + name();
      this.worker = (AlignWorker)Class.forName(cl).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
