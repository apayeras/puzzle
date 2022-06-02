package p6.puzzle.Control;

import p6.puzzle.Model.Movement;
import p6.puzzle.Model.Puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PuzzleResolver implements Runnable {

  private final Control control;
  private final Heuristic heuristic;
  private Puzzle puzzle;

  public PuzzleResolver(Control control, Puzzle puzzle, Heuristic heuristic) {
    this.control = control;
    this.puzzle = puzzle;
    this.heuristic = heuristic;
  }

  private int wrongPlacedHeuristic(int[][] table){
    int count = 0;
    int n = table.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (table[i][j] != puzzle.emptyId && table[i][j] != puzzle.solved[i][j]) {
          count++;
        }
      }
    }
    return count;
  }

  private int manhattanHeuristic(int[][] table){
    return -1;
  }

  private int calcHeuristic(int[][] table) {

    int cost = -1;

    switch(this.heuristic){
      case WRONG_PLACED -> {
        cost = wrongPlacedHeuristic(table);
      }
      case MANHATTAN -> {
        cost = manhattanHeuristic(table);
      }
    }

    return cost;
  }

  private void resolve(){
    ArrayList<int[][]> steps = new ArrayList<int[][]>();

    int[][] table = puzzle.getTable();
    steps.add(table);

    PriorityQueue<Puzzle> opts = new PriorityQueue<>(Movement.values().length, new Comparator<Puzzle>() {
      @Override
      public int compare(Puzzle o1, Puzzle o2) {
        return calcHeuristic(o1.getTable()) - calcHeuristic(o2.getTable());
      }
    });

    while(!isResolved(table)){
      for(Movement mov : Movement.values()){
        try{
          if(mov.opposite() == this.puzzle.getLastMove()){
            throw new Exception();
          }

          Puzzle puzzle = new Puzzle(this.puzzle);
          puzzle.move(mov);
          opts.add(puzzle);
        }catch(Exception e){
          //Not valid move, try other
        }
      }
      //B&B
      this.puzzle = opts.peek();
      table =  puzzle.getTable();
      opts.clear();
      //register step
      System.out.print(puzzle.getLastMove() + "\t");
      steps.add(puzzle.getTable());
    }

    //on finish notify Control
    System.out.println("SOLUCIÓ TROBADA DESPRES DE : "+steps.size());
    control.setResolved(steps);
  }

  private boolean isResolved(int table[][]){
    int n = table.length;
    for(int i = 0; i < n; i++){
      for(int j=0; j < n; j++){
        if(table[i][j] != puzzle.solved[i][j]) return false;
      }
    }
    return true;
  }

  @Override
  public void run() {
    resolve();
  }
}
