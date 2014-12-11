package kz.greepto.gpen.drawport

class Kolor {
  public int red
  public int green
  public int blue

  def static Kolor from(int red, int green, int blue) { new Kolor(red, green, blue) }

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

  /**
     * The color white.  In the default sRGB space.
     */
  public final static Kolor WHITE = from(255, 255, 255);

  /**
     * The color light gray.  In the default sRGB space.
     */
  public final static Kolor LIGHT_GRAY = from(192, 192, 192);

  /**
     * The color gray.  In the default sRGB space.
     */
  public final static Kolor GRAY = from(128, 128, 128);

  /**
     * The color dark gray.  In the default sRGB space.
     */
  public final static Kolor DARK_GRAY = from(64, 64, 64);

  /**
     * The color black.  In the default sRGB space.
     */
  public final static Kolor BLACK = from(0, 0, 0);

  /**
     * The color red.  In the default sRGB space.
     */
  public final static Kolor RED = from(255, 0, 0);

  /**
     * The color pink.  In the default sRGB space.
     */
  public final static Kolor PINK = from(255, 175, 175);

  /**
     * The color orange.  In the default sRGB space.
     */
  public final static Kolor ORANGE = from(255, 200, 0);

  /**
     * The color yellow.  In the default sRGB space.
     */
  public final static Kolor YELLOW = from(255, 255, 0);

  /**
     * The color green.  In the default sRGB space.
     */
  public final static Kolor GREEN = from(0, 255, 0);

  /**
     * The color magenta.  In the default sRGB space.
     */
  public final static Kolor MAGENTA = from(255, 0, 255);

  /**
     * The color cyan.  In the default sRGB space.
     */
  public final static Kolor CYAN = from(0, 255, 255);

  /**
     * The color blue.  In the default sRGB space.
     */
  public final static Kolor BLUE = from(0, 0, 255);

}
