package kz.greetgo.msoffice.docx;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import kz.greetgo.msoffice.docx.Table.TableInfo;

public class TableRow implements XmlWriter {
  private final TableInfo tableInfo;
  private final String ownerPartName;
  private final MSHelper msHelper;
  
  TableRow(TableInfo tableInfo, String ownerPartName, MSHelper msHelper) {
    this.tableInfo = tableInfo;
    this.ownerPartName = ownerPartName;
    this.msHelper = msHelper;
  }
  
  private List<TableCell> cells = new ArrayList<TableCell>();
  
  public TableCell createCell() {
    TableCell ret = new TableCell(tableInfo.getTableCol(cells.size()), ownerPartName, msHelper);
    cells.add(ret);
    return ret;
  }
  
  public TableCell getCell(int index) {
    return cells.get(index);
  }
  
  @Override
  public void write(PrintStream out) {
    out.print("<w:tr>");
    for (TableCell c : cells) {
      c.write(out);
    }
    out.print("</w:tr>");
  }
}
