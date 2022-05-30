package p6.puzzle;

import p6.puzzle.Model.Model;
import p6.puzzle.Model.Puzzle;

/**
 * @author Antoni
 */
public class P6Puzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Model model = new Model(0, 8);
        Puzzle puzzle = model.getPuzzle();
        System.out.println("🌵");
    }

}
