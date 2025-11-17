package br.edu.utfpr.pauloandre7.Distribuido;

import java.io.*;
import java.net.*;
import br.edu.utfpr.pauloandre7.*;

public class Cliente {

    public static void main(String[] args) throws Exception {
        
        System.out.println("Conectando ao servidor em localhost:6789...");
        Socket socket = new Socket("localhost", 6789);
        System.out.println("Conectado!");

        // Configura streams
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        // Recebe o trabalho do servidor
        System.out.println("Aguardando trabalho do servidor...");
        double[][] A_part = (double[][]) ois.readObject(); // Recebe a parte de A
        double[][] B_full = (double[][]) ois.readObject(); // Recebe B inteiro
        
        System.out.println("Trabalho recebido. Iniciando cálculo...");

        // Faz o calculo usando a classe util
        double[][] C_part = Util.multiplicar(A_part, B_full);
        
        System.out.println("Cálculo terminado. Enviando resultado de volta...");

        // Envia o resultado de volta
        oos.writeObject(C_part);
        oos.flush();

        System.out.println("Resultado enviado. Fechando cliente.");
        
        // Fechar tudo
        oos.close();
        ois.close();
        socket.close();
    }
}