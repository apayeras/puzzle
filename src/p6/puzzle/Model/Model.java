package p6.puzzle.Model;

import p6.puzzle.Control.ControlEvent;
import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;

import java.io.File;
import java.io.FilenameFilter;

public class Model implements EventListener {

    private final P6Puzzle p6;
    File[] puzzleImages;
    Puzzle puzzle;

    public Model(P6Puzzle p6){
        this.p6 = p6;
        loadPuzzleImages();
    }

    private void loadPuzzleImages(){
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
            puzzleImages = imagesDir.listFiles();
        }
    }

    public Puzzle getPuzzle(){
        return this.puzzle;
    }

    private void initPuzzle(int imageIndex, int dimension){
        puzzle = new Puzzle(puzzleImages[imageIndex], dimension);
        p6.notify(new ControlEvent(puzzle));
        p6.notify(new ViewEvent(puzzle.cells));
    }

    @Override
    public void notify(Event e) {
        ModelEvent me = (ModelEvent) e;
        initPuzzle(me.imageIndex, me.dimension);
    }
}
