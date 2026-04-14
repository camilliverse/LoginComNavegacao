package br.edu.unifaj.cc.mobile.logincomnavegacao;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.unifaj.cc.mobile.logincomnavegacao.model.Doacao;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;

/**
 * Activity responsável pelo registro de novas doações.
 * Salva os dados da doação no SharedPreferences.
 */
public class DoacaoActivity extends AppCompatActivity {
    
    private EditText editData;
    private EditText editLocal;
    private EditText editQuantidade;
    private Button btnSalvar;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);
        
        // Inicializa o gerenciador de preferências
        prefsManager = new PrefsManager(this);
        
        // Verifica se não está logado, redireciona para login
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        // Inicializa os componentes da tela
        editData = findViewById(R.id.editData);
        editLocal = findViewById(R.id.editLocal);
        editQuantidade = findViewById(R.id.editQuantidade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        editData.setText(dateFormat.format(calendar.getTime()));
        
        editData.setOnClickListener(v -> mostrarDatePicker());
        
        // Configura o botão Salvar
        btnSalvar.setOnClickListener(v -> salvarDoacao());
        
        // Configura o botão Voltar
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    /**
     * Valida e salva os dados da doação.
     */
    private void salvarDoacao() {
        String data = editData.getText().toString().trim();
        String local = editLocal.getText().toString().trim();
        String quantidade = editQuantidade.getText().toString().trim();
        
        // Valida campos vazios
        if (data.isEmpty() || local.isEmpty() || quantidade.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Cria o objeto Doacao e salva
        Doacao doacao = new Doacao(data, local, quantidade);
        prefsManager.adicionarDoacao(doacao);
        
        Toast.makeText(this, "Doação salva com sucesso!", Toast.LENGTH_SHORT).show();
        
        // Limpa os campos
        editData.setText(dateFormat.format(calendar.getTime()));
        editLocal.setText("");
        editQuantidade.setText("");
    }
    
    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editData.setText(dateFormat.format(calendar.getTime()));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    /**
     * Redireciona para a tela de login.
     */
    private void irParaLogin() {
        Intent intent = new Intent(DoacaoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}