package com.example.prueba.CheckMobile.Cliente;

import com.example.prueba.CheckMobile.MainActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Prueba on 19-may-17.
 */


public class AdapterCliente {

    public static com.example.prueba.CheckMobile.Cliente.ClienteService apiService;

    public static com.example.prueba.CheckMobile.Cliente.ClienteService getApiService(){

    //Creating the interceptor , and setting the log level
        MainActivity main = new MainActivity();

        OkHttpClient.Builder httpClient =  main.httpCliente();
        //add logging as last interceptor

        if (apiService == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(main.getBaseUrl())
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(httpClient.build()) //<- using the log level
                                .build();
            apiService = retrofit.create(com.example.prueba.CheckMobile.Cliente.ClienteService.class);

        }

        return apiService;
    }



}
