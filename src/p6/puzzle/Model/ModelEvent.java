package p6.puzzle.Model;

import p6.puzzle.Event;

public class ModelEvent extends Event {

    public final int dimension;
    public final Heuristic heuristic;
    public final ModelEventType met;

    //init puzzle event
    public ModelEvent(Heuristic heuristic, int dimension) {
        super(EventType.Model);
        this.dimension = dimension;
        this.heuristic = heuristic;
        this.met = ModelEventType.INIT_TABLE;
    }
  
    public ModelEvent(Heuristic heuristic) {
        super(EventType.Model);
        this.dimension = 0;
        this.heuristic = heuristic;
        this.met = ModelEventType.SET_HEURISTIC;
    }
  
    enum ModelEventType{
        SET_HEURISTIC,
        INIT_TABLE,
    }
}
