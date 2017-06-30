package com.example.prueba.CheckMobile.VehiculoDocumento;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo.JSON2;
import static com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo.apiService;

/**
 * Created by Prueba on 27-jun-17.
 */

public class AdapterVehiculoDocumento {

    public static VehiculoDocumentoService service;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static VehiculoDocumentoService getService()
    {
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
        service = retrofit.create(com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumentoService.class);
        return service;
    }

    public static VehiculoDocumentoService getDocumentos(String parametro, String valor)
    {
        service = null;
        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro, valor);

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

        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient2.build()) //<- using the log level
                    .build();

            service = retrofit.create(com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumentoService.class);

        }

        return service;
    }

}
