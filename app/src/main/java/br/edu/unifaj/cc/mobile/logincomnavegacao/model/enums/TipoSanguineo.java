package br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums;

public enum TipoSanguineo {
    A("A"),
    B("B"),
    AB("AB"),
    O("O");

    private final String valor;

    TipoSanguineo(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoSanguineo fromValor(String valor) {
        for (TipoSanguineo tipo : values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        return null;
    }
}