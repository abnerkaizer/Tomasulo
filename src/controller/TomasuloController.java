package controller;

import model.*;
import view.TomasuloView;

import java.util.*;

public class TomasuloController {
    private static Queue<Instrucao> instrucoes = new ArrayDeque<>();
    private static List<EstacaoReserva> estacoesReserva = new ArrayList<>();
    private static List<UnidadeFuncional> unidadesFuncionais = new ArrayList<>();
    private static List<Registrador> registradores = new ArrayList<>();
    private static Memoria memoria = new Memoria();
    public static void executar(Queue<Instrucao> input) {
        int ciclo = 0;
        instrucoes.addAll(input);
        for (int i = 0; i < 10; i++) {
            registradores.add(new Registrador(("F"+i)));
        }
        registradores.get(1).setValor("1");
        registradores.get(2).setValor("2");
        registradores.get(3).setValor("2");
        estacoesReserva.add(new EstacaoReserva(("ADD.D"),false,""));
        estacoesReserva.add(new EstacaoReserva(("SUB.D"),false,""));
        estacoesReserva.add(new EstacaoReserva(("MUL.D"),false,""));
        estacoesReserva.add(new EstacaoReserva(("DIV.D"),false,""));
        estacoesReserva.add(new EstacaoReserva(("LOAD.D"),false,""));
        estacoesReserva.add(new EstacaoReserva(("STORE.D"),false,""));
        unidadesFuncionais.add(new UnidadeFuncional(("ADD.D"),false,0));
        unidadesFuncionais.add(new UnidadeFuncional(("SUB.D"),false,0));
        unidadesFuncionais.add(new UnidadeFuncional(("MUL.D"),false,0));
        unidadesFuncionais.add(new UnidadeFuncional(("DIV.D"),false,0));
        unidadesFuncionais.add(new UnidadeFuncional(("LOAD.D"),false,0));
        unidadesFuncionais.add(new UnidadeFuncional(("STORE.D"),false,0));
        memoria.setValorMemoria("1","4");
        System.out.println(memoria.toString());
        while (!instrucoes.isEmpty() || existemInstrucoesEmExecucao() || todasEstacoesReservaOcupadas()) {
            // Etapa 1: Emissão de instruções
            if (!instrucoes.isEmpty()) {
                Instrucao instrucao = instrucoes.peek();
                EstacaoReserva estacaoDisponivel = getEstacaoReservaDisponivel(instrucao.getTipo());
                // Verifica se há dependências de dados
                boolean stall = false;
                for (Registrador reg : registradores) {
                    if (instrucao.getOperando1().equals(reg.getNome()) && !reg.getQi().isEmpty()) {
                        stall = true;
                        break;
                    }
                    if (instrucao.getOperando2() != null && instrucao.getOperando2().equals(reg.getNome()) && !reg.getQi().isEmpty()) {
                        stall = true;
                        break;
                    }
                }

                if (estacaoDisponivel != null && !stall) {
                    instrucoes.poll();
                    emitirInstrucao(instrucao, estacaoDisponivel);
                }
            }

            // Etapa 2: Execução de instruções
            for (UnidadeFuncional uf : unidadesFuncionais) {
                if (uf.isOcupado() && uf.getTempoRestante() > 0) {
                    uf.setTempoRestante(uf.getTempoRestante() - 1);
                }
            }

            // Etapa 3: Conclusão das instruções
            for (UnidadeFuncional uf : unidadesFuncionais) {
                if (uf.isOcupado() && uf.getTempoRestante() == 0) {
                    concluirInstrucao(uf);
                }
            }
            ciclo++;
            TomasuloView.imprimirEstado(ciclo,estacoesReserva,unidadesFuncionais,registradores);
            System.out.println(memoria.toString());
        }
    }
    private static boolean existemInstrucoesEmExecucao() {
        for (UnidadeFuncional uf : unidadesFuncionais) {
            if (uf.isOcupado()) {
                return true;
            }
        }
        return false;
    }
    private static boolean todasEstacoesReservaOcupadas() {
        for (EstacaoReserva er : estacoesReserva) {
            if (!er.isOcupado()) {
                return false;
            }
        }
        return true;
    }

    private static EstacaoReserva getEstacaoReservaDisponivel(String tipoInstrucao) {
        for (EstacaoReserva er : estacoesReserva) {
            if (!er.isOcupado() && er.getNome().startsWith(tipoInstrucao)) {
                return er;
            }
        }
        return null;
    }

    private static void emitirInstrucao(Instrucao instrucao, EstacaoReserva estacaoReserva) {
        estacaoReserva.setOcupado(true);
        estacaoReserva.setOperacao(instrucao.getTipo());
        if (instrucao.getTipo().equals("LOAD.D")||instrucao.getTipo().equals("STORE.D")){
            estacaoReserva.setQj(instrucao.getDestino());
            estacaoReserva.setVk(getRegistrador(instrucao.getOperando1()).getValor());
            Registrador regDestino = getRegistrador(instrucao.getDestino());

            if (regDestino != null) {
                regDestino.setQi(estacaoReserva.getNome());
            }
        }else{
            Registrador reg1 = getRegistrador(instrucao.getOperando1());
            Registrador reg2 = getRegistrador(instrucao.getOperando2());

            if (reg1 != null && reg1.getQi().isEmpty()) {
                estacaoReserva.setVj(reg1.getValor());
            }else{
                estacaoReserva.setQj(reg1.getQi());
            }

            if (reg2 != null && reg2.getQi().isEmpty()) {
                estacaoReserva.setVk(reg2.getValor());
            }else{
                estacaoReserva.setQk(reg2.getQi());
            }
            Registrador regDestino = getRegistrador(instrucao.getDestino());

            if (regDestino != null) {
                regDestino.setQi(estacaoReserva.getNome());
            }
        }


        UnidadeFuncional uf = getUnidadeFuncionalDisponivel(instrucao.getTipo());
        if (uf != null) {
            uf.setOcupado(true);
            uf.setTempoRestante(getTempoExecucao(instrucao.getTipo()));
        }
    }
    private static String buscarValor(String valor, List<EstacaoReserva> estacoesReserva) {
        try {
            Double.parseDouble(valor);
            return valor;
        } catch (NumberFormatException e) {
            for (EstacaoReserva estacao : estacoesReserva) {
                if (estacao.getNome().equals(valor)) {
                    return estacao.getResultado();
                }
            }
        }
        return null;
    }

    private static void concluirInstrucao(UnidadeFuncional uf) {
        String estacaoReservaNome = uf.getNome();
        EstacaoReserva er = getEstacaoReserva(estacaoReservaNome);
        String resultado = null;
        if (er != null) {
            String vj = buscarValor(er.getVj(), estacoesReserva);
            String vk = buscarValor(er.getVk(), estacoesReserva);
            String qj = buscarValor(er.getQj(), estacoesReserva);
            String qk = buscarValor(er.getQk(), estacoesReserva);


            if (er.getOperacao().equals("LOAD.D")) {
                resultado = calcularResultado(er.getOperacao(), qj, vk);
                er.setResultado(resultado);
                er.setQj("");
            } else if (vj != null && vk != null) {
                resultado = calcularResultado(er.getOperacao(), vj, vk);
                er.setResultado(resultado);
            }
        }

        for (Registrador reg : registradores) {
            if (reg.getQi().equals(estacaoReservaNome) && !er.getOperacao().equals("STORE.D")) {
                reg.setValor(resultado);
                reg.setQi("");
            } else if (reg.getNome().equals(er.getQj())) {
                String endereco = reg.getValor();
                String valor = er.getVk();
                memoria.setValorMemoria(endereco, valor);
                reg.setQi("");
            }
        }

        for (EstacaoReserva estacao : estacoesReserva) {
            if (estacao.getQj().equals(estacaoReservaNome)) {
                estacao.setVj(resultado);
                estacao.setQj("");
            }
            if (estacao.getQk().equals(estacaoReservaNome)) {
                estacao.setVk(resultado);
                estacao.setQk("");
            }
        }

        if (er != null) {
            er.setOcupado(false);
            er.setOperacao("");
            er.setVj("");
            er.setVk("");
            er.setQj("");
            er.setQk("");
        }
        uf.setOcupado(false);
    }



    private static Registrador getRegistrador(String nome) {
        for (Registrador reg : registradores) {
            if (reg.getNome().equals(nome)) {
                return reg;
            }
        }
        return null;
    }

    private static UnidadeFuncional getUnidadeFuncionalDisponivel(String tipoInstrucao) {
        for (UnidadeFuncional uf : unidadesFuncionais) {
            if (!uf.isOcupado() && uf.getNome().startsWith(tipoInstrucao)) {
                return uf;
            }
        }
        return null;
    }

    private static EstacaoReserva getEstacaoReserva(String nome) {
        for (EstacaoReserva er : estacoesReserva) {
            if (er.getNome().equals(nome)) {
                return er;
            }
        }
        return null;
    }
    private static int getTempoExecucao(String tipoInstrucao) {
        switch (tipoInstrucao) {
            case "ADD.D":
                return 3; // Tempo de execução para instruções ADD
            case "SUB.D":
                return 3; // Tempo de execução para instruções SUB
            case "MUL.D":
                return 10; // Tempo de execução para instruções MUL
            case "DIV.D":
                return 10; // Tempo de execução para instruções DIV
            case "LOAD.D":
                return 5; // Tempo de execução para instruções LOAD
            case "STORE.D":
                return 4; // Tempo de execução para instruções STORE
            default:
                return 0; // Tipo de instrução desconhecido
        }
    }

    private static String calcularResultado(String operacao, String valor1, String valor2) {
        if (operacao.equals("ADD.D")) {
            double resultado = Double.parseDouble(valor1) + Double.parseDouble(valor2);
            return String.valueOf(resultado);
        } else if (operacao.equals("MUL.D")) {
            double resultado = Double.parseDouble(valor1) * Double.parseDouble(valor2);
            return String.valueOf(resultado);
        } else if (operacao.equals("SUB.D")) {
            double resultado = Double.parseDouble(valor1) - Double.parseDouble(valor2);
            return String.valueOf(resultado);
        } else if (operacao.equals("DIV.D")) {
            double resultado = Double.parseDouble(valor1) / Double.parseDouble(valor2);
            return String.valueOf(resultado);
        } else if (operacao.equals("LOAD.D")) {
            String valorMemoria = memoria.getValorMemoria(valor2); // Obtém o valor armazenado no endereço de memória
            return valorMemoria;
        }
        // Se a operação não for reconhecida, retorne uma string vazia ou algum valor de erro adequado
        return "";
    }

}
