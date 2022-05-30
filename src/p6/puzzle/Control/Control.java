package p6.puzzle.Control;

import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.P6Puzzle;


public class Control implements EventListener {

    private P6Puzzle main;

    public Control(P6Puzzle main){
        this.main = main;
    }

    @Override
    public void notify(Event e) {
        ControlEvent ce = (ControlEvent) e;
    }
}
