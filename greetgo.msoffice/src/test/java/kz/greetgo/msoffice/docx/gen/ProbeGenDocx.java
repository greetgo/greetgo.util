package kz.greetgo.msoffice.docx.gen;

import kz.greetgo.msoffice.docx.Align;
import kz.greetgo.msoffice.docx.Document;
import kz.greetgo.msoffice.docx.Docx;
import kz.greetgo.msoffice.docx.Para;
import kz.greetgo.msoffice.docx.Run;
import kz.greetgo.msoffice.docx.Table;
import kz.greetgo.msoffice.docx.TableCell;
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
      Table table = doc.createTable();
      {
        TableRow row =table.createRow();
        {
          TableCell tableCell=row.createCell();
          {
            Para para = tableCell.createPara();
            {
              Run частьАбзаца = para.createRun();
              частьАбзаца.addText("column one");
            }
          }
        }
        {
          TableCell tableCell=row.createCell();
          {
            Para para = tableCell.createPara();
            {
              Run частьАбзаца = para.createRun();
              частьАбзаца.addText("column two");
            }
          }
        }
        {
          TableCell tableCell=row.createCell();
          {
            Para para = tableCell.createPara();
            {
              Run частьАбзаца = para.createRun();
              частьАбзаца.addText("column three");
            }
          }
        }
        {
          TableCell tableCell=row.createCell();
          {
            Para para = tableCell.createPara();
            {
              Run частьАбзаца = para.createRun();
              частьАбзаца.addText("column four");
            }
          }
        }
      }
    }
    
    docx.write("build/example-gen.docx");
    
    System.out.println("OK");
  }
}
