package Main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		
		List<Problem> listaDeProblemas = new ArrayList<>();
		/*
		int index = 0;
        
     
        int nProblem = Integer.parseInt(args[index++]);

        
       
        for (int i = 0; i < nProblem; i++) {
        
            
            int filas = Integer.parseInt(args[index++]); 
            int columnas = Integer.parseInt(args[index++]); 

            
            int[][] matriz = new int[filas][columnas];

            
            for (int r = 0; r < filas; r++) {
                for (int c = 0; c < columnas; c++) {
                    matriz[r][c] = Integer.parseInt(args[index++]);
                }
            }

            Problem problema = new Problem(matriz);
            
            listaDeProblemas.add(problema);
          
        }
        
        */
		
		int[][] matriz = {
	            {0, 2, 0},
	            {-1, -1, -1},
	            {0, 0, 13},
	            {0, 0, 80}, // Esta es la fila de la mitad (sin -1)
	            {-1, 22, 300},
	            {26, 27, -1},
	            {31, 0, 33}
	     };
		
		
		
        
        listaDeProblemas.add(new Problem(matriz));
        
        
		
		
        for(Problem problema : listaDeProblemas) {
        	
        	int maximoReliquias = problema.hallarMaximoReliquias();
        	System.out.println("El maximo de reliquias es: "+maximoReliquias);
        	
        }
    }
	
}
