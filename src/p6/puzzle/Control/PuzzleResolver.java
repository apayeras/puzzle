package p6.puzzle.Control;

import p6.puzzle.Model.Puzzle;

public class PuzzleResolver implements Runnable {

  private final Control control;
  private final Heuristic heuristic;
  private final Puzzle puzzle;

  public PuzzleResolver(Control control, Puzzle puzzle, Heuristic heuristic) {
    this.control = control;
    this.puzzle = puzzle;
    this.heuristic = heuristic;
  }

  @Override
  public void run() {

  }
}
