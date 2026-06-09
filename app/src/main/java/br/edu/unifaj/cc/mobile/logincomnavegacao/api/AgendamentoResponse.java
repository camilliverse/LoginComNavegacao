package br.edu.unifaj.cc.mobile.logincomnavegacao.api;

public class AgendamentoResponse {
    private String id;
    private String data;
    private String hora;
    private String status;
    private String hemocentroNome;
    private String hemocentroEndereco;
    private String hemocentroTelefone;

    public String getId() { return id; }
    public String getData() { return data; }
    public String getHora() { return hora; }
    public String getStatus() { return status; }
    public String getHemocentroNome() { return hemocentroNome; }
    public String getHemocentroEndereco() { return hemocentroEndereco; }
    public String getHemocentroTelefone() { return hemocentroTelefone; }
}
