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
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiClient;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiService;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.BolsaSangueResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.BolsaSangue;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.FatorRh;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.StatusBolsa;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.TipoSanguineo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoActivity extends AppCompatActivity {

    private RecyclerView recyclerBolsas;
    private Button btnVoltar;
    private ApiService apiService;
    private BolsaSangueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        apiService = ApiClient.getInstance(this);

        if (ApiClient.getToken(this) == null) {
            irParaLogin();
            return;
        }

        recyclerBolsas = findViewById(R.id.recyclerDoacoes);
        btnVoltar = findViewById(R.id.btnVoltar);

        recyclerBolsas.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BolsaSangueAdapter(new ArrayList<>());
        recyclerBolsas.setAdapter(adapter);

        carregarBolsas();

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarBolsas() {
        apiService.listarBolsas().enqueue(new Callback<List<BolsaSangueResponse>>() {
            @Override
            public void onResponse(Call<List<BolsaSangueResponse>> call, Response<List<BolsaSangueResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BolsaSangue> bolsas = new ArrayList<>();
                    for (BolsaSangueResponse dto : response.body()) {
                        BolsaSangue b = new BolsaSangue();
                        b.setCodigo(dto.getCodigo());
                        b.setDataColeta(dto.getDataColeta());
                        b.setDataValidade(dto.getDataValidade());
                        b.setVolumeMl(dto.getVolumeMl());
                        if (dto.getStatus() != null) {
                            for (StatusBolsa s : StatusBolsa.values()) {
                                if (s.name().equalsIgnoreCase(dto.getStatus())) {
                                    b.setStatus(s);
                                    break;
                                }
                            }
                        }
                        String[] tipoParts = dto.getTipoCompleto() != null
                                ? dto.getTipoCompleto().split("(?<=.)(?=[+-])") : new String[2];
                        if (tipoParts.length >= 2) {
                            b.setTipoSanguineo(TipoSanguineo.fromValor(tipoParts[0]));
                            b.setFatorRh(FatorRh.fromValor(tipoParts[1]));
                        }
                        bolsas.add(b);
                    }
                    adapter = new BolsaSangueAdapter(bolsas);
                    recyclerBolsas.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<BolsaSangueResponse>> call, Throwable t) {
                Toast.makeText(HistoricoActivity.this,
                        "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irParaLogin() {
        Intent intent = new Intent(HistoricoActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
