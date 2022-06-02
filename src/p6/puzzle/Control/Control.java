package p6.puzzle.Control;

import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.Model.Puzzle;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;

import java.util.ArrayList;


public class Control implements EventListener {

    private P6Puzzle p6;

    public Control(P6Puzzle p6){
        this.p6 = p6;
    }

    public void setResolved(ArrayList<int[][]> puzzleSteps){
        p6.notify(new ViewEvent(puzzleSteps));
    }

    private void resolvePuzzle(Puzzle puzzle, Heuristic heuristic){
        PuzzleResolver resolver = new PuzzleResolver(this, puzzle, heuristic);
        (new Thread(resolver)).start();
    }

    @Override
    public void notify(Event e) {
        ControlEvent ce = (ControlEvent) e;
        resolvePuzzle(ce.puzzle, ce.heuristic);
    }

}
