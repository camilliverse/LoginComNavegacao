package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.adapter.BolsaSangueAdapter;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.BolsaSangue;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

public class HistoricoActivity extends AppCompatActivity {
    
    private RecyclerView recyclerBolsas;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    private BolsaSangueAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        
        prefsManager = new PrefsManager(this);
        
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        recyclerBolsas = findViewById(R.id.recyclerDoacoes);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        recyclerBolsas.setLayoutManager(new LinearLayoutManager(this));
        
        List<BolsaSangue> bolsas = prefsManager.getBolsas();
        if (bolsas == null) {
            bolsas = new ArrayList<>();
        }
        
        adapter = new BolsaSangueAdapter(bolsas);
        recyclerBolsas.setAdapter(adapter);
        
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    private void irParaLogin() {
        Intent intent = new Intent(HistoricoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}