package kz.greetgo.msoffice.xlsx.probes;

import java.util.Date;
import java.util.List;

import kz.greetgo.msoffice.xlsx.parse.Cell;
import kz.greetgo.msoffice.xlsx.parse.RowHandler;
import kz.greetgo.msoffice.xlsx.parse.XlsxParser;

public class ParseBook3 {
  public static void main(String[] args) {
    XlsxParser p = new XlsxParser();
    p.removeWorkDirOnClose = false;
    
    System.out.println("start=" + new Date());
    p.load(ParseBook1.class.getResourceAsStream("reportClientAttributet.xlsx"));
    System.out.println("end=" + new Date());
    
    List<Cell> row = p.activeSheet().loadRow(2);
    
    System.out.println(row.size() + " ~ " + row);
    
    p.activeSheet().scanRows(p.activeSheet().loadRow(0).size(), new RowHandler() {
      @Override
      public void handle(List<Cell> row, int rowIndex) throws Exception {
        // System.out.println("[" + rowIndex + "] " + row);
      }
    });
    
    p.close();
  }
}
