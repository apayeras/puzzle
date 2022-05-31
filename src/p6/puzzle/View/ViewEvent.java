package p6.puzzle.View;

import p6.puzzle.Event;
import p6.puzzle.Model.Cell;

import java.util.ArrayList;

/**
 * @author Antoni Payeras
 */
public class ViewEvent extends Event{

    public final ViewEventType type;
    public final Cell[] cells;
    public final ArrayList<int[][]> steps;

    //SET PUZZLE STEPS
    public ViewEvent(ArrayList<int[][]> steps){
        super(EventType.View);
        this.type = ViewEventType.RESOLVED;
        this.steps = steps;
        this.cells = null;
    }

    //SET CELLS EVENT
    public ViewEvent(Cell[] cells) {
        super(EventType.View);
        this.type = ViewEventType.SET_CELLS;
        this.cells = cells;
        this.steps = null;
    }

    enum ViewEventType{
        SET_CELLS,
        RESOLVED,
    }
}