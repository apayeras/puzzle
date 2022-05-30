package p6.puzzle;


import p6.puzzle.Control.Control;

/**
 * @author Antoni
 */
public class P6Puzzle {


    public static void main(String[] args) {
        // Situacion inicial
        int[][] inicial = {{1, 8, 2}, {0, 4, 3}, {7, 6, 5}};
        // Situacion objetivo
        int[][] objetivo = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        // Coordenadas iniciales de la casilla en blanco
        int x = 1, y = 0;

        Control puzzle = new Control();
        // Comprobamos si tiene solución
        if (puzzle.tieneSolucion(inicial)) {
            // Resolvemos
            puzzle.resuelve(inicial, objetivo, x, y);
        } else {
            System.out.println("La situación inicial dada es imopsible de resolver");
        }
    }

}
