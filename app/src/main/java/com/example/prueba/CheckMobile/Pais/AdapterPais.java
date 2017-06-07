package com.example.prueba.CheckMobile.Pais;

import com.example.prueba.CheckMobile.Cliente.ClienteService;
import com.example.prueba.CheckMobile.MainActivity;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 31-may-17.
 */

public class AdapterPais {


    public static PaisService apiService;

    public static PaisService getApiService() {

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
            apiService = retrofit.create(com.example.prueba.CheckMobile.Pais.PaisService.class);

        }

        return apiService;
    }
}


