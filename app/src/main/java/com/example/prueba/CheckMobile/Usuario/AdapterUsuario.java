package com.example.prueba.CheckMobile.Usuario;

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

import static com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo.JSON2;

/**
 * Created by Prueba on 22-may-17.
 */

public class AdapterUsuario {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static UsuarioService apiService;

    public static UsuarioService getUsuario(String parametro1, String valor1, String parametro2, String valor2) {

        MainActivity main = new MainActivity();
        final String parametroFormated = main.formatearParametro(parametro1, valor1, parametro2, valor2);
        apiService = null;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient2 = main.httpCliente();
        httpClient2.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                RequestBody requestBody = RequestBody.create(JSON2, parametroFormated);

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

            apiService = retrofit.create(com.example.prueba.CheckMobile.Usuario.UsuarioService.class);

        }

        return apiService;

    }


}
