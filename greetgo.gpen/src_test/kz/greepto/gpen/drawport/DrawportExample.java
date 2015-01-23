package kz.greepto.gpen.drawport;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class DrawportExample {
  public static void main(String[] args) {
    Display display = new Display();
    String home = System.getProperty("user.home");
    
    Image image = new Image(display, home + "/tmp/out0001.jpeg");
    GC gc = new GC(image);
    Rectangle bounds = image.getBounds();
    gc.drawLine(0, 0, bounds.width, bounds.height);
    gc.drawLine(0, bounds.height, bounds.width, 0);
    gc.dispose();
    
    ImageLoader il = new ImageLoader();
    il.data = new ImageData[] { image.getImageData() };
    il.save(home + "/tmp/out0001-out.jpeg", SWT.IMAGE_JPEG);
    
    image.dispose();
    
    System.out.println("OK");
  }
}
