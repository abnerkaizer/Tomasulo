package model;

public class Registrador {
    private String nome;
    private String valor;
    private String qi;
    public Registrador(String nome, String valor, String qi) {
        this.nome = nome;
        this.valor = valor;
        this.qi = qi;
    }

    public Registrador(String nome) {
        this.nome = nome;
        this.valor = "";
        this.qi = "";
    }
    @Override
    public String toString() {
        return "Registrador{" +
                "nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                ", qi='" + qi + '\'' +
                '}';
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }
}
