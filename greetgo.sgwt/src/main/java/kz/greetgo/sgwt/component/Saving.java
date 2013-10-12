package kz.greetgo.sgwt.component;

import kz.greetgo.gwtshare.base.Async;
import kz.greetgo.gwtshare.base.ServiceAsync;
import kz.greetgo.gwtshare.base.Sync;
import kz.greetgo.sgwt.base.BaseCallback;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class Saving<T> extends Snippet implements Async<T, T> {
  private Sync<T> sync;
  protected T value; // gather should use "Copy on Write" strategy.
  protected final VLayout content = vl();
  private final IButton saveButton;
  public final Window window = new Window();
  
  public Saving(final ServiceAsync<T, T> saveService) {
    saveButton = new IButton(SAVE, new ClickHandler() {
      public void onClick(ClickEvent event) {
        gather();
        saveService.invoke(value, new BaseCallback<T>() {
          public void onSuccess(T t) {
            value = null;
            sync.invoke(t);
            window.hide();
          }
        });
      }
    });
    
    IButton cancelButton = new IButton(CANCEL, new ClickHandler() {
      public void onClick(ClickEvent event) {
        window.hide();
      }
    });
    
    window.setAutoSize(true);
    window.setAutoCenter(true);
    window.setIsModal(true);
    
    content.setMargin(GRAIN);
    content.setWidth100();
    content.setHeight100();
    
    HLayout toolbar = toolbar(hl(saveButton, cancelButton));
    toolbar.setAlign(Alignment.CENTER);
    items(window, content, toolbar);
  }
  
  @Override
  public final void invoke(T value, final Sync<T> sync) {
    this.value = value;
    this.sync = sync;
    scatter();
    updateCanSave();
    window.show();
  }
  
  protected final void updateCanSave() {
    saveButton.setDisabled(!canSave());
  }
  
  protected abstract boolean canSave();
  
  protected abstract void gather();
  
  protected abstract void scatter();
}
