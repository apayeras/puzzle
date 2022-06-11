package p6.puzzle.Control;

import p6.puzzle.Model.Movement;
import p6.puzzle.Model.Puzzle;

import java.util.PriorityQueue;

public class PuzzleResolver implements Runnable {

  private final Control control;
  private Puzzle originalPuzzle;

  public PuzzleResolver(Control control, Puzzle puzzle) {
    this.control = control;
    this.originalPuzzle = puzzle;
  }

  private void resolve(){
    PriorityQueue<Puzzle> pq = new PriorityQueue<>();

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
