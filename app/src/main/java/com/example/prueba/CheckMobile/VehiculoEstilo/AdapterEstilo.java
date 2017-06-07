package com.example.prueba.CheckMobile.VehiculoEstilo;


import com.example.prueba.CheckMobile.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Prueba on 22-may-17.
 */

public class AdapterEstilo {

    public static EstiloService apiService;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


//
//
//    public static RestAdapter Connect (){
//        RequestInterceptor requestInterceptor = new RequestInterceptor() {
//            @Override
//            public void intercept(RequestFacade request) {
////                request.addHeader("User-Agent", "Your-App-Name");
//                request.addHeader("Accept", "application/json");
//                request.addHeader("Content-Type", "application/json");
//            }
//        };
//       // MainActivity main = new MainActivity();
//        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.101:4567")
//                .setRequestInterceptor(requestInterceptor)
//                .build();
//        return restAdapter;
//    }


    public static EstiloService getApiService() {

        MainActivity main = new MainActivity();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient2 = main.httpCliente();
        httpClient2.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                RequestBody requestBody = RequestBody.create(JSON, "{'id_modelo' : '2'}");

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

            apiService = retrofit.create(com.example.prueba.CheckMobile.VehiculoEstilo.EstiloService.class);

        }
        return apiService;
    }
    }



