package br.edu.unifaj.cc.mobile.logincomnavegacao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.adapter.DoacaoAdapter;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.Doacao;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

/**
 * Activity responsável pela tela de histórico de doações.
 * Exibe a lista de doações em um RecyclerView.
 */
public class HistoricoActivity extends AppCompatActivity {
    
    private RecyclerView recyclerDoacoes;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    private DoacaoAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        
        // Inicializa o gerenciador de preferências
        prefsManager = new PrefsManager(this);
        
        // Verifica se não está logado, redireciona para login
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        // Inicializa os componentes da tela
        recyclerDoacoes = findViewById(R.id.recyclerDoacoes);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        // Configura o RecyclerView
        recyclerDoacoes.setLayoutManager(new LinearLayoutManager(this));
        
        // Carrega as doações
        List<Doacao> doacoes = prefsManager.getDoacoes();
        if (doacoes == null) {
            doacoes = new ArrayList<>();
        }
        
        // Cria o adapter
        adapter = new DoacaoAdapter(doacoes);
        recyclerDoacoes.setAdapter(adapter);
        
        // Configura o botão Voltar
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    /**
     * Redireciona para a tela de login.
     */
    private void irParaLogin() {
        Intent intent = new Intent(HistoricoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}