package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

public class Agendamento {
    private String id;
    private String data;
    private String hora;
    private Hemocentro hemocentro;
    private String cpfDoador;
    private String status;
    private String dataConfirmacao;
    private String observacoes;

    public Agendamento() {
    }

    public Agendamento(String id, String data, String hora, Hemocentro hemocentro, String cpfDoador) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.hemocentro = hemocentro;
        this.cpfDoador = cpfDoador;
        this.status = "PENDENTE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Hemocentro getHemocentro() {
        return hemocentro;
    }

    public void setHemocentro(Hemocentro hemocentro) {
        this.hemocentro = hemocentro;
    }

    public String getCpfDoador() {
        return cpfDoador;
    }

    public void setCpfDoador(String cpfDoador) {
        this.cpfDoador = cpfDoador;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(String dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isPendente() {
        return "PENDENTE".equals(status);
    }

    public boolean isConfirmado() {
        return "CONFIRMADO".equals(status);
    }

    public boolean isCancelado() {
        return "CANCELADO".equals(status);
    }

    public boolean isRealizado() {
        return "REALIZADO".equals(status);
    }

    public void confirmar() {
        this.status = "CONFIRMADO";
    }

    public void cancelar() {
        this.status = "CANCELADO";
    }

    public void marcarRealizado() {
        this.status = "REALIZADO";
    }
}