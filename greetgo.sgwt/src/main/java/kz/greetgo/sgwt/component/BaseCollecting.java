package kz.greetgo.sgwt.component;

import java.util.List;

import kz.greetgo.gwtshare.base.Async;
import kz.greetgo.gwtshare.base.ServiceAsync;
import kz.greetgo.gwtshare.base.Sync;
import kz.greetgo.sgwt.base.BaseCallback;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;

public abstract class BaseCollecting<F, T> extends Snippet implements Async<F, List<T>> {
  protected final ServiceAsync<F, List<T>> listService;
  public final Listing<T> listing;
  protected F filter;
  protected Sync<List<T>> sync;
  
  protected final ClickHandler toSave(final Async<T, T> saving) {
    return new ClickHandler() {
      public void onClick(ClickEvent event) {
        boolean isNew = ((IButton)event.getSource()).getTitle().equals(PLUS);
        saving.invoke(isNew ? null : listing.selection(), new Sync<T>() {
          public void invoke(T t) {
            update();
          }
        });
      }
    };
  }
  
  protected final ClickHandler toRemove(final ServiceAsync<T, Void> removeService) {
    return new ClickHandler() {
      public void onClick(ClickEvent event) {
        removeService.invoke(listing.selection(), new BaseCallback<Void>() {
          public void onSuccess(Void result) {
            update();
          }
        });
      }
    };
  }
  
  protected final SelectionChangedHandler toDisable(final Canvas... canvases) {
    setDisabled(true, canvases);
    return new SelectionChangedHandler() {
      public void onSelectionChanged(SelectionEvent event) {
        setDisabled(listing.grid.getSelectedRecord() == null, canvases);
      }
    };
  }
  
  public BaseCollecting(ServiceAsync<F, List<T>> listService, Listing<T> listing) {
    this.listService = listService;
    this.listing = listing;
    listing.grid.setSelectionType(SelectionStyle.SINGLE);
  }
  
  public void update() {
    listing.grid.setData(new ListGridRecord[0]);
    listService.invoke(filter, new BaseCallback<List<T>>() {
      public void onSuccess(List<T> result) {
        listing.invoke(result, null);
        if (sync != null) {
          sync.invoke(result);
        }
      }
    });
  }
  
  @Override
  public final void invoke(F filter, Sync<List<T>> sync) {
    this.filter = filter;
    this.sync = sync;
    update();
  }
}
