package br.edu.unifaj.cc.mobile.logincomnavegacao.api;

public class LoginResponse {
    private String token;
    private String tipo;
    private DoadorResponse doador;

    public String getToken() { return token; }
    public String getTipo() { return tipo; }
    public DoadorResponse getDoador() { return doador; }
}
