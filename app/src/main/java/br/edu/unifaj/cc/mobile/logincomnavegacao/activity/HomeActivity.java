package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.user.Doador;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

public class HomeActivity extends AppCompatActivity {
    
    private TextView txtNomeUsuario;
    private TextView txtTipoSanguineo;
    private Button btnRegistrarDoacao;
    private Button btnAgendarDoacao;
    private Button btnVerHistorico;
    private Button btnVerAgendamentos;
    private Button btnSair;
    private PrefsManager prefsManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        prefsManager = new PrefsManager(this);
        
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        txtTipoSanguineo = findViewById(R.id.txtTipoSanguineo);
        btnRegistrarDoacao = findViewById(R.id.btnRegistrarDoacao);
        btnAgendarDoacao = findViewById(R.id.btnAgendarDoacao);
        btnVerHistorico = findViewById(R.id.btnVerHistorico);
        btnVerAgendamentos = findViewById(R.id.btnVerAgendamentos);
        btnSair = findViewById(R.id.btnSair);
        
        Doador doador = prefsManager.getDoador();
        if (doador != null) {
            txtNomeUsuario.setText("Olá, " + doador.getNome() + "!");
            if (doador.getTipoSanguineo() != null) {
                txtTipoSanguineo.setText("Tipo Sanguíneo: " + doador.getTipoSanguineo());
            }
        }
        
        btnRegistrarDoacao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DoacaoActivity.class);
            startActivity(intent);
        });
        
        btnAgendarDoacao.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AgendamentoActivity.class);
            startActivity(intent);
        });
        
        btnVerHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HistoricoActivity.class);
            startActivity(intent);
        });
        
        btnVerAgendamentos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ListaAgendamentosActivity.class);
            startActivity(intent);
        });
        
        btnSair.setOnClickListener(v -> {
            prefsManager.logout();
            Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
            irParaLogin();
        });
    }
    
    private void irParaLogin() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}