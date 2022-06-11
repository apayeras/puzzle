package p6.puzzle.Control;

import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.Model.Puzzle;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;

import java.util.ArrayList;
import java.util.Collections;


public class Control implements EventListener {

    private P6Puzzle p6;

    public Control(P6Puzzle p6){
        this.p6 = p6;
    }

    public void setResolved(Puzzle puzzle){

        ArrayList<int[][]> puzzleSteps = new ArrayList<>(puzzle.level()+ 1);
        while(puzzle != null){
            puzzleSteps.add(puzzle.getTable());
            puzzle = puzzle.prev;
        }

        Collections.reverse(puzzleSteps);

        p6.notify(new ViewEvent(puzzleSteps));

    }

    private void resolvePuzzle(Puzzle puzzle){
        PuzzleResolver resolver = new PuzzleResolver(this, puzzle);
        (new Thread(resolver)).start();
    }

    @Override
    public void notify(Event e) {
        ControlEvent ce = (ControlEvent) e;
        resolvePuzzle(ce.puzzle);
    }

}
