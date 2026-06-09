package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiClient;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiService;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.AgendamentoResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ErrorResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.HemocentroResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendamentoActivity extends AppCompatActivity {

    private Spinner spinnerHemocentro;
    private EditText editData;
    private EditText editHora;
    private Button btnAgendar;
    private Button btnVoltar;
    private ApiService apiService;
    private Calendar calendar;
    private List<HemocentroResponse> hemocentros;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        apiService = ApiClient.getInstance(this);

        if (ApiClient.getToken(this) == null) {
            irParaLogin();
            return;
        }

        spinnerHemocentro = findViewById(R.id.spinnerHemocentro);
        editData = findViewById(R.id.editData);
        editHora = findViewById(R.id.editHora);
        btnAgendar = findViewById(R.id.btnAgendar);
        btnVoltar = findViewById(R.id.btnVoltar);

        calendar = Calendar.getInstance();

        hemocentros = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerHemocentro.setAdapter(adapter);

        carregarHemocentros();

        editData.setOnClickListener(v -> mostrarDatePicker());
        editHora.setOnClickListener(v -> mostrarTimePicker());
        btnAgendar.setOnClickListener(v -> agendar());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarHemocentros() {
        apiService.listarHemocentros().enqueue(new Callback<List<HemocentroResponse>>() {
            @Override
            public void onResponse(Call<List<HemocentroResponse>> call, Response<List<HemocentroResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hemocentros = response.body();
                    List<String> nomes = new ArrayList<>();
                    for (HemocentroResponse h : hemocentros) {
                        nomes.add(h.getNome());
                    }
                    adapter.clear();
                    adapter.addAll(nomes);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<HemocentroResponse>> call, Throwable t) {
                Toast.makeText(AgendamentoActivity.this,
                        "Erro ao carregar hemocentros", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
            (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editData.setText(String.format(Locale.US, "%02d/%02d/%04d", dayOfMonth, month + 1, year));
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void mostrarTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            (view, hourOfDay, minute) -> {
                editHora.setText(String.format(Locale.US, "%02d:%02d", hourOfDay, minute));
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true);
        timePickerDialog.show();
    }

    private void agendar() {
        String data = editData.getText().toString().trim();
        String hora = editHora.getText().toString().trim();

        if (data.isEmpty()) {
            Toast.makeText(this, "Selecione uma data", Toast.LENGTH_SHORT).show();
            return;
        }
        if (hora.isEmpty()) {
            Toast.makeText(this, "Selecione uma hora", Toast.LENGTH_SHORT).show();
            return;
        }

        int posicao = spinnerHemocentro.getSelectedItemPosition();
        if (posicao < 0 || posicao >= hemocentros.size()) {
            Toast.makeText(this, "Selecione um hemocentro", Toast.LENGTH_SHORT).show();
            return;
        }

        btnAgendar.setEnabled(false);
        String hemocentroId = hemocentros.get(posicao).getId();

        Map<String, String> body = new HashMap<>();
        body.put("data", data);
        body.put("hora", hora);
        body.put("hemocentroId", hemocentroId);

        apiService.criarAgendamento(body).enqueue(new Callback<AgendamentoResponse>() {
            @Override
            public void onResponse(Call<AgendamentoResponse> call, Response<AgendamentoResponse> response) {
                btnAgendar.setEnabled(true);
                if (response.isSuccessful()) {
                    Toast.makeText(AgendamentoActivity.this,
                            "Agendamento realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String erro = "Erro ao agendar";
                    try {
                        if (response.errorBody() != null) {
                            ErrorResponse err = new com.google.gson.Gson().fromJson(
                                    response.errorBody().charStream(), ErrorResponse.class);
                            if (err.getErro() != null) erro = err.getErro();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(AgendamentoActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AgendamentoResponse> call, Throwable t) {
                btnAgendar.setEnabled(true);
                Toast.makeText(AgendamentoActivity.this,
                        "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irParaLogin() {
        Intent intent = new Intent(AgendamentoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
