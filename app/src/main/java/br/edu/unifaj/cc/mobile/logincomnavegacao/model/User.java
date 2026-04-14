package br.edu.unifaj.cc.mobile.logincomnavegacao.model;

public class User {
    private String nome;
    private String email;
    private String senha;
    private String tipoSanguineo;

    public User() {
    }

    public User(String nome, String email, String senha, String tipoSanguineo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
}