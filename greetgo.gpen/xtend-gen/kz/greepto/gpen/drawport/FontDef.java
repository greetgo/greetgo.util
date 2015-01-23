package kz.greepto.gpen.drawport;

import com.google.common.base.Objects;

@SuppressWarnings("all")
public class FontDef {
  private boolean italic = false;
  
  private boolean bold = false;
  
  private String family = "Arial";
  
  private int height = 12;
  
  public int hashCode() {
    int prime = 31;
    int result = 1;
    int _xifexpression = (int) 0;
    if (this.bold) {
      _xifexpression = 1231;
    } else {
      _xifexpression = 1237;
    }
    int _plus = ((prime * result) + _xifexpression);
    result = _plus;
    int _hashCode = 0;
    if (this.family!=null) {
      _hashCode=this.family.hashCode();
    }
    int _plus_1 = ((prime * result) + _hashCode);
    result = _plus_1;
    result = ((prime * result) + this.height);
    int _xifexpression_1 = (int) 0;
    if (this.italic) {
      _xifexpression_1 = 1231;
    } else {
      _xifexpression_1 = 1237;
    }
    int _plus_2 = ((prime * result) + _xifexpression_1);
    result = _plus_2;
    return result;
  }
  
  public boolean equals(final Object obj) {
    boolean _tripleEquals = (obj == null);
    if (_tripleEquals) {
      return false;
    }
    boolean _tripleEquals_1 = (this == obj);
    if (_tripleEquals_1) {
      return true;
    }
    if ((!(obj instanceof FontDef))) {
      return false;
    }
    FontDef a = ((FontDef) obj);
    boolean _notEquals = (!Objects.equal(this.family, a.family));
    if (_notEquals) {
      return false;
    }
    if ((this.height != a.height)) {
      return false;
    }
    if (((this.bold && (!a.bold)) || ((!this.bold) && a.bold))) {
      return false;
    }
    if (((this.italic && (!a.italic)) || ((!this.italic) && a.italic))) {
      return false;
    }
    return true;
  }
  
  public void assign(final FontDef other) {
    this.family = other.family;
    this.bold = other.bold;
    this.italic = other.italic;
    this.height = other.height;
  }
  
  public FontDef copy() {
    FontDef ret = new FontDef();
    ret.assign(this);
    return ret;
  }
  
  public void setFamily(final String family) {
    this.family = family;
  }
  
  public String getFamily() {
    return this.family;
  }
  
  public void setItalic(final boolean italic) {
    this.italic = italic;
  }
  
  public boolean isItalic() {
    return this.italic;
  }
  
  public void setBold(final boolean bold) {
    this.bold = bold;
  }
  
  public boolean isBold() {
    return this.bold;
  }
  
  public void setHeight(final int height) {
    this.height = height;
  }
  
  public int getHeight() {
    return this.height;
  }
  
  public FontDef norm() {
    FontDef _xblockexpression = null;
    {
      this.bold = false;
      this.italic = false;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public FontDef i() {
    FontDef _xblockexpression = null;
    {
      this.italic = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public FontDef b() {
    FontDef _xblockexpression = null;
    {
      this.bold = true;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public FontDef f(final String family) {
    FontDef _xblockexpression = null;
    {
      this.family = family;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public FontDef h(final int height) {
    FontDef _xblockexpression = null;
    {
      this.height = height;
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public static FontDef arial() {
    FontDef _fontDef = new FontDef();
    return _fontDef.f("Arial");
  }
  
  public static FontDef timesNewRoman() {
    FontDef _fontDef = new FontDef();
    return _fontDef.f("Times New Roman");
  }
}
