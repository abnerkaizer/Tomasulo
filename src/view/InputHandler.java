package view;

import model.Instrucao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class InputHandler {
    public static Queue<Instrucao> input() {
        Queue<Instrucao> instrucoes = new ArrayDeque<>();
        System.out.println("Instruções aceitas: ADD.D, SUB.D, MUL.D, DIV.D, LOAD.D, STORE.D");

        String arquivo = "/home/abner/Projects/Tomasulo/src/instrucoes.txt"; // Substitua pelo caminho do seu arquivo de instruções

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length == 4) {
                    String tipo = partes[0];
                    String destino = partes[1];
                    String operando1 = partes[2];
                    String operando2 = partes[3];

                    Instrucao instrucao = new Instrucao(tipo, destino, operando1, operando2);
                    //System.out.println(instrucao.toString());
                    instrucoes.add(instrucao);
                } else if (partes.length==3) {
                    String tipo = partes[0];
                    String destino = partes[1];
                    String operando1 = partes[2];
                    Instrucao instrucao = new Instrucao(tipo, destino, operando1,operando1);
                    instrucoes.add(instrucao);
                } else {
                    System.err.println("Instrução inválida: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return instrucoes;
    }
}

