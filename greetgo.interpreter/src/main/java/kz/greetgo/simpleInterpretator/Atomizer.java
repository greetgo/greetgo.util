package kz.greetgo.simpleInterpretator;

public class Atomizer {
  private AtomHandler atomHandler;
  
  public void setAtomHandler(AtomHandler atomHandler) {
    this.atomHandler = atomHandler;
  }
  
  private boolean inSpace = true;
  private StringBuilder chars = null;
  
  private char kav;
  private boolean inKav = false;
  private char prevChar;
  private boolean inWord = false;
  private boolean inSymbol = false;
  private boolean inDecimal = false;
  private boolean inDate = false;
  
  public void append(char c) {
    if (inKav) {
      if (c != kav) {
        appendChar(c);
        return;
      }
      if (prevChar == '\\') {
        appendChar(c);
        return;
      }
      completeAtom();
      return;
    }
    if (HelperChar.isKav(c)) {
      completeAtom();
      inKav = true;
      kav = c;
      appendChar(c);
      return;
    }
    if (inDate) {
      if (c == '}') {
        completeAtom();
        inDate = false;
        return;
      }
      appendChar(c);
      return;
    }
    
    if (HelperChar.isSpace(c)) {
      if (inSpace) return;
      completeAtom();
      inSpace = true;
      return;
    }
    inSpace = false;
    if (HelperChar.isWord(c)) {
      if (inWord || inDecimal) {
        appendChar(c);
        return;
      }
      completeAtom();
      if (HelperChar.isDigit(c)) {
        inDecimal = true;
      } else {
        inWord = true;
      }
      appendChar(c);
      return;
    }
    if (inDecimal && (c == '.' || c == ',')) {
      appendChar('.');
      return;
    }
    if (HelperChar.isSymbol(c)) {
      if (inSymbol) {
        appendChar(c);
        return;
      }
      completeAtom();
      inSymbol = true;
      appendChar(c);
      return;
    }
    if (c == '{') {
      if (inDate) {
        appendChar(c);
        return;
      }
      completeAtom();
      inDate = true;
      return;
    }
    
    throw new InterpreterError(ErrorCode.UNKNOWN_CHAR, "Unknown character: '" + c + "'");
  }
  
  private void completeAtom() {
    if (inKav) {
      Atom a = new Atom();
      a.setType(AtomType.STRING);
      a.setValue(value());
      atomHandler.append(a);
      chars = null;
      inKav = false;
      return;
    }
    if (inWord) {
      Atom a = new Atom();
      a.setType(AtomType.WORD);
      a.setValue(value());
      atomHandler.append(a);
      chars = null;
      inWord = false;
      return;
    }
    if (inDecimal) {
      Atom a = new Atom();
      a.setType(AtomType.DECIMAL);
      a.setValue(value());
      atomHandler.append(a);
      chars = null;
      inDecimal = false;
      return;
    }
    if (inSymbol) {
      completeSymbol();
      inSymbol = false;
      chars = null;
      return;
    }
    if (inDate) {
      String value = "";
      if (chars != null) value = chars.toString();
      inDate = false;
      chars = null;
      {
        Atom atom = new Atom();
        atom.setType(AtomType.DATE);
        atom.setValue(value);
        atomHandler.append(atom);
      }
      return;
    }
  }
  
  private String value() {
    if (chars == null) return null;
    return chars.toString();
  }
  
  private void appendChar(char c) {
    if (chars == null) chars = new StringBuilder();
    chars.append(c);
    prevChar = c;
  }
  
  public void complete() {
    completeAtom();
    atomHandler.complete();
  }
  
  private void completeSymbol() {
    while (chars != null && chars.length() > 0) {
      String value = null;
      for (String bs : HelperOperator.bigSymbols()) {
        int bsLength = bs.length();
        if (chars.length() < bsLength) continue;
        String x = chars.substring(0, bsLength);
        if (x.equals(bs)) {
          value = x;
          chars.delete(0, bsLength);
          break;
        }
      }
      if (value == null) {
        value = chars.substring(0, 1);
        chars.delete(0, 1);
      }
      {
        Atom atom = new Atom();
        atom.setType(AtomType.SYMBOL);
        atom.setValue(value);
        atomHandler.append(atom);
      }
    }
  }
}
