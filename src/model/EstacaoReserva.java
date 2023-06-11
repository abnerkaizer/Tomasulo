package model;

public class EstacaoReserva {
    private String nome;
    private boolean ocupado;
    private String operacao;
    private String vj;
    private String vk;
    private String qj;
    private String qk;
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public EstacaoReserva(String nome, boolean ocupado, String operacao, String vj, String vk, String qj, String qk) {
        this.nome = nome;
        this.ocupado = ocupado;
        this.operacao = operacao;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
    }

    public EstacaoReserva(String nome, boolean ocupado, String operacao) {
        this.nome = nome;
        this.ocupado = ocupado;
        this.operacao = operacao;
        this.vj = "";
        this.vk = "";
        this.qj = "";
        this.qk = "";
    }
    @Override
    public String toString() {
        return "EstacaoReserva{" +
                "nome='" + nome + '\'' +
                ", ocupado=" + ocupado +
                ", operacao='" + operacao + '\'' +
                ", vj='" + vj + '\'' +
                ", vk='" + vk + '\'' +
                ", qj='" + qj + '\'' +
                ", qk='" + qk + '\'' +
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

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getVj() {
        return vj;
    }

    public void setVj(String vj) {
        this.vj = vj;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }
}
