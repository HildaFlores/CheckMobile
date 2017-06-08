package com.example.prueba.CheckMobile.Cliente;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 19-may-17.
 */


public class AdapterCliente {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static com.example.prueba.CheckMobile.Cliente.ClienteService apiService;

    public static com.example.prueba.CheckMobile.Cliente.ClienteService getApiService() {

        //Creating the interceptor , and setting the log level
        MainActivity main = new MainActivity();

        OkHttpClient.Builder httpClient = main.httpCliente();
        //add logging as last interceptor

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.Cliente.ClienteService.class);

        }

        return apiService;
    }

    public static ClienteService getFiltroClliente(String parametro, String valor) {
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);
        apiService = null;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient2 = main.httpCliente();
        httpClient2.addInterceptor(new Interceptor() {
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
                    .client(httpClient2.build()) //<- using the log level
                    .build();


            apiService = retrofit.create(com.example.prueba.CheckMobile.Cliente.ClienteService.class);
        }

        return apiService;


    }
}
