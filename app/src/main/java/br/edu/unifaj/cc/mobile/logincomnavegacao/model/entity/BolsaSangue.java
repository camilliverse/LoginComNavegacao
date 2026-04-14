package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

public class BolsaSangue {
    private String codigo;
    private String tipoSanguineo;
    private String fatorRh;
    private String dataColeta;
    private String dataValidade;
    private int volumeMl;
    private String status;
    private Hemocentro hemocentroOrigem;
    private String receptorCpf;
    private String dataDestinacao;

    public BolsaSangue() {
    }

    public BolsaSangue(String codigo, String tipoSanguineo, String fatorRh, 
                      String dataColeta, int volumeMl) {
        this.codigo = codigo;
        this.tipoSanguineo = tipoSanguineo;
        this.fatorRh = fatorRh;
        this.dataColeta = dataColeta;
        this.volumeMl = volumeMl;
        this.status = "DISPONIVEL";
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public String getFatorRh() {
        return fatorRh;
    }

    public void setFatorRh(String fatorRh) {
        this.fatorRh = fatorRh;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        this.volumeMl = volumeMl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Hemocentro getHemocentroOrigem() {
        return hemocentroOrigem;
    }

    public void setHemocentroOrigem(Hemocentro hemocentroOrigem) {
        this.hemocentroOrigem = hemocentroOrigem;
    }

    public String getReceptorCpf() {
        return receptorCpf;
    }

    public void setReceptorCpf(String receptorCpf) {
        this.receptorCpf = receptorCpf;
    }

    public String getDataDestinacao() {
        return dataDestinacao;
    }

    public void setDataDestinacao(String dataDestinacao) {
        this.dataDestinacao = dataDestinacao;
    }

    public String getTipoCompleto() {
        return tipoSanguineo + fatorRh;
    }

    public boolean estaDisponivel() {
        return "DISPONIVEL".equals(status);
    }
}