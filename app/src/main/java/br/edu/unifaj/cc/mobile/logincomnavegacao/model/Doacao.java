package br.edu.unifaj.cc.mobile.logincomnavegacao.model;

public class Doacao {
    private String data;
    private String local;
    private String quantidade;

    public Doacao() {
    }

    public Doacao(String data, String local, String quantidade) {
        this.data = data;
        this.local = local;
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
}