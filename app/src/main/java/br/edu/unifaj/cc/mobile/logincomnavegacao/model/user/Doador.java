package br.edu.unifaj.cc.mobile.logincomnavegacao.model.user;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.User;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Contato;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Endereco;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.HistoricoSaude;

public class Doador extends User {
    private String cpf;

    public Doador() {
        super();
    }

    public Doador(String nome, String email, String senha, String tipoSanguineo) {
        super(nome, email, senha, tipoSanguineo);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}