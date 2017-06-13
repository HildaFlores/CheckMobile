package com.example.prueba.CheckMobile.OtrosParametros;

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

import static com.example.prueba.CheckMobile.AccesoriosParametros.AdapterAccesorios.serviceAccesorios;

/**
 * Created by Prueba on 09-jun-17.
 */

public class AdapterOtrosParametros {


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static OtrosParametrosService service;

    //**************Lista de accesorios de vehiculos **********************//

    public static OtrosParametrosService getServiceOtros(String parametro, String valor) {
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

            service = retrofit.create(OtrosParametrosService.class);

        }
        return service;
    }

}
