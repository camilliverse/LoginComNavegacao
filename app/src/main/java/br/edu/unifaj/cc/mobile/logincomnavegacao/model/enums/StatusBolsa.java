package br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums;

public enum StatusBolsa {
    DISPONIVEL("Disponível"),
    RESERVADA("Reservada"),
    UTILIZADA("Utilizada"),
    VENCIDA("Vencida");

    private final String descricao;

    StatusBolsa(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}