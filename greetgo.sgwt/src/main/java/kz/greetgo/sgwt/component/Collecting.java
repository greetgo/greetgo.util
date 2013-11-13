package kz.greetgo.sgwt.component;

import java.util.List;

import kz.greetgo.gwtshare.base.Async;
import kz.greetgo.gwtshare.base.ServiceAsync;
import kz.greetgo.gwtshare.base.Sync;
import kz.greetgo.sgwt.base.BaseCallback;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Collecting<F, T> extends Snippet implements Async<F, List<T>> {
  private final ServiceAsync<F, List<T>> listService;
  private F filter;
  private Sync<List<T>> sync;
  public final Listing<T> listing;
  public final Layout root;
  
  public Collecting(final Async<T, T> saving, ServiceAsync<F, List<T>> listService,
      final ServiceAsync<T, Void> removeService, Listing<T> listing) {
    this.listService = listService;
    this.listing = listing;
    
    ClickHandler toSave = new ClickHandler() {
      public void onClick(ClickEvent event) {
        boolean isNew = ((IButton)event.getSource()).getTitle().equals(PLUS);
        saving.invoke(isNew ? null : Collecting.this.listing.selection(), new Sync<T>() {
          public void invoke(T t) {
            update();
          }
        });
      }
    };
    
    IButton plus = new IButton(PLUS, toSave);
    final IButton ellipsis = new IButton(ELLIPSIS, toSave);
    
    final IButton minus = new IButton(MINUS, new ClickHandler() {
      public void onClick(ClickEvent event) {
        removeService.invoke(Collecting.this.listing.selection(), new BaseCallback<Void>() {
          public void onSuccess(Void result) {
            update();
          }
        });
      }
    });
    
    listing.grid.setSelectionType(SelectionStyle.SINGLE);
    listing.grid.addSelectionChangedHandler(new SelectionChangedHandler() {
      public void onSelectionChanged(SelectionEvent event) {
        boolean disabled = Collecting.this.listing.grid.getSelectedRecord() == null;
        minus.setDisabled(disabled);
        ellipsis.setDisabled(disabled);
      }
    });
    
    minus.disable();
    ellipsis.disable();
    
    root = layout(plus, minus, ellipsis);
  }
  
  protected Layout layout(IButton plus, IButton minus, IButton ellipsis) {
    plus.setWidth(TOOL);
    minus.setWidth(TOOL);
    ellipsis.setWidth(TOOL);
    
    VLayout layout = vl(hl(listing.grid, toolbar(vl(plus, minus, ellipsis))));
    layout.setMembersMargin(GRAIN);
    layout.setWidth100();
    return layout;
  }
  
  public void update() {
    listing.grid.setData((ListGridRecord[])null);
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
  public void invoke(F filter, Sync<List<T>> sync) {
    this.filter = filter;
    this.sync = sync;
    update();
  }
}
