package br.edu.utfpr.pauloandre7;

import java.util.Random;

public class Util {

    // Cria uma matriz com valores aleatórios
    public static double[][] criarMatriz(int linhas, int colunas) {
        double[][] matriz = new double[linhas][colunas];
        Random rand = new Random();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = rand.nextDouble() * 10.0; // Valores entre 0 e 10
            }
        }
        return matriz;
    }

    
    public static void imprimirMatriz(double[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                System.out.printf("%.2f \t", matriz[i][j]);
            }
            System.out.println();
        }
    }

    // O algoritmo de multiplicação sequencial (vou usar pra todas os tipos de codigo)
    public static double[][] multiplicar(double[][] A, double[][] B) {
        int linhasA = A.length;
        int colunasA = A[0].length;
        int colunasB = B[0].length;

        double[][] C = new double[linhasA][colunasB];

        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                double soma = 0.0;
                for (int k = 0; k < colunasA; k++) {
                    soma += A[i][k] * B[k][j];
                }
                C[i][j] = soma;
            }
        }
        return C;
    }
}
