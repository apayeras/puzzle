package p6.puzzle.Model;

import p6.puzzle.Control.ControlEvent;
import p6.puzzle.Control.Heuristic;
import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;

import java.io.File;
import java.io.FilenameFilter;

public class Model implements EventListener {

    private final P6Puzzle p6;
    public final File[] puzzleImages;
    private Puzzle puzzle;

    public Model(P6Puzzle p6){
        this.p6 = p6;
        this.puzzleImages = loadPuzzleImages();
    }

    private File[] loadPuzzleImages(){
        String userDir = System.getProperty("user.dir");
        File imagesDir = new File(userDir + "/assets");

        if(imagesDir.isDirectory()){
            FilenameFilter imageFilter = new FilenameFilter(){

                @Override
                public boolean accept(File dir, String name) {
                    if(name.endsWith(".jpg")){
                        return true;
                    }
                    return false;
                }
            };
            return imagesDir.listFiles();
        }
        return new File[0];
    }

    public Puzzle getPuzzle(){
        return this.puzzle;
    }

    private void initPuzzle(Heuristic heuristic, int imageIndex, int dimension){
        puzzle = new Puzzle(puzzleImages[imageIndex], dimension, heuristic);
        //p6.notify(new ControlEvent(puzzle));
        p6.notify(new ViewEvent(puzzle.getTable()));
    }

    @Override
    public void notify(Event e) {
        ModelEvent me = (ModelEvent) e;
        initPuzzle(me.heuristic, me.imageIndex, me.dimension);
    }
}
