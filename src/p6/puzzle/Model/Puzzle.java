package p6.puzzle.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Puzzle {

  public final int dimension;
  public final Cell[] cells;
  private int[][] table;
  private int emptyX;
  private int emptyY;
  private int emptyId;

  public Puzzle(File imageFile, int dimension) {
    this.dimension = dimension;
    this.cells = new Cell[dimension*dimension];
    createCells(imageFile);
    initPuzzle();
    shakePuzzle();
  }

  private void initPuzzle() {
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
  }

  private void shakePuzzle(){
    try {
      Movement[] moves = Movement.values();
      Movement lastMove = null;
      Random rand = new Random();
      // Nº of movements
      int N_MOVES = rand.nextInt(10, 101) * dimension;
      System.out.println("N_MOVES : "+N_MOVES);

      while(N_MOVES > 0){
        int movIndex = rand.nextInt(0, moves.length);
        while(moves[movIndex].opposite() == lastMove || !isValidMove(moves[movIndex])){
          movIndex = rand.nextInt(0, moves.length);
        }

        move(moves[movIndex]); //throws Exception
        lastMove = moves[movIndex];
        N_MOVES--;
      }
    } catch (Exception e) {
      //Not accessible, we check if valid move before move
      System.out.println("EXCEPTIOOOOOON");
    }
  }

  private int[] getMoveValues(Movement move){
    int movX = 0;
    int movY = 0;

    switch(move){
      //blanc per avall
      case TOP -> {
        movY++;
      }
      //blanc per amunt
      case BOTTOM -> {
        movY--;
      }
      //blanc dreta
      case LEFT -> {
        movX++;
      }
      //blanc esquerra
      case RIGHT -> {
        movX--;
      }
    }

    return new int[]{movX, movY};
  }

  public boolean isValidMove(Movement move){
    switch(move){
      //blanc per avall
      case TOP -> {
        if(emptyY + 1 >= dimension) return false;
      }
      //blanc per amunt
      case BOTTOM -> {
        if(emptyY - 1 < 0) return false;
      }
      //blanc dreta
      case LEFT -> {
        if(emptyX + 1 >= dimension) return false;
      }
      //blanc esquerra
      case RIGHT -> {
        if(emptyX - 1 < 0) return false;
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

    return this.table;
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

  public int[][] getTable(){
    return this.table;
  }
}
