package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiClient;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiService;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.BolsaSangueResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ErrorResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.DateUtils;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.ValidacaoUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoacaoActivity extends AppCompatActivity {

    private EditText editData;
    private EditText editLocal;
    private EditText editQuantidade;
    private EditText editVolumeMl;
    private Button btnSalvar;
    private Button btnVoltar;
    private ApiService apiService;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        apiService = ApiClient.getInstance(this);

        if (ApiClient.getToken(this) == null) {
            irParaLogin();
            return;
        }

        editData = findViewById(R.id.editData);
        editLocal = findViewById(R.id.editLocal);
        editQuantidade = findViewById(R.id.editQuantidade);
        editVolumeMl = findViewById(R.id.editVolumeMl);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        calendar = Calendar.getInstance();
        editData.setText(DateUtils.getDataAtual());

        editData.setOnClickListener(v -> mostrarDatePicker());
        btnSalvar.setOnClickListener(v -> salvarBolsa());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void salvarBolsa() {
        String dataColeta = editData.getText().toString().trim();
        String local = editLocal.getText().toString().trim();
        String volumeStr = editVolumeMl.getText().toString().trim();

        String validacao = ValidacaoUtils.validarCamposObrigatorios(dataColeta, local, volumeStr);
        if (validacao != null) {
            Toast.makeText(this, validacao, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarData(dataColeta)) {
            Toast.makeText(this, "Data invalida", Toast.LENGTH_SHORT).show();
            return;
        }
        int volumeMl;
        try {
            volumeMl = Integer.parseInt(volumeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Volume invalido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarVolumeDoacao(volumeMl)) {
            Toast.makeText(this, "Volume deve estar entre 200ml e 470ml", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSalvar.setEnabled(false);

        Map<String, Object> body = new HashMap<>();
        body.put("dataColeta", dataColeta);
        body.put("local", local);
        body.put("volumeMl", volumeMl);

        apiService.criarBolsa(body).enqueue(new Callback<BolsaSangueResponse>() {
            @Override
            public void onResponse(Call<BolsaSangueResponse> call, Response<BolsaSangueResponse> response) {
                btnSalvar.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(DoacaoActivity.this,
                            "Bolsa de sangue salva com sucesso!", Toast.LENGTH_SHORT).show();
                    editData.setText(DateUtils.getDataAtual());
                    editLocal.setText("");
                    editVolumeMl.setText("");
                } else {
                    String erro = "Erro ao salvar";
                    try {
                        if (response.errorBody() != null) {
                            ErrorResponse err = new com.google.gson.Gson().fromJson(
                                    response.errorBody().charStream(), ErrorResponse.class);
                            if (err.getErro() != null) erro = err.getErro();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(DoacaoActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BolsaSangueResponse> call, Throwable t) {
                btnSalvar.setEnabled(true);
                Toast.makeText(DoacaoActivity.this,
                        "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editData.setText(DateUtils.formatarData(calendar.getTime()));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void irParaLogin() {
        Intent intent = new Intent(DoacaoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
