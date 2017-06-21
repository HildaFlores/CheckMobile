package com.example.prueba.CheckMobile.TablaDgii;

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

public class AdapterDgii {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static com.example.prueba.CheckMobile.TablaDgii.DgiiService apiService;

    public static com.example.prueba.CheckMobile.TablaDgii.DgiiService getApiService(String parametro, String valor)
    {

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


            apiService = retrofit.create(com.example.prueba.CheckMobile.TablaDgii.DgiiService.class);
        }

        return apiService;


    }

}
