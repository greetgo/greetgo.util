package kz.greepto.gpen.views.gpen.align.worker;

import java.util.List;

import kz.greepto.gpen.drawport.DrawPort;
import kz.greepto.gpen.editors.gpen.model.FigureGeom;

public interface AlignWorker {
  
  void paintIcon(DrawPort gc, int width, int height);
  
  boolean canDoFor(List<FigureGeom> geomList);
  
}
