package Main;

public class Problem {

	private int[][] matrizDeTerreno;
	
	private boolean[][] matrizVisita;
	
	Problem(int[][] matrizDeTerreno){
		this.matrizDeTerreno = matrizDeTerreno;
		matrizVisita = new boolean[matrizDeTerreno.length][matrizDeTerreno[0].length];
	}

	public int hallarMaximoReliquias(){
		
		int[][] matrizMax = new int[matrizDeTerreno.length][matrizDeTerreno[0].length];
		
	    int mitad = matrizDeTerreno.length/2;
		
        CasoIndiana(matrizMax, mitad);
		
        int CasoIndiana = encontrarCaminoMaximo(matrizMax,mitad);
		
		actualizarMatrizDeTerreno();

		
		
		matrizMax = new int[matrizDeTerreno.length][matrizDeTerreno[0].length];
        int CasoMarion = CasoMarion(matrizMax, mitad);


		matrizMax = new int[matrizDeTerreno.length][matrizDeTerreno[0].length];
        int CasoSallah = CasoSallah(matrizMax ,mitad);
		
		//System.out.println(CasoIndiana);
		//System.out.println(CasoMarion);
		//System.out.println(CasoSallah);
		if(CasoIndiana + CasoMarion + CasoSallah <= -3){
			return -1;
		}
		
		if(CasoIndiana == -1){
			CasoIndiana = 0;
		}
		
		if(CasoMarion == -1){
			CasoMarion = 0;
		}

		if(CasoSallah == -1){
			CasoSallah = 0;
		}

		return CasoIndiana + CasoMarion + CasoSallah;
	}
	
	// Encontrar el numero de reliquias maxima que pueda llegar cada jugador
	private int hallarMayorReliquias(int[][] matrizMax){
		int mitad = matrizDeTerreno.length/2;
        int mayor = 0;
		int subMayor = 0;
		for ( int i = 0; i< matrizMax[mitad].length;i++){
            if(matrizMax[mitad][i] > mayor ){
				mayor = matrizMax[mitad][i];
				subMayor = i;
			}
		}
		

        if (mayor != 0 || subMayor !=0 ){
            matrizDeTerreno[mitad][subMayor] = 0;
			return mayor;
		}

		return -1;
	}
	
	
   //Ejecucion de retroceso del camino de Indiana y actualizacion de el terreno
	private void actualizarMatrizDeTerreno() {
		for(int i = 0; i<matrizVisita.length;i++){
			for(int j = 0; j<matrizVisita[0].length;j++){
			    if(matrizVisita[i][j] == true){
                   matrizDeTerreno[i][j] = 0;
				}
			}
		}
		
	}

	private int encontrarCaminoMaximo(int[][] matrizMax, int mitad) {
		int maxColumna = 0;
		int maxValor = Integer.MIN_VALUE;
	
		// Encontrar la columna con el valor máximo en la fila de la mitad
		for (int j = 0; j < matrizMax[mitad].length; j++) {
			if (matrizMax[mitad][j] > maxValor) {
				maxValor = matrizMax[mitad][j];
				maxColumna = j;
			}
		}
	
		// Al llegar al final, la suma de reliquias debe coincidir con el valor máximo alcanzado
		int reliquiasRestantes = maxValor;
		int[] caminoColumnas = new int[mitad + 1];  // Para guardar las columnas del camino de Indiana
	
		// Restar el valor de la casilla donde llegó Indiana en la fila de la mitad
		reliquiasRestantes = reliquiasRestantes - matrizDeTerreno[mitad][maxColumna];
		//System.out.println(matrizDeTerreno[mitad][maxColumna]);
		//System.out.println(reliquiasRestantes);
		// Reconstruir el camino desde la fila de la mitad hacia arriba
		for (int i = mitad; i >= 0; i--) {
			// Guardamos la columna del camino en la fila actual
			caminoColumnas[i] = maxColumna;
	
			// Restamos el valor de las reliquias en la celda actual (excepto en la primera iteración que ya restamos)
			if (i != mitad) {
				reliquiasRestantes -= matrizDeTerreno[i][maxColumna];
			}
	
			// Ahora debemos encontrar de dónde vino el máximo en la fila anterior
			if (i > 0) {
				int mejorColumna = maxColumna; // Inicialmente asumimos que vino de la misma columna
				
				// Verificamos si vino de la diagonal izquierda
				if (maxColumna > 0 && matrizMax[i-1][maxColumna-1] != Integer.MIN_VALUE) {
					mejorColumna = maxColumna - 1;
				}
	
				// Verificamos si vino de la diagonal derecha
				if (maxColumna < matrizMax[0].length - 1 && matrizMax[i-1][maxColumna+1] != Integer.MIN_VALUE
						&& matrizMax[i-1][maxColumna+1] > matrizMax[i-1][mejorColumna]) {
					mejorColumna = maxColumna + 1;
				}
	
				// Verificamos si la misma columna es mejor
				if (matrizMax[i-1][maxColumna] > matrizMax[i-1][mejorColumna]) {
					mejorColumna = maxColumna;
				}
	
				// Actualizamos la columna para la siguiente iteración
				maxColumna = mejorColumna;
			}
		}
	
		// Verificación: Al llegar a (0, 0), las reliquias restantes deberían ser 0
		if (reliquiasRestantes != 0) {
			//System.out.println("Error en la reconstrucción del camino de Indiana, reliquias restantes: " + reliquiasRestantes);
		} else {
			// Ahora que sabemos que el camino es correcto, marcamos las celdas visitadas
			for (int i = 0; i <= mitad; i++) {
				matrizVisita[i][caminoColumnas[i]] = true;  // Marcamos la celda como visitada
				matrizDeTerreno[i][caminoColumnas[i]] = 0;  // Actualizamos el terreno a 0
			}
		}
	
		// Mostrar la matriz de visitas para verificar el camino
		for (int i = 0; i < matrizVisita.length; i++) {
			for (int j = 0; j < matrizVisita[i].length; j++) {
				//System.out.print(matrizVisita[i][j] ? "T " : "F ");
			}
			//System.out.println();
		}

		return hallarMayorReliquias(matrizMax);
	}
	
	

	//Ejecucion de cada uno de los Jugadores (Indiana, Marion y Sallah)
	private int CasoSallah(int[][] matrizMax, int mitad) {
		int p = matrizDeTerreno[0].length / 2; 
		
		
		for (int i = matrizDeTerreno.length - 1; i >= mitad; i--) {
			int n = matrizDeTerreno.length - 1 - i; 
	
			int inicio = Math.max(0, p - n); 
			int fin = Math.min(matrizDeTerreno[0].length - 1, p + n); 
	
			for (int j = inicio; j <= fin; j++) {
				

				if (matrizDeTerreno[i][j] == -1) {
					matrizMax[i][j] = Integer.MIN_VALUE; 
				}
				else if (i == matrizDeTerreno.length - 1 && j == p) {
					matrizMax[i][j] = 0; 
				}
				else if (i==matrizDeTerreno.length-2) {
					matrizMax[i][j] = matrizDeTerreno[i][j];
				}
				else if (j == p - n) {
					matrizMax[i][j] = matrizMax[i+1][j+1] + matrizDeTerreno[i][j];
				}
				else if (j == p + n){
					matrizMax[i][j] = matrizMax[i+1][j-1] + matrizDeTerreno[i][j];
				}
				else if (j==0 && j != p - n){
					matrizMax[i][j] = Math.max(matrizMax[i+1][j+1] + matrizDeTerreno[i][j], matrizMax[i+1][j] + matrizDeTerreno[i][j]);
				}
				else if (j==matrizDeTerreno[0].length-1 && j != p + n){ 
					matrizMax[i][j] = Math.max(matrizMax[i+1][j-1] + matrizDeTerreno[i][j], matrizMax[i+1][j] + matrizDeTerreno[i][j]);

				}
				else{
					
					matrizMax[i][j] = Math.max(Math.max(matrizMax[i+1][j-1] + matrizDeTerreno[i][j], matrizMax[i+1][j] + matrizDeTerreno[i][j]), matrizMax[i+1][j+1] + matrizDeTerreno[i][j]);
				}

			}
		}
	
		// Mostrar la matriz máxima para Sallah
		//System.out.println("Matriz Maxima para Sallah:");
		for (int i = 0; i < matrizMax.length; i++) {
			for (int j = 0; j < matrizMax[i].length; j++) {
				//System.out.print(matrizMax[i][j] + " ");
			}
			//System.out.println();
		}
	
		return hallarMayorReliquias(matrizMax);
	}
	
	private int CasoMarion(int[][] matrizMax, int mitad) {
		int p = matrizDeTerreno[0].length - 1; // Marion empieza en la esquina superior derecha
		
		
		// Recorremos la matriz solo en las columnas a la derecha (donde Marion se mueve)
		for (int i = 0; i < mitad + 1; i++) {
			for (int j = matrizDeTerreno[0].length - 1; j >= p; j--) {

				if (matrizDeTerreno[i][j] == -1) {
					matrizMax[i][j] = Integer.MIN_VALUE; // Si es una celda maldita
				}
				else if(i==0 && j==matrizDeTerreno[0].length-1) {
        			matrizMax[i][j] = 0;
        			
        		}
				else if(matrizDeTerreno[0].length-1 == i+j) {
        			matrizMax[i][j] = matrizMax[i-1][j+1] + matrizDeTerreno[i][j];
        			
        		}else if(i==1 && j==matrizDeTerreno[0].length-1) {
					matrizMax[i][j] = matrizMax[i-1][j] + matrizDeTerreno[i][j];

        		}else if(i>1 && j==matrizDeTerreno[0].length-1) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j-1] + matrizDeTerreno[i][j], matrizMax[i-1][j] + matrizDeTerreno[i][j]);
        		
        		}else if(i>1 && j==0) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j] + matrizDeTerreno[i][j], matrizMax[i-1][j+1] + matrizDeTerreno[i][j]);
        		
        		}else if(i+j == matrizDeTerreno[0].length) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j] + matrizDeTerreno[i][j], matrizMax[i-1][j+1] + matrizDeTerreno[i][j]);
        		
        		}else if(i>=1 && j>=1) {
        			matrizMax[i][j] = Math.max(Math.max(matrizMax[i-1][j+1] + matrizDeTerreno[i][j], matrizMax[i-1][j] + matrizDeTerreno[i][j]), matrizMax[i-1][j-1] + matrizDeTerreno[i][j]);
        		}
			}
			// Nos aseguramos de movernos solo en las columnas que corresponden a Marion
			if (p > 0) {
				p--; // Reducimos el límite izquierdo de Marion
				}
		}
		
		
	
		// Mostrar la matriz máxima
		//System.out.println("Matriz Maxima para Marion:");
		for (int i = 0; i < matrizMax.length; i++) {
			for (int j = 0; j < matrizMax[i].length; j++) {
				//System.out.print(matrizMax[i][j] + " ");
			}
			//System.out.println();
		}
		
		return hallarMayorReliquias(matrizMax);
	}

	private int CasoIndiana(int[][] matrizMax, int mitad) {
        int p=0;
		
        for(int i=0;i<mitad+1;i++) {
        	for(int j=0;j<p+1;j++) {
        		
        		if(matrizDeTerreno[i][j] == -1) {
        			matrizMax[i][j] = Integer.MIN_VALUE; 
        			
        		}else if(i==0 && j==0) {
        			matrizMax[i][j] = 0;
        			
        		}else if(i==1 && j==0) {
        			matrizMax[i][j] = matrizMax[i-1][j] + matrizDeTerreno[i][j];
        			
        		}else if(i==j) {
        			matrizMax[i][j] = matrizMax[i-1][j-1] + matrizDeTerreno[i][j];
        			
        		}else if(i>1 && j==0) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j] + matrizDeTerreno[i][j], matrizMax[i-1][j+1] + matrizDeTerreno[i][j]);
        		
        		}else if(i >0 && j == matrizMax.length-1) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j] + matrizDeTerreno[i][j], matrizMax[i-1][j-1] + matrizDeTerreno[i][j]);
        		
        		}else if(i == j+1) {
        			matrizMax[i][j] = Math.max(matrizMax[i-1][j] + matrizDeTerreno[i][j], matrizMax[i-1][j-1] + matrizDeTerreno[i][j]);
        		
        		}else if(i>=1 && j>=1) {
        			matrizMax[i][j] = Math.max(Math.max(matrizMax[i-1][j+1] + matrizDeTerreno[i][j], matrizMax[i-1][j] + matrizDeTerreno[i][j]), matrizMax[i-1][j-1] + matrizDeTerreno[i][j]);
        		}   		
        	}
        	
        	if(p<matrizMax[0].length-1) {
        	  p++;
        	}
        }
        
        for (int i = 0; i < matrizMax.length; i++) {  // Recorre cada fila
            for (int j = 0; j < matrizMax[i].length; j++) {  // Recorre cada columna de la fila actual
                //System.out.print(matrizMax[i][j] + " ");  // Imprime el elemento seguido de un espacio
            }
            //System.out.println();  // Salto de línea después de imprimir cada fila
        }

		return p;


	}
		 
}