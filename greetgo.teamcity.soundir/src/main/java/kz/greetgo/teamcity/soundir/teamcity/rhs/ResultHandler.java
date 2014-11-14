package kz.greetgo.teamcity.soundir.teamcity.rhs;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public abstract class ResultHandler<R> implements ContentHandler {
  
  public abstract R result();
  
  public abstract String ref();
  
  @Override
  public void setDocumentLocator(Locator locator) {}
  
  @Override
  public void startDocument() throws SAXException {}
  
  @Override
  public void endDocument() throws SAXException {}
  
  @Override
  public void startPrefixMapping(String prefix, String uri) throws SAXException {}
  
  @Override
  public void endPrefixMapping(String prefix) throws SAXException {}
  
  protected abstract void startTag(String name, Attributes atts) throws Exception;
  
  @Override
  public void startElement(String uri, String localName, String qName, Attributes atts)
      throws SAXException {
    try {
      startTag(localName, atts);
    } catch (Exception e) {
      if (e instanceof SAXException) throw (SAXException)e;
      if (e instanceof RuntimeException) throw (RuntimeException)e;
      throw new RuntimeException(e);
    }
  }
  
  protected abstract void endTag(String name) throws Exception;
  
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    try {
      endTag(localName);
    } catch (Exception e) {
      if (e instanceof SAXException) throw (SAXException)e;
      if (e instanceof RuntimeException) throw (RuntimeException)e;
      throw new RuntimeException(e);
    }
  }
  
  protected StringBuilder text;
  
  protected String text() {
    if (text == null) return "";
    return text.toString();
  }
  
  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (text == null) text = new StringBuilder();
    text.append(ch, start, length);
  }
  
  @Override
  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
  
  @Override
  public void processingInstruction(String target, String data) throws SAXException {}
  
  @Override
  public void skippedEntity(String name) throws SAXException {}
  
}
