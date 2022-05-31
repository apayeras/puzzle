package p6.puzzle.Model;

public enum Movement{
  TOP,
  BOTTOM,
  LEFT,
  RIGHT;

  private Movement opposite;

  static {
    TOP.opposite = BOTTOM;
    BOTTOM.opposite = TOP;
    LEFT.opposite = RIGHT;
    RIGHT.opposite = LEFT;
  }

  public Movement opposite() {
    return opposite;
  }
}
