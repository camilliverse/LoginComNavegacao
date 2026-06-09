package br.edu.unifaj.cc.mobile.logincomnavegacao.api;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body Map<String, String> body);

    @POST("api/auth/cadastro")
    Call<LoginResponse> cadastrar(@Body Map<String, String> body);

    @GET("api/doador/perfil")
    Call<DoadorResponse> getPerfil();

    @GET("api/bolsas")
    Call<List<BolsaSangueResponse>> listarBolsas();

    @POST("api/bolsas")
    Call<BolsaSangueResponse> criarBolsa(@Body Map<String, Object> body);

    @GET("api/agendamentos")
    Call<List<AgendamentoResponse>> listarAgendamentos();

    @POST("api/agendamentos")
    Call<AgendamentoResponse> criarAgendamento(@Body Map<String, String> body);

    @PUT("api/agendamentos/{id}/cancelar")
    Call<Void> cancelarAgendamento(@Path("id") String id);

    @GET("api/hemocentros")
    Call<List<HemocentroResponse>> listarHemocentros();
}
