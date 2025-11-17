package br.edu.utfpr.pauloandre7.Distribuido;

import java.io.*;
import java.net.*;
import br.edu.utfpr.pauloandre7.*;

public class Servidor {

    private static int N = 500; // Tamanho da matriz
    private static double[][] A;
    private static double[][] B;
    private static double[][] C;

    public static void main(String[] args) throws Exception {
        System.out.println("Criando matrizes...");
        A = Util.criarMatriz(N, N);
        B = Util.criarMatriz(N, N);
        C = new double[N][N]; // Matriz C para ser preenchida

        System.out.println("Iniciando SERVIDOR. Aguardando cliente na porta 6789...");
        ServerSocket serverSocket = new ServerSocket(6789);
        Socket clientSocket = serverSocket.accept(); // Trava até o cliente conectar
        
        System.out.println("Cliente conectado!");
        
        // Configura streams de objetos para enviar/receber matrizes
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

        // inicia a contagem do tempo
        long startTime = System.nanoTime();

        // Divide o trabalho
        int split = N / 2;
        
        // Cria a sub-matriz A para o cliente (metade de baixo)
        double[][] A_cliente = new double[N - split][N];
        for (int i = split; i < N; i++) {
            System.arraycopy(A[i], 0, A_cliente[i - split], 0, N);
        }

        // Envia o trabalho para o cliente
        System.out.println("Enviando trabalho para o cliente...");
        oos.writeObject(A_cliente); // Envia a parte de A
        oos.writeObject(B); // Envia B inteiro
        oos.flush();

        // Servidor faz a parte dele
        System.out.println("Servidor calculando sua metade...");
        int colunasB = B[0].length;
        int colunasA = A[0].length;
        for (int i = 0; i < split; i++) { // Servidor faz de 0 até 'split'
            for (int j = 0; j < colunasB; j++) {
                double soma = 0.0;
                for (int k = 0; k < colunasA; k++) {
                    soma += A[i][k] * B[k][j];
                }
                C[i][j] = soma; // Preenche a primeira metade de C
            }
        }
        System.out.println("Servidor terminou sua metade.");

        // Espera e recebe o resultado do cliente
        System.out.println("Aguardando resultado do cliente...");
        double[][] C_cliente = (double[][]) ois.readObject();
        System.out.println("Cliente enviou resultado!");

        // Monta a matriz C final
        for (int i = 0; i < C_cliente.length; i++) {
            System.arraycopy(C_cliente[i], 0, C[i + split], 0, N);
        }
        
        // finaliza a contagem do tempo
        long endTime = System.nanoTime();
        long duracaoNs = endTime - startTime;
        double duracaoMs = duracaoNs / 1_000_000.0;
        
        System.out.printf("Tempo Distribuído (Servidor + 1 Cliente): %.2f ms\n", duracaoMs);

        // Adiciona no arquivo
        try (PrintWriter out = new PrintWriter(new FileWriter("tempoAlgoritmos.txt", true))) {
            out.printf("Tempo Distribuído (Servidor + 1 Cliente): %.2f ms\n", duracaoMs);
        }

        // Fecha tudo
        ois.close();
        oos.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Servidor finalizado.");
    }
}