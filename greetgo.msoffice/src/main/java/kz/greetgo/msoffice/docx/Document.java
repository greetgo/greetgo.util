package kz.greetgo.msoffice.docx;

import java.io.PrintStream;

public class Document extends DocumentFlow {
  
  Document(String partName, MSHelper msHelper) {
    super(partName, msHelper);
  }
  
  @Override
  public ContentType getContentType() {
    return ContentType.DOCUMENT;
  }
  
  @Override
  protected void writeTopXml(PrintStream out) {
    out.print("<?xml version=\"1.0\" encoding=\"UTF-8\"" + " standalone=\"yes\"?>\n");
    out.print("<w:document xmlns:ve=\"http://schemas.openxmlformats.org/"
        + "markup-compatibility/2006\"" + " xmlns:o=\"urn:schemas-microsoft-com:office:office\""
        + " xmlns:r=\"http://schemas.openxmlformats.org/" + "officeDocument/2006/relationships\""
        + " xmlns:m=\"http://schemas.openxmlformats.org/" + "officeDocument/2006/math\""
        + " xmlns:v=\"urn:schemas-microsoft-com:vml\""
        + " xmlns:wp=\"http://schemas.openxmlformats.org/"
        + "drawingml/2006/wordprocessingDrawing\""
        + " xmlns:w10=\"urn:schemas-microsoft-com:office:word\""
        + " xmlns:w=\"http://schemas.openxmlformats.org/" + "wordprocessingml/2006/main\""
        + " xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/" + "wordml\">");
    out.print("<w:body>");
  }
  
  @Override
  protected void writeBottomXml(PrintStream out) {
    out.print("<w:sectPr>");
    writeReferences(out);
    out.print("<w:pgSz w:w=\"11906\" w:h=\"16838\" />");
    out.print("<w:pgMar w:top=\"1134\" w:bottom=\"1134\""
        + " w:left=\"1701\" w:header=\"708\" w:footer=\"708\"" + " w:gutter=\"0\" />");
    out.print("<w:cols w:space=\"708\" />");
    out.print("<w:docGrid w:linePitch=\"360\" />");
    out.print("</w:sectPr>");
    
    out.print("</w:body>");
    out.print("</w:document>");
  }
  
  public void addPageBreak() {
    createPara().addPageBreak();
  }
  
}
