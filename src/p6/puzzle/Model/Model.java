/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p6.puzzle.Model;

/**
 *
 * @author soyjo
 */

public class Model {

    // Nodo al que pertenece
    public Model nodo;
    // Matriz de la situacion
    public int[][] tablero;

    // Lugar donde encontramos el "hueco"
    public int x, y;

    // Numero de casillas mal colocadas
    public int coste;

    // Numero total de movimientos
    public int movimientos;

    public Model(int[][] tablero, int x, int y, int newX, int newY, int movimientos, Model padre) {
        this.nodo = padre;
        this.tablero = new int[tablero.length][];
        for (int i = 0; i < tablero.length; i++) {
            this.tablero[i] = tablero[i].clone();
        }

        // Intercambiamos los valores
        this.tablero[x][y] = this.tablero[x][y] + this.tablero[newX][newY];
        this.tablero[newX][newY] = this.tablero[x][y] - this.tablero[newX][newY];
        this.tablero[x][y] = this.tablero[x][y] - this.tablero[newX][newY];

        this.coste = Integer.MAX_VALUE;
        this.movimientos = movimientos;
        this.x = newX;
        this.y = newY;
    }
}
