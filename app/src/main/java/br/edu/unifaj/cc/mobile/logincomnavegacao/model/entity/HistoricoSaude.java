package br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity;

import java.util.List;

public class HistoricoSaude {
    private String cpfDoador;
    private String ultimaDoacao;
    private int totalDoacoes;
    private List<String> restricoes;
    private List<String> doencasCronicas;
    private List<String> medicamentos;
    private boolean alergias;
    private String descricaoAlergias;
    private boolean cirurgiasRecentes;
    private String dataCirurgia;
    private boolean viagemInternacional;
    private String paisVisitados;
    private String observacoes;

    public HistoricoSaude() {
    }

    public HistoricoSaude(String cpfDoador) {
        this.cpfDoador = cpfDoador;
        this.totalDoacoes = 0;
    }

    public String getCpfDoador() {
        return cpfDoador;
    }

    public void setCpfDoador(String cpfDoador) {
        this.cpfDoador = cpfDoador;
    }

    public String getUltimaDoacao() {
        return ultimaDoacao;
    }

    public void setUltimaDoacao(String ultimaDoacao) {
        this.ultimaDoacao = ultimaDoacao;
    }

    public int getTotalDoacoes() {
        return totalDoacoes;
    }

    public void setTotalDoacoes(int totalDoacoes) {
        this.totalDoacoes = totalDoacoes;
    }

    public List<String> getRestricoes() {
        return restricoes;
    }

    public void setRestricoes(List<String> restricoes) {
        this.restricoes = restricoes;
    }

    public List<String> getDoencasCronicas() {
        return doencasCronicas;
    }

    public void setDoencasCronicas(List<String> doencasCronicas) {
        this.doencasCronicas = doencasCronicas;
    }

    public List<String> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<String> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public boolean isAlergias() {
        return alergias;
    }

    public void setAlergias(boolean alergias) {
        this.alergias = alergias;
    }

    public String getDescricaoAlergias() {
        return descricaoAlergias;
    }

    public void setDescricaoAlergias(String descricaoAlergias) {
        this.descricaoAlergias = descricaoAlergias;
    }

    public boolean isCirurgiasRecentes() {
        return cirurgiasRecentes;
    }

    public void setCirurgiasRecentes(boolean cirurgiasRecentes) {
        this.cirurgiasRecentes = cirurgiasRecentes;
    }

    public String getDataCirurgia() {
        return dataCirurgia;
    }

    public void setDataCirurgia(String dataCirurgia) {
        this.dataCirurgia = dataCirurgia;
    }

    public boolean isViagemInternacional() {
        return viagemInternacional;
    }

    public void setViagemInternacional(boolean viagemInternacional) {
        this.viagemInternacional = viagemInternacional;
    }

    public String getPaisVisitados() {
        return paisVisitados;
    }

    public void setPaisVisitados(String paisVisitados) {
        this.paisVisitados = paisVisitados;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean temRestricoes() {
        return restricoes != null && !restricoes.isEmpty();
    }

    public boolean podeDoar(int mesesIntervalo) {
        if (cirurgiasRecentes) return false;
        if (viagemInternacional) return false;
        if (temRestricoes()) return false;
        return true;
    }
}