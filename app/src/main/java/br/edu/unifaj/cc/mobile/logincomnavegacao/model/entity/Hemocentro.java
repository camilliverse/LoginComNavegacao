package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

public class Hemocentro {
    private String id;
    private String nome;
    private Endereco endereco;
    private String telefone;
    private String email;
    private String horarioAbertura;
    private String horarioFechamento;
    private boolean funcionamento24Horas;
    private double capacidadeColeta;

    public Hemocentro() {
    }

    public Hemocentro(String id, String nome, Endereco endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorarioAbertura() {
        return horarioAbertura;
    }

    public void setHorarioAbertura(String horarioAbertura) {
        this.horarioAbertura = horarioAbertura;
    }

    public String getHorarioFechamento() {
        return horarioFechamento;
    }

    public void setHorarioFechamento(String horarioFechamento) {
        this.horarioFechamento = horarioFechamento;
    }

    public boolean isFuncionamento24Horas() {
        return funcionamento24Horas;
    }

    public void setFuncionamento24Horas(boolean funcionamento24Horas) {
        this.funcionamento24Horas = funcionamento24Horas;
    }

    public double getCapacidadeColeta() {
        return capacidadeColeta;
    }

    public void setCapacidadeColeta(double capacidadeColeta) {
        this.capacidadeColeta = capacidadeColeta;
    }

    public String getHorarioFuncionamento() {
        if (funcionamento24Horas) {
            return "24 horas";
        }
        return horarioAbertura + " - " + horarioFechamento;
    }

    @Override
    public String toString() {
        return nome;
    }
}