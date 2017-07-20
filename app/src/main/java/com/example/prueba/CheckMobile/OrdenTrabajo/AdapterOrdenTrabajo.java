package com.example.prueba.CheckMobile.OrdenTrabajo;

import com.example.prueba.CheckMobile.Inspeccion.updateInspeccionService;
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

import static com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion.updateService;

/**
 * Created by Prueba on 07-jul-17.
 */

public class AdapterOrdenTrabajo {

    public static OrdenTrabajoEncService apiService;
    public static OrdenTrabajoDetService apiServiceDetalle;
    public  static UpdateOrdenService updateService;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static OrdenTrabajoEncService setApiService() {

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

        apiService = retrofit.create(OrdenTrabajoEncService.class);
        return apiService;
    }

    public static OrdenTrabajoDetService insertOrdendDetalle() {

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
        apiServiceDetalle = retrofit.create(OrdenTrabajoDetService.class);
        return apiServiceDetalle;
    }

    public static OrdenTrabajoEncService getApiService(String parametro, String valor)
    {
        apiService = null;
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);


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
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEncService.class);

        }
        return apiService;
    }

    public static UpdateOrdenService updateOrdenTrabajo(final String parametro)
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

            updateService = retrofit.create(com.example.prueba.CheckMobile.OrdenTrabajo.UpdateOrdenService.class);

        }
        return updateService;

    }

    public static OrdenTrabajoDetService getOrdenDetalle(String parametro1, String valor1, String parametro2, String valor2)
    {
        apiServiceDetalle = null;
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro1, valor1, parametro2, valor2);


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

        if (apiServiceDetalle == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiServiceDetalle = retrofit.create(com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoDetService.class);

        }
        return apiServiceDetalle;
    }

    public static UpdateOrdenService updateOrdenTrabajo() {

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
        updateService = retrofit.create(UpdateOrdenService.class);
        return updateService;
    }

}
