package com.example.prueba.CheckMobile.VehiculoMarca;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 22-may-17.
 */

public class AdapterMarca {

    public static MarcaService apiService;

    public static MarcaService getApiService() {

        apiService = null;
        MainActivity main = new MainActivity();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = main.httpCliente();


        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.VehiculoMarca.MarcaService.class);

        }
        return apiService;
    }

    public static MarcaService insertarMarca() {

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


        apiService = retrofit.create(com.example.prueba.CheckMobile.VehiculoMarca.MarcaService.class);

        return apiService;
    }
}
