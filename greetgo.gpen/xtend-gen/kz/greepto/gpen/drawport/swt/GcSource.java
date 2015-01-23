package kz.greepto.gpen.drawport.swt;

import kz.greepto.gpen.util.ColorManager;
import kz.greepto.gpen.util.FontManager;
import org.eclipse.swt.graphics.GC;

@SuppressWarnings("all")
public interface GcSource {
  public abstract GC createGC();
  
  public abstract FontManager fm();
  
  public abstract ColorManager cm();
}
