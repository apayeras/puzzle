package p6.puzzle;

import p6.puzzle.Control.Control;
import p6.puzzle.Model.Model;
import p6.puzzle.Model.Puzzle;
import p6.puzzle.View.View;

/**
 * @author Antoni
 */
public class P6Puzzle implements EventListener {

    private Model model;
    private Control control;
    private View view;


    public static void main(String[] args) {
        (new P6Puzzle()).init();
    }


    private void init(){
        this.model = new Model(this);
        this.control = new Control(this);
        this.view = new View(this);
    }

    @Override
    public void notify(Event e) {

    }
}
