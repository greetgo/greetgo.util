package kz.greepto.gpen.editors.gpen.model;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class FigTest {
  @Test
  public void c_Label() throws Exception {
    IdFigure fig = Fig.c("Label", "asd", "x 111 y 222");
    
    assertThat(fig).isNotNull();
    assertThat(fig).isInstanceOf(Label.class);
    Label label = (Label)fig;
    assertThat(label.id).isEqualTo("asd");
    assertThat(label.getPoint().x).isEqualTo(111);
    assertThat(label.getPoint().y).isEqualTo(222);
    
    assertThat(1);
  }
}
