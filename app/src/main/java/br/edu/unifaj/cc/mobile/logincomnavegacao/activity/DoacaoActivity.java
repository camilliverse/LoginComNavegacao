package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.BolsaSangue;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.StatusBolsa;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.TipoSanguineo;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.FatorRh;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.DateUtils;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.ValidacaoUtils;

public class DoacaoActivity extends AppCompatActivity {
    
    private EditText editData;
    private EditText editLocal;
    private EditText editQuantidade;
    private EditText editVolumeMl;
    private Button btnSalvar;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    private java.util.Calendar calendar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);
        
        prefsManager = new PrefsManager(this);
        
        if (!prefsManager.isLoggedIn()) {
            irParaLogin();
            return;
        }
        
        editData = findViewById(R.id.editData);
        editLocal = findViewById(R.id.editLocal);
        editQuantidade = findViewById(R.id.editQuantidade);
        editVolumeMl = findViewById(R.id.editVolumeMl);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
        
        calendar = java.util.Calendar.getInstance();
        editData.setText(DateUtils.getDataAtual());
        
        editData.setOnClickListener(v -> mostrarDatePicker());
        
        btnSalvar.setOnClickListener(v -> salvarBolsa());
        
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    private void salvarBolsa() {
        String dataColeta = editData.getText().toString().trim();
        String local = editLocal.getText().toString().trim();
        String quantidade = editQuantidade.getText().toString().trim();
        String volumeStr = editVolumeMl.getText().toString().trim();
        
        // Valida campos obrigatórios
        String validacao = ValidacaoUtils.validarCamposObrigatorios(dataColeta, local, volumeStr);
        if (validacao != null) {
            Toast.makeText(this, validacao, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Valida data
        if (!ValidacaoUtils.validarData(dataColeta)) {
            Toast.makeText(this, "Data inválida", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Valida volume
        int volumeMl;
        try {
            volumeMl = Integer.parseInt(volumeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Volume inválido", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!ValidacaoUtils.validarVolumeDoacao(volumeMl)) {
            Toast.makeText(this, "Volume deve estar entre 200ml e 470ml", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Pega o tipo sanguineo do doador logado
        TipoSanguineo tipoSanguineo = null;
        FatorRh fatorRh = null;
        if (prefsManager.getDoador() != null) {
            tipoSanguineo = prefsManager.getDoador().getTipoSanguineo();
            fatorRh = prefsManager.getDoador().getFatorRh();
        }
        
        if (tipoSanguineo == null || fatorRh == null) {
            Toast.makeText(this, "Doador sem tipo sanguíneo cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Gera código único para a bolsa
        String codigo = "BS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Calcula validade (42 dias)
        String dataValidade = DateUtils.calcularValidadeBolsa(dataColeta);
        
        BolsaSangue bolsa = new BolsaSangue(codigo, tipoSanguineo, fatorRh, dataColeta, volumeMl);
        bolsa.setDataValidade(dataValidade);
        bolsa.setStatus(StatusBolsa.DISPONIVEL);
        
        prefsManager.adicionarBolsa(bolsa);
        
        Toast.makeText(this, "Bolsa de sangue salva com sucesso!", Toast.LENGTH_SHORT).show();
        
        editData.setText(DateUtils.getDataAtual());
        editLocal.setText("");
        editQuantidade.setText("");
        editVolumeMl.setText("");
    }
    
    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
            this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(java.util.Calendar.YEAR, year);
                calendar.set(java.util.Calendar.MONTH, month);
                calendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
                editData.setText(DateUtils.formatarData(calendar.getTime()));
            },
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH),
            calendar.get(java.util.Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    
    private void irParaLogin() {
        Intent intent = new Intent(DoacaoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}