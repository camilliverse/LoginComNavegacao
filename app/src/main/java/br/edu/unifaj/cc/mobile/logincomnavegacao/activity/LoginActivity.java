package br.edu.unifaj.cc.mobile.logincomnavegacao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editSenha;
    private Button btnEntrar;
    private Button btnCadastrar;
    private PrefsManager prefsManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        prefsManager = new PrefsManager(this);
        apiService = ApiClient.getInstance(this);

        if (ApiClient.getToken(this) != null) {
            irParaHome();
            return;
        }

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnEntrar.setOnClickListener(v -> validarLogin());
        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }

    private void validarLogin() {
        String email = editEmail.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ValidacaoUtils.validarEmail(email)) {
            Toast.makeText(this, "Email invalido", Toast.LENGTH_SHORT).show();
            return;
        }

        btnEntrar.setEnabled(false);

        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("senha", senha);

        apiService.login(body).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnEntrar.setEnabled(true);
                if (response.isSuccessful() && response.body() != null) {
                    ApiClient.salvarToken(LoginActivity.this, response.body().getToken());
                    salvarDoadorLocal(response.body().getDoador());
                    irParaHome();
                } else {
                    String erro = "Email ou senha incorretos";
                    try {
                        if (response.errorBody() != null) {
                            ErrorResponse err = new com.google.gson.Gson().fromJson(
                                    response.errorBody().charStream(), ErrorResponse.class);
                            if (err.getErro() != null) erro = err.getErro();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(LoginActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnEntrar.setEnabled(true);
                Toast.makeText(LoginActivity.this,
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

    private void irParaHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
