package br.edu.utfpr.pauloandre7;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Sequencial {

    public static void main(String[] args) throws Exception {
        int N = 500; // Tamanho da matriz
        
        System.out.println("Criando matrizes...");
        double[][] A = Util.criarMatriz(N, N);
        double[][] B = Util.criarMatriz(N, N);

        System.out.println("Iniciando multiplicação SEQUENCIAL...");
        
        long startTime = System.nanoTime(); // Começa a contar o tempo
        
        double[][] C = Util.multiplicar(A, B); // Executa
        
        long endTime = System.nanoTime(); // Para de contar o tempo
        
        long duracaoNs = endTime - startTime;
        double duracaoMs = duracaoNs / 1_000_000.0; // Converte para milissegundos

        System.out.printf("Tempo Sequencial: %.2f ms\n", duracaoMs);

        // Já cria logo um arquivo de texto para facilitar a vida (Sobrescreve sempre)
        try (PrintWriter out = new PrintWriter(new FileWriter("tempoAlgoritmos.txt"))) {
            out.printf("Tempo Sequencial: %.2f ms\n", duracaoMs);
        }
        Util.imprimirMatriz(A);

        // System.out.println("Matriz Resultante C (primeiros 5x5):");
        // Util.imprimirMatriz(C); // Não descomente isso com N=1000
    }
}