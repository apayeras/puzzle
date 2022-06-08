package p6.puzzle.Model;

import p6.puzzle.Control.Heuristic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Puzzle implements Comparable<Puzzle> {

    private final int dimension;
    //public final Cell[] cells;
    private final int[][] solved;
    private int[][] table;
    private int emptyX;
    private int emptyY;
    private int emptyId;
    private Movement lastMove;
    public final Puzzle prev;
    private double cost;
    private final int level;
    private Heuristic heuristic;


    public Puzzle(Puzzle puzzle, Movement move){
        this.dimension = puzzle.dimension;
        //this.cells = puzzle.cells;
        this.emptyId = puzzle.emptyId;
        this.emptyX = puzzle.emptyX;
        this.emptyY = puzzle.emptyY;
        this.solved = puzzle.solved;
        this.table = cloneMatrix(puzzle.table);
        this.lastMove = puzzle.lastMove;
        this.prev = puzzle;
        this.heuristic = puzzle.heuristic;
        this.level = puzzle.level+ 1;
        move(move);
        calcCost();
    }

    public Puzzle(int dimension, Heuristic heuristic) {
        this.level = 0;
        this.prev = null;
        this.dimension = dimension;
        //this.cells = new Cell[dimension*dimension];
        this.solved = initPuzzle();
        this.heuristic = heuristic;
        //createCells(imageFile);
        shakePuzzle();
        calcCost();
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
        Movement[] moves = Movement.values();
        Movement lastMove = null;
        Random rand = new Random();
        // Nº of movements
        int N_MOVES = rand.nextInt(10, 50) * dimension/3;

        while(N_MOVES > 0){
            int movIndex = rand.nextInt(0, moves.length);
            while(moves[movIndex].opposite() == lastMove || !isValidMove(moves[movIndex])){
                movIndex = rand.nextInt(0, moves.length);
            }

            move(moves[movIndex]);
            lastMove = moves[movIndex];
            N_MOVES--;
        }
        //reset last move on shake
        this.lastMove = null;
    }

    private int wrongPlacedHeuristic(){
        int count = 0;
        int n = this.dimension;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.table[i][j] != this.emptyId && this.table[i][j] != this.solved[i][j]) {
                  count++;
                }
          }
        }
        return count;
    }

    private int[] getDistance(int id, int x, int y){
      int n = this.dimension;
      int solvedPosX = id % n;
      int solvedPosY = id / n;

      int diffX = Math.abs(solvedPosX - x);
      int diffY = Math.abs(solvedPosY - y);

      return new int[]{diffX, diffY};
    }

    private int manhattanHeuristic(){
      int count = 0;
      int n = this.dimension;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          int id = this.table[i][j];
          if (id != this.emptyId && id != this.solved[i][j]) {
            int[] distance = getDistance(id, i, j);
            int distX = distance[0];
            int distY = distance[1];

            count += distX + distY;
          }
        }
      }
      return count;
    }

    private int euclideanHeuristic(){
      int count = 0;
      int n = this.dimension;
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          int id = this.table[i][j];
          if (id != this.emptyId && id != this.solved[i][j]) {

            int[] distance = getDistance(id, i, j);
            int distX = distance[0];
            int distY = distance[1];

            double euclideanDistance = Math.sqrt((distX * distX) + (distY * distY));
            count += euclideanDistance;
          }
        }
      }
      return count;
  }

    private void calcCost() {
      switch(this.heuristic){
        case WRONG_PLACED -> {
          cost = wrongPlacedHeuristic();
        }
        case MANHATTAN -> {
          cost = manhattanHeuristic();
        }
        case EUCLIDEAN -> {
          cost = euclideanHeuristic();
        }
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

    public void move(Movement move){
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
    }

    public Movement getLastMove(){
      return this.lastMove;
    }

    private static int[][] cloneMatrix(int[][] matrix){
      return Arrays.stream(matrix).map(int[]::clone).toArray(int[][]::new);
    }

    public int[][] getTable() {
      return this.table;
    }

    public double cost(){
      return this.cost;
    }

    public int level(){
      return this.level;
    }

    @Override
    public int compareTo(Puzzle o) {
      double diff = (this.cost + this.level) - (o.cost + o.level);
      if(diff < 0){
        return -1;
      }
      if(diff > 0){
        return 1;
      }
      return 0;
    }

    public void setHeuristic(Heuristic heu){
        this.heuristic = heu;
    }
}
