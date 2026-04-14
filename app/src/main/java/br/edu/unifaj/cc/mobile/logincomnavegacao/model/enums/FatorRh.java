package br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums;

public enum FatorRh {
    POSITIVO("+"),
    NEGATIVO("-");

    private final String valor;

    FatorRh(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static FatorRh fromValor(String valor) {
        for (FatorRh fator : values()) {
            if (fator.valor.equals(valor)) {
                return fator;
            }
        }
        return null;
    }
}