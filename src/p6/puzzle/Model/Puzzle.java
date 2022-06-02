package p6.puzzle.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Puzzle {

  public final int dimension;
  public final Cell[] cells;
  public final int[][] solved;
  private int[][] table;
  private int emptyX;
  private int emptyY;
  public int emptyId;
  private Movement lastMove;

  public Puzzle(Puzzle puzzle){
    this.dimension = puzzle.dimension;
    this.cells = puzzle.cells;
    this.emptyId = puzzle.emptyId;
    this.emptyX = puzzle.emptyX;
    this.emptyY = puzzle.emptyY;
    this.solved = cloneMatrix(puzzle.solved);
    this.table = cloneMatrix(puzzle.table);
    this.lastMove = puzzle.lastMove;
  }

  public Puzzle(File imageFile, int dimension) {
    this.dimension = dimension;
    this.cells = new Cell[dimension*dimension];
    this.solved = initPuzzle();
    createCells(imageFile);
    shakePuzzle();
  }

  private int[][] initPuzzle() {
    table = new int[dimension][dimension];
    int pos = 0;

    for(int i=0;i<dimension;i++) {
      for(int j=0;j<dimension;j++) {
        table[j][i] = pos++;
      }
    }

    emptyId = table[dimension-1][dimension-1];

    emptyX = dimension-1;
    emptyY = dimension-1;

    return cloneMatrix(table);
  }

  private void shakePuzzle(){
    try {
      Movement[] moves = Movement.values();
      Movement lastMove = null;
      Random rand = new Random();
      // Nº of movements
      int N_MOVES = rand.nextInt(5, 20) /* dimension*/;

      while(N_MOVES > 0){
        int movIndex = rand.nextInt(0, moves.length);
        while(moves[movIndex].opposite() == lastMove || !isValidMove(moves[movIndex])){
          movIndex = rand.nextInt(0, moves.length);
        }

        move(moves[movIndex]); //throws Exception
        lastMove = moves[movIndex];
        N_MOVES--;
      }
      //reset last move on shake
      this.lastMove = null;
    } catch (Exception e) {
      //Not accessible, we check if valid move before move
      System.out.println("EXCEPTIOOOOOON");
    }
  }

  private int[] getMoveValues(Movement move){
    int movX = 0;
    int movY = 0;

    switch(move){
      //blanc per amunt
      case TOP -> {
        movY--;
      }
      //blanc per avall
      case BOTTOM -> {
        movY++;
      }
      //blanc esquerra
      case LEFT -> {
        movX--;
      }
      //blanc dreta
      case RIGHT -> {
        movX++;
      }
    }

    return new int[]{movX, movY};
  }

  public boolean isValidMove(Movement move){
    switch(move){
      //blanc amunt
      case TOP -> {
        if(emptyY - 1 < 0) return false;
      }
      //blanc abaix
      case BOTTOM -> {
        if(emptyY + 1 >= dimension) return false;
      }
      //blanc esquerra
      case LEFT -> {
        if(emptyX - 1 < 0) return false;
      }
      //blanc dreta
      case RIGHT -> {
        if(emptyX + 1 >= dimension) return false;
      }
    }
    return true;
  }

  public int[][] move(Movement move) throws Exception{
    if(!isValidMove(move)){
      throw new Exception("Not valid movement");
    }

    int[] moveValues = getMoveValues(move);
    int movX = moveValues[0];
    int movY = moveValues[1];

    int prevId = table[movX + emptyX][movY + emptyY];
    //replace empty id
    table[emptyX][emptyY] = prevId;

    //update empty pos
    emptyX += movX;
    emptyY += movY;
    table[emptyX][emptyY] = emptyId;

    //update last move
    lastMove = move;

    return cloneMatrix(table);
  }

  public Movement getLastMove(){
    return this.lastMove;
  }

  private void createCells(File imageFile){
    try {
      BufferedImage image = ImageIO.read(imageFile);
      final int WIDTH = image.getWidth();
      final int HEIGHT = image.getHeight();

      final int SUB_WIDTH = WIDTH / dimension;
      final int SUB_HEIGHT = HEIGHT / dimension;

      int accWidth = 0;
      int accHeight = 0;
      int pos = 0;

      for(int i=0; i<dimension;i++){
        for (int j=0;j<dimension;j++){
          if(pos == emptyId){
            cells[pos] = new Cell(null, emptyId);
            continue;
          }
          BufferedImage subImage = image.getSubimage(accWidth, accHeight, SUB_WIDTH, SUB_HEIGHT);
          cells[pos] = new Cell(subImage, pos);
          pos++;
          accWidth += SUB_WIDTH;
        }
        accWidth = 0;
        accHeight += SUB_HEIGHT;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static int[][] cloneMatrix(int[][] matrix){
    return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
  }

  public int[][] getTable() {
    return this.table;
  }
}
