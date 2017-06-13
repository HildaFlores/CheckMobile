package com.example.prueba.CheckMobile.LucesParametros;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 05-jun-17.
 */

public interface ListaLucesService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("parametrosDet")
    Call<ListaLuces> getListaLuces();

}
