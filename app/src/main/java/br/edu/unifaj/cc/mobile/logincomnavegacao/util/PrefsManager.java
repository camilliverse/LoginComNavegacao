package br.edu.unifaj.cc.mobile.logincomnavegacao.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Agendamento;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.BolsaSangue;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.user.Doador;

/**
 * Classe auxiliar para gerenciar dados no SharedPreferences.
 * Armazena dados do usuário e lista de doações em formato JSON.
 */
public class PrefsManager {
    
    private static final String PREF_NAME = "PrefDoacaoSangue";
    private static final String KEY_DOADOR = "doador";
    private static final String KEY_BOLSAS = "bolsas_sangue";
    private static final String KEY_AGENDAMENTOS = "agendamentos";
    private static final String KEY_LOGGED_EMAIL = "logged_email";
    
    private final SharedPreferences prefs;
    private final Gson gson;
    
    public PrefsManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }
    
    public void salvarDoador(Doador doador) {
        String json = gson.toJson(doador);
        prefs.edit().putString(KEY_DOADOR, json).apply();
    }
    
    public Doador getDoador() {
        String json = prefs.getString(KEY_DOADOR, null);
        if (json != null) {
            return gson.fromJson(json, Doador.class);
        }
        return null;
    }
    
    public boolean login(String email, String senha) {
        Doador doador = getDoador();
        if (doador != null && doador.getEmail().equals(email) && doador.getSenha().equals(senha)) {
            prefs.edit().putString(KEY_LOGGED_EMAIL, email).apply();
            return true;
        }
        return false;
    }
    
    public boolean isLoggedIn() {
        return prefs.getString(KEY_LOGGED_EMAIL, null) != null;
    }
    
    public void logout() {
        prefs.edit().remove(KEY_LOGGED_EMAIL).apply();
    }
    
    public String getLoggedEmail() {
        return prefs.getString(KEY_LOGGED_EMAIL, null);
    }
    
    public void salvarBolsas(List<BolsaSangue> bolsas) {
        String json = gson.toJson(bolsas);
        prefs.edit().putString(KEY_BOLSAS, json).apply();
    }
    
    public List<BolsaSangue> getBolsas() {
        String json = prefs.getString(KEY_BOLSAS, null);
        if (json != null) {
            Type listType = new TypeToken<ArrayList<BolsaSangue>>(){}.getType();
            return gson.fromJson(json, listType);
        }
        return new ArrayList<>();
    }
    
    public void adicionarBolsa(BolsaSangue bolsa) {
        List<BolsaSangue> bolsas = getBolsas();
        bolsas.add(bolsa);
        salvarBolsas(bolsas);
    }
    
    public void salvarAgendamentos(List<Agendamento> agendamentos) {
        String json = gson.toJson(agendamentos);
        prefs.edit().putString(KEY_AGENDAMENTOS, json).apply();
    }
    
    public List<Agendamento> getAgendamentos() {
        String json = prefs.getString(KEY_AGENDAMENTOS, null);
        if (json != null) {
            Type listType = new TypeToken<ArrayList<Agendamento>>(){}.getType();
            return gson.fromJson(json, listType);
        }
        return new ArrayList<>();
    }
    
    public void adicionarAgendamento(Agendamento agendamento) {
        List<Agendamento> agendamentos = getAgendamentos();
        agendamentos.add(agendamento);
        salvarAgendamentos(agendamentos);
    }
    
    public void cancelarAgendamento(String agendamentoId) {
        List<Agendamento> agendamentos = getAgendamentos();
        for (Agendamento a : agendamentos) {
            if (a.getId().equals(agendamentoId)) {
                a.cancelar();
                break;
            }
        }
        salvarAgendamentos(agendamentos);
    }

    public List<Agendamento> getAgendamentosPorCpf(String cpf) {
        List<Agendamento> todos = getAgendamentos();
        List<Agendamento> filtrados = new ArrayList<>();
        for (Agendamento a : todos) {
            if (cpf != null && cpf.equals(a.getCpfDoador())) {
                filtrados.add(a);
            }
        }
        return filtrados;
    }
    
    public void limparDados() {
        prefs.edit().clear().apply();
    }
    
    // Métodos de compatibilidade (deprecated)
    @Deprecated
    public void salvarUser(Object user) {
        if (user instanceof Doador) {
            salvarDoador((Doador) user);
        }
    }
    
    @Deprecated
    public Object getUser() {
        return getDoador();
    }
    
    @Deprecated
    public List<?> getDoacoes() {
        return getBolsas();
    }
    
    @Deprecated
    public void adicionarDoacao(Object doacao) {
        if (doacao instanceof BolsaSangue) {
            adicionarBolsa((BolsaSangue) doacao);
        }
    }
}