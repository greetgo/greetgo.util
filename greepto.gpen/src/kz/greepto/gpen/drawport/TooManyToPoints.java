package kz.greepto.gpen.drawport;

public class TooManyToPoints extends RuntimeException {
  
  public final int toPointsCount;
  
  public TooManyToPoints(int toPointsCount) {
    super("toPointsCount = " + toPointsCount);
    this.toPointsCount = toPointsCount;
  }
}
