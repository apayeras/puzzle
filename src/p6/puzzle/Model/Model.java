package p6.puzzle.Model;

import p6.puzzle.Control.Heuristic;
import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;


public class Model implements EventListener {

    private final P6Puzzle p6;
    private Puzzle puzzle;

    public Model(P6Puzzle p6){
        this.p6 = p6;
    }

    public Puzzle getPuzzle(){
        return this.puzzle;
    }

    private void initPuzzle(Heuristic heuristic, int dimension){
        puzzle = new Puzzle(dimension, heuristic);
        p6.notify(new ViewEvent(puzzle.getTable()));
    }

    @Override
    public void notify(Event e) {
        ModelEvent me = (ModelEvent) e;
        if (me.met.equals(ModelEvent.ModelEventType.INIT_TABLE)) initPuzzle(me.heuristic, me.dimension);
        else puzzle.setHeuristic(me.heuristic);
    }
}
