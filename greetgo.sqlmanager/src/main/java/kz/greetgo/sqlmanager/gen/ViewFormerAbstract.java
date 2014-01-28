package kz.greetgo.sqlmanager.gen;

public abstract class ViewFormerAbstract implements ViewFormer {
  
  protected Conf conf;
  
  protected ViewFormerAbstract(Conf conf) {
    this.conf = conf;
  }
  
  protected String space(int tabSize, int tabs) {
    StringBuilder sb = new StringBuilder(tabSize * tabs);
    for (int i = 0, C = tabSize * tabs; i < C; i++) {
      sb.append(' ');
    }
    return sb.toString();
  }
}
