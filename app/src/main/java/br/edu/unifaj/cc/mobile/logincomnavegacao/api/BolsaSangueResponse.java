package br.edu.unifaj.cc.mobile.logincomnavegacao.api;

public class BolsaSangueResponse {
    private String codigo;
    private String tipoCompleto;
    private String dataColeta;
    private String dataValidade;
    private int volumeMl;
    private String status;
    private String hemocentroNome;

    public String getCodigo() { return codigo; }
    public String getTipoCompleto() { return tipoCompleto; }
    public String getDataColeta() { return dataColeta; }
    public String getDataValidade() { return dataValidade; }
    public int getVolumeMl() { return volumeMl; }
    public String getStatus() { return status; }
    public String getHemocentroNome() { return hemocentroNome; }
}
