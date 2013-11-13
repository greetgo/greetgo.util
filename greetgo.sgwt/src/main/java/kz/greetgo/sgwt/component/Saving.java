package kz.greetgo.sgwt.component;

import kz.greetgo.gwtshare.base.ServiceAsync;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public abstract class Saving<T> extends BaseSaving<T> {
  protected final Layout content;
  
  public Saving(final ServiceAsync<T, T> saveService) {
    super(saveService);
    
    content = vl();
    content.setMargin(GRAIN);
    content.setWidth100();
    content.setHeight100();
    
    HLayout toolbar = toolbar(hl(saveButton, cancelButton));
    toolbar.setAlign(Alignment.CENTER);
    items(window, content, toolbar);
  }
}
