package kz.greepto.gpen.editors.gpen.style

import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.FontDef

class TableStyle extends AbstractStyle {
  public Kolor bgColor
  public Kolor borderColor

  public Kolor headerFgColor
  public Kolor headerBgColor
  public FontDef headerFont

  public Kolor contentFgColor
  public Kolor contentBgColor
  public FontDef contentFont

  public Kolor selFgColor
  public Kolor selBgColor
  public FontDef selFont

  public Padding cellPadding
}