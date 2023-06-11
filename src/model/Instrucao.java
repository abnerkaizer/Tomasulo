package model;

public class Instrucao {
    private String tipo;
    private String destino;
    private String operando1;
    private String operando2;

    public Instrucao(String tipo, String destino, String operando1, String operando2) {
        this.tipo = tipo;
        this.destino = destino;
        this.operando1 = operando1;
        this.operando2 = operando2;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOperando1() {
        return operando1;
    }

    public void setOperando1(String operando1) {
        this.operando1 = operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    public void setOperando2(String operando2) {
        this.operando2 = operando2;
    }

    @Override
    public String toString() {
        return "Instrucao{" +
                "tipo='" + tipo + '\'' +
                ", destino='" + destino + '\'' +
                ", operando1='" + operando1 + '\'' +
                ", operando2='" + operando2 + '\'' +
                '}';
    }
}
