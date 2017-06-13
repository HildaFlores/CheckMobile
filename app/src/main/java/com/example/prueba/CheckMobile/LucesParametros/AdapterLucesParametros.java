package com.example.prueba.CheckMobile.LucesParametros;

import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesoriosService;
import com.example.prueba.CheckMobile.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 05-jun-17.
 */

public class AdapterLucesParametros {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static ListaLucesService apiService;

    //****************** Lista de luces encendidas en el vehiculo ******************//


    public static ListaLucesService getApiService(String parametro, String valor) {

        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = main.httpCliente();

        httpClient.addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request original = chain.request();

            RequestBody requestBody = RequestBody.create(JSON, parametroFormated);

            Request request = original.newBuilder()
                    .post(requestBody)
                    .build();

            return chain.proceed(request);
        }
    });

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(ListaLucesService.class);

        }
        return apiService;
    }


}
