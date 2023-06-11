package model;

import java.util.HashMap;
import java.util.Map;

public class Memoria {
    private Map<String, String> memoria;

    public Map<String, String> getMemoria() {
        return memoria;
    }

    public Memoria() {
        memoria = new HashMap<>();
    }

    public String getValorMemoria(String endereco) {
        if (memoria.containsKey(endereco)) {
            return memoria.get(endereco);
        } else {
            return "Não encontrado.";
        }
    }

    public void setValorMemoria(String endereco, String valor) {
        this.memoria.put(endereco, valor);
    }

    public String getEnderecoPorValor(String valor) {
        for (Map.Entry<String, String> entry : memoria.entrySet()) {
            if (entry.getValue().equals(valor)) {
                return entry.getKey();
            }
        }
        return "Não encontrado.";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Memoria{");
        for (Map.Entry<String, String> entry : memoria.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        // Remove a vírgula e o espaço extra no final
        if (!memoria.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
