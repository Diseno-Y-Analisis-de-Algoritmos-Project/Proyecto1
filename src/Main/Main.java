package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Leer el número de casos de prueba
        int nProblem = sc.nextInt();
        List<Problem> listaDeProblemas = new ArrayList<>();

        // Leer cada caso de prueba
        for (int i = 0; i < nProblem; i++) {
            // Leer las dimensiones de la matriz
            int filas = sc.nextInt();
            int columnas = sc.nextInt();

            // Inicializar la matriz
            int[][] matriz = new int[filas][columnas];

            // Llenar la matriz con los datos de entrada
            for (int r = 0; r < filas; r++) {
                for (int c = 0; c < columnas; c++) {
                    matriz[r][c] = sc.nextInt();
                }
            }

            // Crear el problema con la matriz leída
            Problem problema = new Problem(matriz);
            listaDeProblemas.add(problema);
        }

        // Resolver cada caso de prueba y mostrar el resultado
        for (Problem problema : listaDeProblemas) {
            int maximoReliquias = problema.hallarMaximoReliquias();
            System.out.println(maximoReliquias);
        }

        sc.close();
    }
}
