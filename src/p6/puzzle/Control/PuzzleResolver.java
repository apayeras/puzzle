package p6.puzzle.Control;

import p6.puzzle.Model.Movement;
import p6.puzzle.Model.Puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;

public class PuzzleResolver implements Runnable {

  private final Control control;
  private Puzzle originalPuzzle;

  public PuzzleResolver(Control control, Puzzle puzzle) {
    this.control = control;
    this.originalPuzzle = puzzle;
  }

  private void resolve(){
    PriorityQueue<Puzzle> pq = new PriorityQueue<>(Movement.values().length);
    HashSet<String> visited = new HashSet<String>();

    pq.add(this.originalPuzzle);
    visited.add(this.originalPuzzle.hash());

    while(!pq.isEmpty()){
      Puzzle puzzle = pq.poll();
      if(puzzle.cost() == 0){
        control.setResolved(puzzle);
        return;
      }
      for(Movement mov : Movement.values()){
        if(mov.opposite() != puzzle.getLastMove() && puzzle.isValidMove(mov)){
          Puzzle movedPuzzle = new Puzzle(puzzle, mov);
          String hash = movedPuzzle.hash();
          if(!visited.contains(hash)) {
            pq.add(movedPuzzle);
            visited.add(hash);
          }
        }
      }
    }
  }

  @Override
  public void run() {
    resolve();
  }
}
