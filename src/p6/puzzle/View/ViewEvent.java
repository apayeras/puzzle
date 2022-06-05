package p6.puzzle.View;

import p6.puzzle.Event;
import p6.puzzle.Model.Cell;

import java.util.ArrayList;

/**
 * @author Antoni Payeras
 */
public class ViewEvent extends Event{

    public final ViewEventType type;

    public final int[][] table;
    public final ArrayList<int[][]> steps;

    //SET PUZZLE STEPS
    public ViewEvent(ArrayList<int[][]> steps){
        super(EventType.View);
        this.type = ViewEventType.RESOLVED;
        this.steps = steps;
        this.table = null;
    }

    //SET CELLS EVENT
    public ViewEvent(int[][] table) {
        super(EventType.View);
        this.type = ViewEventType.SET_TABLE;
        this.table = table;
        this.steps = null;
    }
    
    
    enum ViewEventType{
        SET_TABLE,
        RESOLVED,
    }
}