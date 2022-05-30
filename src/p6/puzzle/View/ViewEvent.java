package p6.puzzle.View;

import p6.puzzle.Event;

/**
 * @author Antoni Payeras
 */
public class ViewEvent extends Event{

    public ViewEvent() {
        super(EventType.View);
    }
}