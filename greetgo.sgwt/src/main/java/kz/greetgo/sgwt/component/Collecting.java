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
import com.smartgwt.client.widgets.layout.VLayout;

public class Collecting<F, T> extends Snippet implements Async<F, List<T>> {
  public static final int VISIBLE_PLUS = 1;
  public static final int VISIBLE_MINUS = 2;
  public static final int VISIBLE_ELLIPSIS = 4;
  
  private final ServiceAsync<F, List<T>> listService;
  private F filter;
  private Sync<List<T>> sync;
  public final Listing<T> listing;
  public final VLayout root;
  
  public Collecting(final Saving<T> saving, ServiceAsync<F, List<T>> listService,
      final ServiceAsync<T, Void> removeService, Listing<T> listing) {
    this(saving, listService, removeService, listing, VISIBLE_PLUS | VISIBLE_MINUS
        | VISIBLE_ELLIPSIS);
  }
  
  public Collecting(final Saving<T> saving, ServiceAsync<F, List<T>> listService,
      final ServiceAsync<T, Void> removeService, Listing<T> listing, int visibility) {
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
    
    plus.setVisible((visibility & VISIBLE_PLUS) != 0);
    minus.setVisible((visibility & VISIBLE_MINUS) != 0);
    ellipsis.setVisible((visibility & VISIBLE_ELLIPSIS) != 0);
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
