package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.adapter.AgendamentoAdapter;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.AgendamentoResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiClient;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiService;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Agendamento;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Endereco;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.entity.Hemocentro;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.StatusAgendamento;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAgendamentosActivity extends AppCompatActivity {

    private RecyclerView recyclerAgendamentos;
    private TextView txtSemAgendamentos;
    private Button btnVoltar;
    private ApiService apiService;
    private AgendamentoAdapter adapter;
    private List<Agendamento> agendamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agendamentos);

        apiService = ApiClient.getInstance(this);

        if (ApiClient.getToken(this) == null) {
            irParaLogin();
            return;
        }

        recyclerAgendamentos = findViewById(R.id.recyclerAgendamentos);
        txtSemAgendamentos = findViewById(R.id.txtSemAgendamentos);
        btnVoltar = findViewById(R.id.btnVoltar);

        recyclerAgendamentos.setLayoutManager(new LinearLayoutManager(this));

        agendamentos = new ArrayList<>();
        adapter = new AgendamentoAdapter(agendamentos, new AgendamentoAdapter.OnAgendamentoClickListener() {
            @Override
            public void onCancelarClick(Agendamento agendamento, int position) {
                confirmarCancelamento(agendamento, position);
            }
        });
        recyclerAgendamentos.setAdapter(adapter);

        carregarAgendamentos();

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarAgendamentos() {
        apiService.listarAgendamentos().enqueue(new Callback<List<AgendamentoResponse>>() {
            @Override
            public void onResponse(Call<List<AgendamentoResponse>> call, Response<List<AgendamentoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    agendamentos.clear();
                    for (AgendamentoResponse dto : response.body()) {
                        Agendamento a = new Agendamento();
                        a.setId(dto.getId());
                        a.setData(dto.getData());
                        a.setHora(dto.getHora());
                        if (dto.getStatus() != null) {
                            for (StatusAgendamento s : StatusAgendamento.values()) {
                                if (s.name().equalsIgnoreCase(dto.getStatus())) {
                                    a.setStatus(s);
                                    break;
                                }
                            }
                        }
                        Hemocentro h = new Hemocentro();
                        h.setNome(dto.getHemocentroNome());
                        h.setTelefone(dto.getHemocentroTelefone());
                        Endereco end = new Endereco();
                        end.setEnderecoCompleto(dto.getHemocentroEndereco());
                        h.setEndereco(end);
                        a.setHemocentro(h);
                        agendamentos.add(a);
                    }
                    adapter.notifyDataSetChanged();
                    atualizarVisibilidade();
                }
            }

            @Override
            public void onFailure(Call<List<AgendamentoResponse>> call, Throwable t) {
                Toast.makeText(ListaAgendamentosActivity.this,
                        "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmarCancelamento(Agendamento agendamento, int position) {
        new AlertDialog.Builder(this)
            .setTitle("Cancelar Agendamento")
            .setMessage("Tem certeza que deseja cancelar este agendamento?")
            .setPositiveButton("Sim", (dialog, which) -> {
                apiService.cancelarAgendamento(agendamento.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ListaAgendamentosActivity.this,
                                    "Agendamento cancelado", Toast.LENGTH_SHORT).show();
                            carregarAgendamentos();
                        } else {
                            Toast.makeText(ListaAgendamentosActivity.this,
                                    "Erro ao cancelar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAgendamentosActivity.this,
                                "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            })
            .setNegativeButton("Nao", null)
            .show();
    }

    private void atualizarVisibilidade() {
        txtSemAgendamentos.setVisibility(agendamentos.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerAgendamentos.setVisibility(agendamentos.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private void irParaLogin() {
        Intent intent = new Intent(ListaAgendamentosActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
