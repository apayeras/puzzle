package p6.puzzle.Model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class Model{

    File[] puzzleImages;
    Puzzle puzzle;

    public Model(int imageIndex, int dimension){
        loadPuzzleImages();
        puzzle = new Puzzle(puzzleImages[imageIndex], dimension);
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

}
