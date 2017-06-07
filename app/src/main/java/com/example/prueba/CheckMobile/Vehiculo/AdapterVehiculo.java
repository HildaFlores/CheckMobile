package com.example.prueba.CheckMobile.Vehiculo;


import com.example.prueba.CheckMobile.MainActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 22-may-17.
 */

public class AdapterVehiculo {

public static VehiculoService apiService;

    public static VehiculoService getApiService(){
        MainActivity main = new MainActivity();
        OkHttpClient.Builder httpClient = main.httpCliente();

        if (apiService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(main.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) //<- using the log level
                    .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.Vehiculo.VehiculoService.class);

        }
        return apiService;
    }


 /*   public static VehiculoService getChasis()
    {


    }*/
}
