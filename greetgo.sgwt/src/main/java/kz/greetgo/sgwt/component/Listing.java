package kz.greetgo.sgwt.component;

import java.util.List;

import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class Listing<T> extends Snippet {
  public final ListGrid grid = new ListGrid();
  
  private static final String ASSOCIATED = "ASSOCIATED";
  
  protected final void render(List<T> result) {
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
  
  @SuppressWarnings("unchecked")
  public final T associated(ListGridRecord record) {
    return (T)record.getAttributeAsObject(ASSOCIATED);
  }
  
  public final T selection() {
    ListGridRecord selectedRecord = grid.getSelectedRecord();
    return selectedRecord == null ? null : associated(selectedRecord);
  }
  
  protected abstract void render(ListGridRecord record, T t);
}
