package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import br.edu.unifaj.cc.mobile.logincomnavegacao.R;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiClient;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ApiService;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.DoadorResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.ErrorResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.api.LoginResponse;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.FatorRh;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.enums.TipoSanguineo;
import br.edu.unifaj.cc.mobile.logincomnavegacao.model.user.Doador;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.PrefsManager;
import br.edu.unifaj.cc.mobile.logincomnavegacao.util.ValidacaoUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editCpf;
    private Spinner spinnerTipoSanguineo;
    private Button btnCadastrar;
    private Button btnVoltar;
    private PrefsManager prefsManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        prefsManager = new PrefsManager(this);
        apiService = ApiClient.getInstance(this);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        editCpf = findViewById(R.id.editCpf);
        spinnerTipoSanguineo = findViewById(R.id.spinnerTipoSanguineo);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        String[] tiposSanguineos = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposSanguineos) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(getResources().getColor(android.R.color.white, null));
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerTipoSanguineo.setAdapter(adapter);

        btnCadastrar.setOnClickListener(v -> cadastrarDoador());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void cadastrarDoador() {
        String nome = editNome.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim();
        String tipoCompleto = spinnerTipoSanguineo.getSelectedItem().toString();

        String validacao = ValidacaoUtils.validarCamposObrigatorios(nome, email, senha, cpf, tipoCompleto);
        if (validacao != null) {
            Toast.makeText(this, validacao, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarEmail(email)) {
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarSenha(senha)) {
            Toast.makeText(this, "Senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarCpf(cpf)) {
            Toast.makeText(this, "CPF invalido", Toast.LENGTH_SHORT).show();
            return;
        }

        btnCadastrar.setEnabled(false);

        String tipoStr = tipoCompleto.substring(0, tipoCompleto.length() - 1);
        String fatorStr = tipoCompleto.substring(tipoCompleto.length() - 1);

        Map<String, String> body = new HashMap<>();
        body.put("nome", nome);
        body.put("email", email);
        body.put("senha", senha);
        body.put("cpf", cpf);
        body.put("tipoSanguineo", tipoStr);
        body.put("fatorRh", fatorStr);

        apiService.cadastrar(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnCadastrar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    ApiClient.salvarToken(CadastroActivity.this, response.body().getToken());
                    salvarDoadorLocal(response.body().getDoador());
                    Toast.makeText(CadastroActivity.this,
                            "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String erro = "Erro ao cadastrar";
                    try {
                        if (response.errorBody() != null) {
                            ErrorResponse err = new com.google.gson.Gson().fromJson(
                                    response.errorBody().charStream(), ErrorResponse.class);
                            if (err.getErro() != null) erro = err.getErro();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnCadastrar.setEnabled(true);
                Toast.makeText(CadastroActivity.this,
                        "Erro de conexao: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarDoadorLocal(DoadorResponse dto) {
        Doador doador = new Doador();
        doador.setNome(dto.getNome());
        doador.setEmail(dto.getEmail());
        doador.setCpf(dto.getCpf());
        if (dto.getTipoSanguineo() != null) {
            doador.setTipoSanguineo(TipoSanguineo.fromValor(dto.getTipoSanguineo()));
        }
        if (dto.getFatorRh() != null) {
            doador.setFatorRh(FatorRh.fromValor(dto.getFatorRh()));
        }
        prefsManager.salvarDoador(doador);
    }
}
