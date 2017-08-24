package com.example.prueba.CheckMobile.Provincias;

import com.example.prueba.CheckMobile.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 11-ago-17.
 */

public class AdapterProvincias {
    public static provinciaServices apiService;

    public static provinciaServices getApiService()
    {
        MainActivity main = new MainActivity();

        OkHttpClient.Builder httpClient2 = main.httpCliente();


        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient2.build()) //<- using the log level
                    .build();

            apiService = retrofit.create(com.example.prueba.CheckMobile.Provincias.provinciaServices.class);

        }
        return apiService;
    }

}
