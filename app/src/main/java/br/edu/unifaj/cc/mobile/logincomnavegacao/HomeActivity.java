package br.edu.unifaj.cc.mobile.logincomnavegacao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.User;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

/**
 * Activity responsável pela tela principal (Home).
 * Exibe o nome do usuário e botões para ações.
 */
public class HomeActivity extends AppCompatActivity {
    
    private TextView txtNomeUsuario;
    private Button btnRegistrarDoacao;
    private Button btnVerHistorico;
    private Button btnSair;
    private PrefsManager prefsManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        // Inicializa o gerenciador de preferências
        prefsManager = new PrefsManager(this);
        
        // Verifica se não está logado, redireciona para login
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        // Inicializa os componentes da tela
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        btnRegistrarDoacao = findViewById(R.id.btnRegistrarDoacao);
        btnVerHistorico = findViewById(R.id.btnVerHistorico);
        btnSair = findViewById(R.id.btnSair);
        
        // Carrega o nome do usuário
        User user = prefsManager.getUser();
        if (user != null) {
            txtNomeUsuario.setText("Olá, " + user.getNome() + "!");
        }
        
        // Configura o botão Registrar Doação
        btnRegistrarDoacao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DoacaoActivity.class);
            startActivity(intent);
        });
        
        // Configura o botão Ver Histórico
        btnVerHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoricoActivity.class);
            startActivity(intent);
        });
        
        // Configura o botão Sair
        btnSair.setOnClickListener(v -> {
            prefsManager.logout();
            Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
            irParaLogin();
        });
    }
    
    /**
     * Redireciona para a tela de login.
     */
    private void irParaLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}