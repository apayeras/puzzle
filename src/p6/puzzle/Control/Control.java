package p6.puzzle.Control;

import p6.puzzle.Event;
import p6.puzzle.EventListener;
import p6.puzzle.Model.Puzzle;
import p6.puzzle.P6Puzzle;
import p6.puzzle.View.ViewEvent;

import java.util.ArrayList;
import java.util.Collections;


public class Control implements EventListener {

    private P6Puzzle p6;

    public Control(P6Puzzle p6){
        this.p6 = p6;
    }

    public void setResolved(Puzzle puzzle){

        ArrayList<int[][]> puzzleSteps = new ArrayList<>(puzzle.level()+ 1);
        while(puzzle != null){
            puzzleSteps.add(puzzle.getTable());
            puzzle = puzzle.prev;
        }

        Collections.reverse(puzzleSteps);

        System.out.println("SOLUCIO TROBADA : ");
        for (int i=0;i<puzzleSteps.size();i++){
            System.out.println(printTable(puzzleSteps.get(i)));
        }
        p6.notify(new ViewEvent(puzzleSteps));

    }

    private void resolvePuzzle(Puzzle puzzle, Heuristic heuristic){
        PuzzleResolver resolver = new PuzzleResolver(this, puzzle, heuristic);
        (new Thread(resolver)).start();
    }

    private String printTable(int[][] table){
        String s = "";
        for (int y=0;y<table.length;y++){
            for(int x=0;x<table.length;x++){
                if(table[x][y] == table.length*table.length - 1){
                    s+="💩 ";
                }else {
                    s += table[x][y] + " ";
                }
            }
            s+='\n';
        }
        return s;
    }

    @Override
    public void notify(Event e) {
        ControlEvent ce = (ControlEvent) e;
        resolvePuzzle(ce.puzzle, ce.heuristic);
    }

}
