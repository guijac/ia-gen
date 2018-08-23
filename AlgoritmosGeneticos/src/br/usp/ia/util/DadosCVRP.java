package br.usp.ia.util;

/*
 * 
 * Classe que representa os dados das rotas e demandas que o problema ira trabalhar.
 * A classe "CVRPDataReader" exibe os dados no formato adequado para ser inserido na 
 * matriz de posicoes das rotas e array de de demandas de clientes. 
 * 
 */

public class DadosCVRP {
	
	public final static int DIMENSION = 101;

	public final static int CAPACITY = 400;

	final static int[][] position =
		{
				{35, 35}, {41, 49}, {35, 17}, {55, 45}, {55, 20}, {15, 30}, {25, 30}, {20, 50}, {10, 43}, {55, 60}, {30, 60}, {20, 65}, {50, 35}, {30, 25}, {15, 10}, {30, 5}, {10, 20}, {5, 30}, {20, 40}, {15, 60}, {45, 65}, {45, 20}, {45, 10}, {55, 5}, {65, 35}, {65, 20}, {45, 30}, {35, 40}, {41, 37}, {64, 42}, {40, 60}, {31, 52}, {35, 69}, {53, 52}, {65, 55}, {63, 65}, {2, 60}, {20, 20}, {5, 5}, {60, 12}, {40, 25}, {42, 7}, {24, 12}, {23, 3}, {11, 14}, {6, 38}, {2, 48}, {8, 56}, {13, 52}, {6, 68}, {47, 47}, {49, 58}, {27, 43}, {37, 31}, {57, 29}, {63, 23}, {53, 12}, {32, 12}, {36, 26}, {21, 24}, {17, 34}, {12, 24}, {24, 58}, {27, 69}, {15, 77}, {62, 77}, {49, 73}, {67, 5}, {56, 39}, {37, 47}, {37, 56}, {57, 68}, {47, 16}, {44, 17}, {46, 13}, {49, 11}, {49, 42}, {53, 43}, {61, 52}, {57, 48}, {56, 37}, {55, 54}, {15, 47}, {14, 37}, {11, 31}, {16, 22}, {4, 18}, {28, 18}, {26, 52}, {26, 35}, {31, 67}, {15, 19}, {22, 22}, {18, 24}, {26, 27}, {25, 24}, {22, 27}, {25, 21}, {19, 21}, {20, 26}, {18, 18}, 
 
		};

	public final static int[] demand =
		{
			0, 10, 7, 13, 19, 26, 3, 5, 9, 16, 16, 12, 19, 23, 20, 8, 19, 2, 12, 17, 9, 11, 18, 29, 3, 6, 17, 16, 16, 9, 21, 27, 23, 11, 14, 8, 5, 8, 16, 31, 9, 5, 5, 7, 18, 16, 1, 27, 36, 30, 13, 10, 9, 14, 18, 2, 6, 7, 18, 28, 3, 13, 19, 10, 9, 20, 25, 25, 36, 6, 5, 15, 25, 9, 8, 18, 13, 14, 3, 23, 6, 26, 16, 11, 7, 41, 35, 26, 9, 15, 3, 1, 2, 22, 27, 20, 11, 12, 10, 9, 17, 		
		};
			

	/* Euclidean Distance Matrix */
	public final static double[][] EDM = getEuclideanDistanceMatrix();

	static double getEuclideanDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
	}

	static double[][] getEuclideanDistanceMatrix() {
		double[][] matrix = new double[CAPACITY][CAPACITY];
		for(int i=0; i<position.length; i++) {
			for(int j=0; j<position.length; j++) {
				if(i==j) {
					matrix[i][j] = 0;
				} else {
					matrix[i][j] = getEuclideanDistance(position[i][0], position[i][1], position[j][0], position[j][1]);
				}
			}
		}
		return matrix;
	}
}