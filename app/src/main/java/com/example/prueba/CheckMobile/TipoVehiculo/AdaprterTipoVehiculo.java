package com.example.prueba.CheckMobile.TipoVehiculo;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.VehiculoMarca.MarcaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 23-may-17.
 */

public class AdaprterTipoVehiculo {

    public static TipoVehiculoService apiService;

    public static TipoVehiculoService getApiService() {

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
            apiService = retrofit.create(com.example.prueba.CheckMobile.TipoVehiculo.TipoVehiculoService.class);

        }
        return apiService;
    }
}
