package kz.greetgo.sgwt.component;

import kz.greetgo.gwtshare.base.Async;
import kz.greetgo.gwtshare.base.ServiceAsync;
import kz.greetgo.gwtshare.base.Sync;
import kz.greetgo.sgwt.base.BaseCallback;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public abstract class BaseSaving<T> extends Snippet implements Async<T, T> {
  private Sync<T> sync;
  protected final IButton saveButton;
  protected final IButton cancelButton;
  protected final Window window = new Window();
  protected boolean needToDisable = true;
  
  public BaseSaving(final ServiceAsync<T, T> saveService) {
    saveButton = new IButton(SAVE, new ClickHandler() {
      public void onClick(ClickEvent event) {
        if (needToDisable) {
          saveButton.disable();
          cancelButton.disable();
        }
        saveService.invoke(gather(), new BaseCallback<T>() {
          public void onSuccess(T t) {
            if (sync != null) {
              sync.invoke(t);
            }
            window.hide();
            saveButton.enable();
            cancelButton.enable();
          }
        });
      }
    });
    
    cancelButton = new IButton(CANCEL, new ClickHandler() {
      public void onClick(ClickEvent event) {
        window.hide();
      }
    });
    
    window.setAutoSize(true);
    window.setAutoCenter(true);
    window.setIsModal(true);
  }
  
  @Override
  public final void invoke(T value, final Sync<T> sync) {
    this.sync = sync;
    scatter(value);
    updateCanSave();
    window.show();
  }
  
  protected final void updateCanSave() {
    saveButton.setDisabled(!canSave());
  }
  
  protected abstract boolean canSave();
  
  protected abstract T gather();
  
  protected abstract void scatter(T value);
}
