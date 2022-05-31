package p6.puzzle.Model;

import p6.puzzle.Event;

public class ModelEvent extends Event {

  public final int imageIndex;
  public final int dimension;

  //init puzzle event
  public ModelEvent(int imageIndex, int dimension) {
    super(EventType.Model);
    this.imageIndex = imageIndex;
    this.dimension = dimension;
  }
}
