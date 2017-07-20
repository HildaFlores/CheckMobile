package com.example.prueba.CheckMobile.Inspeccion;

import com.example.prueba.CheckMobile.Cliente.ClienteService;
import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.OrdenTrabajo.UpdateOrdenService;
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
 * Created by Prueba on 21-jun-17.
 */

public class AdapterInspeccion {

    public static InspeccionService apiService;
    public static InspeccionDetalleService detApiService;
    public static updateInspeccionService updateService;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType JSON2
            = MediaType.parse("text/plain");


    public static InspeccionService setInspeccionVehiculo() {

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


        apiService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.InspeccionService.class);

        return apiService;
    }

    public static InspeccionDetalleService setInspeccionVehiculoDet() {

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


        detApiService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.InspeccionDetalleService.class);

        return detApiService;
    }



    public static InspeccionService getApiService() {
        apiService = null;
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
            apiService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.InspeccionService.class);

        }

        return apiService;
    }

    public static InspeccionService getApiService(String parametro, String valor) {
        apiService = null;
        //Creating the interceptor , and setting the log level
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);
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
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient2.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.InspeccionService.class);

        }

        return apiService;
    }

    public static InspeccionDetalleService getInspeccionDetalle(String parametro, String valor) {
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);
        detApiService = null;
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

        if (detApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient2.build()) //<- using the log level
                    .build();


            detApiService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.InspeccionDetalleService.class);
        }

        return detApiService;
    }

    public static updateInspeccionService updateInspeccion(final String parametro)
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

            updateService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.updateInspeccionService.class);

        }
        return updateService;

    }

    public static updateInspeccionService convertInspeccion(final String parametro)
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

            updateService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.updateInspeccionService.class);

        }
        return updateService;

    }


    public static updateInspeccionService setUpdateInspeccionVeh() {

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


        updateService = retrofit.create(com.example.prueba.CheckMobile.Inspeccion.updateInspeccionService.class);

        return updateService;
    }
}
