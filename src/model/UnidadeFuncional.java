package model;

public class UnidadeFuncional {
    private String nome;
    private boolean ocupado;
    private int tempoRestante;

    public UnidadeFuncional(String nome, boolean ocupado, int tempoRestante) {
        this.nome = nome;
        this.ocupado = ocupado;
        this.tempoRestante = tempoRestante;
    }
    @Override
    public String toString() {
        return "UnidadeFuncional{" +
                "nome='" + nome + '\'' +
                ", ocupado=" + ocupado +
                ", tempoRestante=" + tempoRestante +
                '}';
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(int tempoRestante) {
        this.tempoRestante = tempoRestante;
    }
}
