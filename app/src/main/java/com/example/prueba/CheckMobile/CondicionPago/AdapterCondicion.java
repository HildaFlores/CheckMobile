package com.example.prueba.CheckMobile.CondicionPago;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.Pais.PaisService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 23-may-17.
 */

public class AdapterCondicion {

    public static CondicionService apiService;

    public static CondicionService getApiService() {

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
            apiService = retrofit.create(com.example.prueba.CheckMobile.CondicionPago.CondicionService.class);

        }

        return apiService;
    }
}
