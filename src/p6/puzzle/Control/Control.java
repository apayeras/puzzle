/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p6.puzzle.Control;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import p6.puzzle.Model.Model;

/**
 *
 * @author soyjo
 */
public class Control {
    // Obligatorio 3x3
    // Pro -> para NxN
    public int dimension = 3;

    // Abajo, izquierda, arriba, derecha
    int[] fila = {1, 0, -1, 0};
    int[] columna = {0, -1, 0, 1};

    // Calculamos el coste del movimiento planteado (es el arbol de 
    // de posibilidades como tal)
    public int calculaCoste(int[][] inicial, int[][] objetivo) {
        int count = 0;
        int n = inicial.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (inicial[i][j] != 0 && inicial[i][j] != objetivo[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Muestra por la consola la situación actual
    public void muestraTablero(int[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    // No salimos del tablero a la hora de probar el B&B
    public boolean esSeguro(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }
    
    // Mostramos por consola la solución a la que se ha llegado
    public void muestraCamino(Model root) {
        if (root == null) {
            return;
        }
        muestraCamino(root.nodo);
        muestraTablero(root.tablero);
        System.out.println();
    }

    // Metodo que comprueba si la situación inicial tiene solución o no
    // Podemos aprovecharlo porque es más chulo para el usuario
    public boolean tieneSolucion(int[][] tablero) {
        int count = 0;
        List<Integer> array = new ArrayList<Integer>();

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                array.add(tablero[i][j]);
            }
        }

        Integer[] anotherArray = new Integer[array.size()];
        array.toArray(anotherArray);

        for (int i = 0; i < anotherArray.length - 1; i++) {
            for (int j = i + 1; j < anotherArray.length; j++) {
                if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
                    count++;
                }
            }
        }

        return count % 2 == 0;
    }

    // B&B -> Hacemos uso de una cola de prioridad
    public void resuelve(int[][] initial, int[][] goal, int x, int y) {
        PriorityQueue<Model> pq = new PriorityQueue<Model>(1000, (a, b) -> (a.coste + a.movimientos) - (b.coste + b.movimientos));
        Model root = new Model(initial, x, y, x, y, 0, null);
        root.coste = calculaCoste(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Model min = pq.poll();
            if (min.coste == 0) {
                muestraCamino(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (esSeguro(min.x + fila[i], min.y + columna[i])) {
                    Model child = new Model(min.tablero, min.x, min.y, min.x + fila[i], min.y + columna[i], min.movimientos + 1, min);
                    child.coste = calculaCoste(child.tablero, goal);
                    pq.add(child);
                }
            }
        }
    }
}
