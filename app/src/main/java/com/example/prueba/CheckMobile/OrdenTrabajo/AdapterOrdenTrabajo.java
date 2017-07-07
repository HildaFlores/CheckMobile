package com.example.prueba.CheckMobile.OrdenTrabajo;

import com.example.prueba.CheckMobile.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 07-jul-17.
 */

public class AdapterOrdenTrabajo {

    public static OrdenTrabajoEncService apiService;

    public static OrdenTrabajoEncService setApiService()
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

        apiService = retrofit.create(OrdenTrabajoEncService.class);
        return apiService;
    }

}
