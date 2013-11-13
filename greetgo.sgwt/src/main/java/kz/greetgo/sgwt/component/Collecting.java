package kz.greetgo.sgwt.component;

import java.util.List;

import kz.greetgo.gwtshare.base.Async;
import kz.greetgo.gwtshare.base.ServiceAsync;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.Layout;

public class Collecting<F, T> extends BaseCollecting<F, T> {
  public final Layout root;
  
  public Collecting(Async<T, T> saving, ServiceAsync<F, List<T>> listService,
      ServiceAsync<T, Void> removeService, Listing<T> listing) {
    super(listService, listing);
    
    ClickHandler toSave = toSave(saving);
    IButton plus = new IButton(PLUS, toSave);
    IButton ellipsis = new IButton(ELLIPSIS, toSave);
    IButton minus = new IButton(MINUS, toRemove(removeService));
    plus.setWidth(TOOL);
    minus.setWidth(TOOL);
    ellipsis.setWidth(TOOL);
    listing.grid.addSelectionChangedHandler(toDisable(minus, ellipsis));
    
    root = vl(hl(listing.grid, toolbar(vl(plus, minus, ellipsis))));
    root.setMembersMargin(GRAIN);
    root.setWidth100();
  }
}
