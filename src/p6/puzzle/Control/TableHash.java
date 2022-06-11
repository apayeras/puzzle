package p6.puzzle.Control;

public class TableHash {

  private final static String aux = "$";

  public static String getHash(int[][] table){
    String s = "";
    for(int i = 0; i < table.length;i++){
      for(int j = 0; j < table.length;j++){
        s += "#"+table[i][j]+"#";
      }
    }

    return s;
  }

  public static String updateHash(String prevHash, int emptyId, int movedId){
    String empty = "#"+ emptyId + "#";
    String moved = "#" + movedId + "#";

    return prevHash.replace(empty, aux).replace(moved, empty).replace(aux, moved);
  }
}
