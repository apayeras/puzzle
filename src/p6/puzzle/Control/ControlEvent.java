package p6.puzzle.Control;

import p6.puzzle.Event;
import p6.puzzle.Model.Puzzle;

/**
 * @author Antoni Payeras
 */
public class ControlEvent extends Event {

    public final Puzzle puzzle;

    //SET PUZZLE
    public ControlEvent(Puzzle puzzle){
        super(EventType.Control);
        this.puzzle = puzzle;
    }
    
    
}
