package br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums;

public enum StatusAgendamento {
    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    CANCELADO("Cancelado"),
    REALIZADO("Realizado");

    private final String descricao;

    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}