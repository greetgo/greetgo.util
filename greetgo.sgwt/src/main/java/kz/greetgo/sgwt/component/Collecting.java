package kz.greetgo.sgwt.component;

import java.util.List;

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
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class Collecting<T, F> extends Snippet {
  private final ServiceAsync<F, List<T>> listService;
  protected final Listing<T> listing;
  public final VLayout root;
  public F filter;
  
  public Collecting(final Saving<T> saving, ServiceAsync<F, List<T>> listService,
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
    
    minus.setDisabled(true);
    ellipsis.setDisabled(true);
    
    plus.setWidth(TOOL);
    minus.setWidth(TOOL);
    ellipsis.setWidth(TOOL);
    
    root = vl(hl(listing.grid, toolbar(vl(plus, minus, ellipsis))));
    root.setMembersMargin(GRAIN);
    root.setWidth100();
  }
  
  public void update() {
    listing.grid.setData((ListGridRecord[])null);
    listService.invoke(filter, new BaseCallback<List<T>>() {
      public void onSuccess(List<T> result) {
        listing.set(result);
      }
    });
  }
}
