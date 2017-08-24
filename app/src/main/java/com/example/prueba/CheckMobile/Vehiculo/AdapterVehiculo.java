package com.example.prueba.CheckMobile.Vehiculo;


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
 * Created by Prueba on 22-may-17.
 */

public class AdapterVehiculo {

    public static VehiculoService apiService;
    public static VehiculoClienteService updateService;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType JSON2
            = MediaType.parse("text/plain");


    public static VehiculoService getApiService() {
        apiService = null;
        MainActivity main = new MainActivity();
        OkHttpClient.Builder httpClient = main.httpCliente();

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.Vehiculo.VehiculoService.class);

        }
        return apiService;
    }


    public static VehiculoService getChasis(String parametro, String valor) {

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

                RequestBody requestBody = RequestBody.create(JSON2, parametroFormated);

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

            apiService = retrofit.create(com.example.prueba.CheckMobile.Vehiculo.VehiculoService.class);

        }

        return apiService;

    }


    public static VehiculoService setVehiculo() {

        MainActivity main = new MainActivity();
        OkHttpClient.Builder httpClient = main.httpCliente();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(main.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();


        apiService = retrofit.create(com.example.prueba.CheckMobile.Vehiculo.VehiculoService.class);

        return apiService;
    }
    public static VehiculoClienteService setUpdateService(final String parametro)
    {
        MainActivity main = new MainActivity();
        updateService = null;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient2 = main.httpCliente();
        httpClient2.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                RequestBody requestBody = RequestBody.create(JSON, parametro);

                Request request = original.newBuilder()
                        .post(requestBody)
                        .build();

                return chain.proceed(request);
            }
        });

        if (updateService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient2.build()) //<- using the log level
                    .build();

            updateService = retrofit.create(com.example.prueba.CheckMobile.Vehiculo.VehiculoClienteService.class);

        }

        return updateService;
    }
}
