package view;

import model.EstacaoReserva;
import model.Instrucao;
import model.Registrador;
import model.UnidadeFuncional;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class TomasuloView {
    public static void imprimirEstado(int ciclo, List<EstacaoReserva> estacoesReserva, List<UnidadeFuncional> unidadesFuncionais, List<Registrador> registradores) {
        System.out.println("\nCiclo: " + ciclo);

        System.out.println("\nEstações de Reserva:");
        System.out.printf("%-15s %-10s %-10s %-10s %-10s %-10s %-10s%n", "Nome", "Ocupado", "Operação", "Vj", "Vk", "Qj", "Qk");
        for (EstacaoReserva er : estacoesReserva) {
            System.out.printf("%-15s %-10b %-10s %-10s %-10s %-10s %-10s%n", er.getNome(), er.isOcupado(), er.getOperacao(), er.getVj(), er.getVk(), er.getQj(), er.getQk());
        }

        System.out.println("\nUnidades Funcionais:");
        System.out.printf("%-15s %-10s %-10s%n", "Nome", "Ocupado", "Tempo Restante");
        for (UnidadeFuncional uf : unidadesFuncionais) {
            System.out.printf("%-15s %-10b %-10d%n", uf.getNome(), uf.isOcupado(), uf.getTempoRestante());
        }

        System.out.println("\nRegistradores:");
        System.out.printf("%-10s %-10s %-10s %n", "Nome", "Valor", "Qi");
        for (Registrador reg : registradores) {
            System.out.printf("%-10s %-10s %-10s%n", reg.getNome(), reg.getValor(), reg.getQi());
        }
    }

    public static Queue<Instrucao> input() {
        return InputHandler.input();
    }
}
