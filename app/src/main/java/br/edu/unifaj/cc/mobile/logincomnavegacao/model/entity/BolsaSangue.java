package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.FatorRh;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.StatusBolsa;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.TipoSanguineo;

public class BolsaSangue {
    private String codigo;
    private TipoSanguineo tipoSanguineo;
    private FatorRh fatorRh;
    private String dataColeta;
    private String dataValidade;
    private int volumeMl;
    private StatusBolsa status;
    private Hemocentro hemocentroOrigem;
    private String receptorCpf;
    private String dataDestinacao;

    public BolsaSangue() {
    }

    public BolsaSangue(String codigo, TipoSanguineo tipoSanguineo, FatorRh fatorRh, 
                      String dataColeta, int volumeMl) {
        this.codigo = codigo;
        this.tipoSanguineo = tipoSanguineo;
        this.fatorRh = fatorRh;
        this.dataColeta = dataColeta;
        this.volumeMl = volumeMl;
        this.status = StatusBolsa.DISPONIVEL;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public FatorRh getFatorRh() {
        return fatorRh;
    }

    public void setFatorRh(FatorRh fatorRh) {
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

    public StatusBolsa getStatus() {
        return status;
    }

    public void setStatus(StatusBolsa status) {
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
        if (tipoSanguineo == null || fatorRh == null) {
            return null;
        }
        return tipoSanguineo.getValor() + fatorRh.getValor();
    }

    public boolean estaDisponivel() {
        return status == StatusBolsa.DISPONIVEL;
    }
}