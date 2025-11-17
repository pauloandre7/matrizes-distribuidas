package br.edu.utfpr.pauloandre7;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Paralelo {

    private static int N = 500; // Tamanho da matriz
    private static double[][] A;
    private static double[][] B;
    private static double[][] C;

    // A classe do Thread que faz o trabalho
    static class Worker extends Thread {
        private int startRow, endRow;

        public Worker(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public void run() {
            int colunasB = B[0].length;
            int colunasA = A[0].length;

            for (int i = startRow; i < endRow; i++) { // Este thread só faz suas próprias linhas
                for (int j = 0; j < colunasB; j++) {
                    double soma = 0.0;
                    for (int k = 0; k < colunasA; k++) {
                        soma += A[i][k] * B[k][j];
                    }
                    C[i][j] = soma;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Criando matrizes...");
        A = Util.criarMatriz(N, N);
        B = Util.criarMatriz(N, N);
        C = new double[N][N]; // Matriz C para ser preenchida

        int numThreads = 2; // Vamos dividir em 2 threads
        int split = N / numThreads;

        Worker t1 = new Worker(0, split); // Thread 1 faz a primeira metade
        Worker t2 = new Worker(split, N); // Thread 2 faz a segunda metade
        
        System.out.println("Iniciando multiplicação PARALELA...");
        long startTime = System.nanoTime();

        // Inicia os threads
        t1.start();
        t2.start();

        // Espera os dois terminarem
        t1.join();
        t2.join();

        long endTime = System.nanoTime();
        long duracaoNs = endTime - startTime;
        double duracaoMs = duracaoNs / 1_000_000.0;

        System.out.printf("Tempo Paralelo (2 Threads): %.2f ms\n", duracaoMs);

        // Adiciona no arquivo (sem sobrescrever)
        try (PrintWriter out = new PrintWriter(new FileWriter("tempoAlgoritmos.txt", true))) {
            out.printf("Tempo Paralelo (2 Threads): %.2f ms\n", duracaoMs);
        }

        // Util.imprimirMatriz(A);
    }
}