package kz.greetgo.sgwt.component;

import java.util.ArrayList;
import java.util.List;

import kz.greetgo.gwtshare.base.Forcible;
import kz.greetgo.gwtshare.base.Sync;
import kz.greetgo.sgwt.base.Snippet;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public abstract class Listing<T> extends Snippet implements Forcible<List<T>, List<T>> {
  private static final String ASSOCIATED = "ASSOCIATED";
  
  private Sync<List<T>> sync;
  
  public final ListGrid grid = new ListGrid();
  
  @Override
  public final void force() {
    if (sync == null) {
      return;
    }
    List<T> list = new ArrayList<T>();
    for (ListGridRecord record : grid.getRecords()) {
      list.add(associated(record));
    }
    sync.invoke(list);
  }
  
  @Override
  public final void invoke(List<T> list, Sync<List<T>> sync) {
    this.sync = sync;
    ListGridRecord[] records = new ListGridRecord[list.size()];
    int i = 0;
    for (T t : list) {
      records[i++] = render(t);
    }
    grid.setData(records);
  }
  
  public final void add(T t) {
    grid.addData(render(t));
  }
  
  @SuppressWarnings("unchecked")
  public final T associated(ListGridRecord record) {
    return (T)record.getAttributeAsObject(ASSOCIATED);
  }
  
  public final T selection() {
    ListGridRecord selectedRecord = grid.getSelectedRecord();
    return selectedRecord == null ? null : associated(selectedRecord);
  }
  
  protected final ListGridRecord render(T t) {
    ListGridRecord record = new ListGridRecord();
    render(record, t);
    record.setAttribute(ASSOCIATED, t);
    return record;
  }
  
  protected abstract void render(ListGridRecord record, T t);
}
