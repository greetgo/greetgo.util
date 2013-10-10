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
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;

public abstract class Listing<T> extends Snippet {
  private final ServiceAsync<Void, List<T>> listService;
  protected final ListGrid grid = new ListGrid();
  public final VLayout root;
  
  public Listing(final Saving<T> saving, ServiceAsync<Void, List<T>> listService,
      final ServiceAsync<T, Void> removeService) {
    this.listService = listService;
    
    ClickHandler toSave = new ClickHandler() {
      public void onClick(ClickEvent event) {
        boolean isNew = ((IButton)event.getSource()).getTitle().equals(PLUS);
        saving.invoke(isNew ? null : selection(), new Sync<T>() {
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
        removeService.invoke(selection(), new BaseCallback<Void>() {
          public void onSuccess(Void result) {
            update();
          }
        });
      }
    });
    
    grid.setSelectionType(SelectionStyle.SINGLE);
    grid.addSelectionChangedHandler(new SelectionChangedHandler() {
      public void onSelectionChanged(SelectionEvent event) {
        boolean disabled = grid.getSelectedRecord() == null;
        minus.setDisabled(disabled);
        ellipsis.setDisabled(disabled);
      }
    });
    
    minus.setDisabled(true);
    ellipsis.setDisabled(true);
    
    plus.setWidth(TOOL);
    minus.setWidth(TOOL);
    ellipsis.setWidth(TOOL);
    
    root = vl(hl(grid, toolbar(vl(plus, minus, ellipsis))));
    root.setMembersMargin(GRAIN);
    root.setWidth100();
  }
  
  public void update() {
    grid.setData((ListGridRecord[])null);
    listService.invoke(null, new BaseCallback<List<T>>() {
      public void onSuccess(List<T> result) {
        ListGridRecord[] records = new ListGridRecord[result.size()];
        int i = 0;
        for (T t : result) {
          ListGridRecord record = new ListGridRecord();
          render(record, t);
          record.setAttribute(ASSOCIATED, t);
          records[i++] = record;
        }
        grid.setData(records);
      }
    });
  }
  
  private static final String ASSOCIATED = "ASSOCIATED";
  
  @SuppressWarnings("unchecked")
  protected final T associated(ListGridRecord record) {
    return (T)record.getAttributeAsObject(ASSOCIATED);
  }
  
  protected final T selection() {
    ListGridRecord selectedRecord = grid.getSelectedRecord();
    return selectedRecord == null ? null : associated(selectedRecord);
  }
  
  protected abstract void render(ListGridRecord record, T t);
}
