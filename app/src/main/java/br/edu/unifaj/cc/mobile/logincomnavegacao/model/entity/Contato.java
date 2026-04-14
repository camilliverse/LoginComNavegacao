package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

public class Contato {
    private String telefoneFixo;
    private String celular;
    private String telefoneEmergencia;
    private String nomeEmergencia;
    private String email;

    public Contato() {
    }

    public Contato(String celular, String email) {
        this.celular = celular;
        this.email = email;
    }

    public Contato(String telefoneFixo, String celular, String telefoneEmergencia, 
                   String nomeEmergencia, String email) {
        this.telefoneFixo = telefoneFixo;
        this.celular = celular;
        this.telefoneEmergencia = telefoneEmergencia;
        this.nomeEmergencia = nomeEmergencia;
        this.email = email;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    public String getNomeEmergencia() {
        return nomeEmergencia;
    }

    public void setNomeEmergencia(String nomeEmergencia) {
        this.nomeEmergencia = nomeEmergencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean possuiContatoEmergencia() {
        return telefoneEmergencia != null && !telefoneEmergencia.isEmpty() 
               && nomeEmergencia != null && !nomeEmergencia.isEmpty();
    }
}