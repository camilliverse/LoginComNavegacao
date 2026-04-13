package br.edu.unifaj.cc.mobile.logincomnavegacao.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.Doacao;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.User;

/**
 * Classe auxiliar para gerenciar dados no SharedPreferences.
 * Armazena dados do usuário e lista de doações em formato JSON.
 */
public class PrefsManager {
    
    private static final String PREF_NAME = "PrefDoacaoSangue";
    private static final String KEY_USER = "user";
    private static final String KEY_DOACOES = "doacoes";
    private static final String KEY_LOGGED_EMAIL = "logged_email";
    
    private final SharedPreferences prefs;
    private final Gson gson;
    
    public PrefsManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }
    
    /**
     * Salva os dados do usuário no SharedPreferences.
     */
    public void salvarUser(User user) {
        String json = gson.toJson(user);
        prefs.edit().putString(KEY_USER, json).apply();
    }
    
    /**
     * Recupera o usuário salvo no SharedPreferences.
     */
    public User getUser() {
        String json = prefs.getString(KEY_USER, null);
        if (json != null) {
            return gson.fromJson(json, User.class);
        }
        return null;
    }
    
    /**
     * Verifica se o usuário existe e se a senha corresponde.
     */
    public boolean login(String email, String senha) {
        User user = getUser();
        if (user != null && user.getEmail().equals(email) && user.getSenha().equals(senha)) {
            prefs.edit().putString(KEY_LOGGED_EMAIL, email).apply();
            return true;
        }
        return false;
    }
    
    /**
     * Verifica se há um usuário logado.
     */
    public boolean isLoggedIn() {
        return prefs.getString(KEY_LOGGED_EMAIL, null) != null;
    }
    
    /**
     * Faz logout do usuário.
     */
    public void logout() {
        prefs.edit().remove(KEY_LOGGED_EMAIL).apply();
    }
    
    /**
     * Retorna o email do usuário logado.
     */
    public String getLoggedEmail() {
        return prefs.getString(KEY_LOGGED_EMAIL, null);
    }
    
    /**
     * Salva uma lista de doações no SharedPreferences.
     */
    public void salvarDoacoes(List<Doacao> doacoes) {
        String json = gson.toJson(doacoes);
        prefs.edit().putString(KEY_DOACOES, json).apply();
    }
    
    /**
     * Recupera a lista de doações do SharedPreferences.
     */
    public List<Doacao> getDoacoes() {
        String json = prefs.getString(KEY_DOACOES, null);
        if (json != null) {
            Type listType = new TypeToken<ArrayList<Doacao>>(){}.getType();
            return gson.fromJson(json, listType);
        }
        return new ArrayList<>();
    }
    
    /**
     * Adiciona uma nova doação à lista existente.
     */
    public void adicionarDoacao(Doacao doacao) {
        List<Doacao> doacoes = getDoacoes();
        doacoes.add(doacao);
        salvarDoacoes(doacoes);
    }
    
    /**
     * Limpa todos os dados salvos.
     */
    public void limparDados() {
        prefs.edit().clear().apply();
    }
}