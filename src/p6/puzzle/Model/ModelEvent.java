package p6.puzzle.Model;

import p6.puzzle.Control.Heuristic;
import p6.puzzle.Event;

public class ModelEvent extends Event {

  public final int imageIndex;
  public final int dimension;
  public final Heuristic heuristic;

  //init puzzle event
  public ModelEvent(Heuristic heuristic, int imageIndex, int dimension) {
    super(EventType.Model);
    this.imageIndex = imageIndex;
    this.dimension = dimension;
    this.heuristic = heuristic;
  }
}
