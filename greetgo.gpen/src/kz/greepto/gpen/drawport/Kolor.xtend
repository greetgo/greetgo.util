package kz.greepto.gpen.drawport

class Kolor {
  public int red
  public int green
  public int blue

  def static Kolor rgb(int red, int green, int blue) { new Kolor(red, green, blue) }

  def static Kolor copy(Kolor k) { new Kolor(k) }

  private new() {
    this(0, 0, 0)
  }

  private new(int red, int green, int blue) {
    this.red = red
    this.green = green
    this.blue = blue
  }

  private new(Kolor k) {
    this(k?.red, k?.green, k?.blue)
  }

  override toString() { "Kolor#RGB(" + red + ", " + green + ", " + blue + ")" }

  /**
     * The color white.  In the default sRGB space.
     */
  public final static Kolor WHITE = rgb(255, 255, 255);

  /**
     * The color light gray.  In the default sRGB space.
     */
  public final static Kolor LIGHT_GRAY = rgb(192, 192, 192);

  /**
     * The color gray.  In the default sRGB space.
     */
  public final static Kolor GRAY = rgb(128, 128, 128);

  /**
     * The color dark gray.  In the default sRGB space.
     */
  public final static Kolor DARK_GRAY = rgb(64, 64, 64);

  /**
     * The color black.  In the default sRGB space.
     */
  public final static Kolor BLACK = rgb(0, 0, 0);

  /**
     * The color red.  In the default sRGB space.
     */
  public final static Kolor RED = rgb(255, 0, 0);

  /**
     * The color pink.  In the default sRGB space.
     */
  public final static Kolor PINK = rgb(255, 175, 175);

  /**
     * The color orange.  In the default sRGB space.
     */
  public final static Kolor ORANGE = rgb(255, 200, 0);

  /**
     * The color yellow.  In the default sRGB space.
     */
  public final static Kolor YELLOW = rgb(255, 255, 0);

  /**
     * The color green.  In the default sRGB space.
     */
  public final static Kolor GREEN = rgb(0, 255, 0);

  /**
     * The color magenta.  In the default sRGB space.
     */
  public final static Kolor MAGENTA = rgb(255, 0, 255);

  /**
     * The color cyan.  In the default sRGB space.
     */
  public final static Kolor CYAN = rgb(0, 255, 255);

  val static FACTOR = 0.7f

  /**
     * The color blue.  In the default sRGB space.
     */
  public final static Kolor BLUE = rgb(0, 0, 255);

  def Kolor brighter() {
    var r = red;
    var g = green;
    var b = blue;

    /* From 2D group:
     * 1. black.brighter() should return grey
     * 2. applying brighter to blue will always return blue, brighter
     * 3. non pure color (non zero rgb) will eventually return white
     */
    var i = (1.0 / (1.0 - FACTOR)) as int;
    if (r == 0 && g == 0 && b == 0) {
      return new Kolor(i, i, i);
    }
    if(r > 0 && r < i) r = i;
    if(g > 0 && g < i) g = i;
    if(b > 0 && b < i) b = i;

    return new Kolor(//
    Math.min((r / FACTOR) as int, 255),//
    Math.min((g / FACTOR) as int, 255),//
    Math.min((b / FACTOR) as int, 255))
  }

  def Kolor darker() {
    return new Kolor(//
    Math.max((red * FACTOR) as int, 0), //
    Math.max((green * FACTOR) as int, 0),//
    Math.max((blue * FACTOR) as int, 0))
  }
}
