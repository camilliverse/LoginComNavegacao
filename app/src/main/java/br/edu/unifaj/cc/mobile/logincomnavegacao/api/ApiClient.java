package br.edu.unifaj.cc.mobile.logincomnavegacao.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.249:8080/";
    private static final String PREFS_NAME = "PrefDoacaoSangue";
    private static final String KEY_TOKEN = "api_token";

    private static Retrofit retrofit;
    private static ApiService apiService;

    public static ApiService getInstance(Context context) {
        if (apiService == null) {
            retrofit = criarRetrofit(context);
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    private static Retrofit criarRetrofit(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AuthInterceptor(context))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void salvarToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null);
    }

    public static void limparToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_TOKEN).apply();
    }

    private static class AuthInterceptor implements Interceptor {
        private final Context context;

        AuthInterceptor(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            String token = getToken(context);
            Request original = chain.request();

            if (token != null && !original.url().encodedPath().contains("/api/auth/")
                    && !original.url().encodedPath().contains("/api/hemocentros")) {
                Request.Builder builder = original.newBuilder()
                        .header("Authorization", "Bearer " + token);
                return chain.proceed(builder.build());
            }

            return chain.proceed(original);
        }
    }
}
