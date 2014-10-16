package kz.greetgo.msoffice.docx.gen;

import kz.greetgo.msoffice.docx.Align;
import kz.greetgo.msoffice.docx.Document;
import kz.greetgo.msoffice.docx.Docx;
import kz.greetgo.msoffice.docx.LineStyle;
import kz.greetgo.msoffice.docx.Para;
import kz.greetgo.msoffice.docx.Run;
import kz.greetgo.msoffice.docx.Table;
import kz.greetgo.msoffice.docx.TableCell;
import kz.greetgo.msoffice.docx.TableCol;
import kz.greetgo.msoffice.docx.TableRow;
import kz.greetgo.msoffice.docx.Underline;

public class ProbeGenDocx {
  public static void main(String[] args) {
    Docx docx = new Docx();
    
    Document doc = docx.getDocument();
    {
      Para para = doc.createPara();
      
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.setUnderline(Underline.DOUBLE);
        частьАбзаца.addText("Вот часть абзаца дважды подчёркнутая,");
      }
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.addText("а вот не подчёркнутая, ");
      }
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.setBold(true);
        частьАбзаца.addText("а вот жирная.");
      }
    }
    
    {
      Para para = doc.createPara();
      para.setAlign(Align.RIGHT);
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.addText("Вот ещё один абзац");
      }
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.setItalic(true);
        частьАбзаца.setBold(true);
        частьАбзаца.addText(" выровненный справа.");
      }
    }
    
    doc.createPara().createRun().addText("");
    doc.createPara().createRun().addText("");
    
    {
      Para para = doc.createPara();
      para.setAlign(Align.CENTER);
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.setBold(true);
        частьАбзаца.setTextSize(70);
        частьАбзаца.addText("Вот так!");
      }
    }
    
    {
      Para para = doc.createPara();
      para.setAlign(Align.CENTER);
      {
        Run частьАбзаца = para.createRun();
        частьАбзаца.setBold(true);
        частьАбзаца.setTextSize(70);
      }
    }
    Table table = doc.createTable();
    table.setTableWidth(9540);
    table.setTblCellSpacing(0);
    table.getMargins().setWidth(105);
    table.setTableLookVal("04A0");
    {
      TableRow row = table.createRow();
      row.setTblCellSpacing(0);
      {
        TableCol col1 = table.createCol();
        col1.setWidth(4601);
        TableCell cell1 = row.createCell();
        cell1.setWidth(9300);
        cell1.setGridSpan(3);
        cell1.getBorders().setLineStyle(LineStyle.SINGLE);
        cell1.getBorders().setSize(4);
        cell1.getBorders().getInsideH().setSize(0);
        cell1.getBorders().getInsideV().setSize(0);
        cell1.getMargins().setWidth(108);
        cell1.getMargins().getTop().setWidth(0);
        cell1.getMargins().getBottom().setWidth(0);
        cell1.getShd().setVal("clear");
        cell1.getShd().setFill("C0C0C0");
        Para para = cell1.createPara();
        para.setAlign(Align.BOTH);
        Run title = para.createRun();
        title.setTextSize(13);
        title.setFontName("Arial");
        for (int i = 0; i < 10; i++) {
          title.addText("Краткая информация по Заемщику/Гаранту и проекту " + i);
        }
        
      }
    }
    
    docx.write("build/example-gen.docx");
    
    System.out.println("OK");
  }
}
