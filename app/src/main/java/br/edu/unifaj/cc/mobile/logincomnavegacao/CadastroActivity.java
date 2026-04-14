package br.edu.unifaj.cc.mobile.logincomnavegacao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.User;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

/**
 * Activity responsável pela tela de cadastro de novo usuário.
 * Salva os dados do usuário no SharedPreferences.
 */
public class CadastroActivity extends AppCompatActivity {
    
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editTipoSanguineo;
    private Button btnCadastrar;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        
        // Inicializa o gerenciador de preferências
        prefsManager = new PrefsManager(this);
        
        // Inicializa os componentes da tela
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editTipoSanguineo = findViewById(R.id.editTipoSanguineo);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        // Configura o botão Cadastrar
        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());
        
        // Configura o botão Voltar
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    /**
     * Valida e salva os dados do novo usuário.
     */
    private void cadastrarUsuario() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String tipoSanguineo = editTipoSanguineo.getText().toString().trim();
        
        // Valida campos vazios
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || tipoSanguineo.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Cria o objeto User e salva
        User user = new User(nome, email, senha, tipoSanguineo);
        prefsManager.salvarUser(user);
        
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
        
        // Retorna para a tela de login
        finish();
    }
}