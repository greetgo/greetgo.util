package kz.greepto.gpen.views.gpen.align.worker;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.util.ColorManager;

public interface AlignWorker {
  
  void paintIcon(DrawPort gc, ColorManager colos, int width, int height);
  
}
