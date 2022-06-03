package p6.puzzle.Control;

import p6.puzzle.Model.Movement;
import p6.puzzle.Model.Puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class PuzzleResolver implements Runnable {

  private final Control control;
  private final Heuristic heuristic;
  private Puzzle originalPuzzle;

  public PuzzleResolver(Control control, Puzzle puzzle, Heuristic heuristic) {
    this.control = control;
    this.originalPuzzle = puzzle;
    this.heuristic = heuristic;
  }

  private void resolve(){
    PriorityQueue<Puzzle> pq = new PriorityQueue<>(Movement.values().length);

    pq.add(this.originalPuzzle);

    while(!pq.isEmpty()){
      Puzzle puzzle = pq.poll();
      if(puzzle.cost() == 0){
        control.setResolved(puzzle);
        return;
      }
      for(Movement mov : Movement.values()){
        if(mov.opposite() != puzzle.getLastMove() && puzzle.isValidMove(mov)){
          Puzzle movedPuzzle = new Puzzle(puzzle, mov);
          pq.add(movedPuzzle);
        }
      }
    }
  }

  @Override
  public void run() {
    resolve();
  }
}
