package br.edu.unifaj.cc.mobile.logincomnavegacao.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.user.Doador;

public class PrefsManager {

    private static final String PREF_NAME = "PrefDoacaoSangue";
    private static final String KEY_DOADOR = "doador";

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

    public void logout() {
        prefs.edit().clear().apply();
    }
}
