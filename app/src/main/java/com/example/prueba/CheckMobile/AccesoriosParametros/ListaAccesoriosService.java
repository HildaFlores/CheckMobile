package com.example.prueba.CheckMobile.AccesoriosParametros;

import com.example.prueba.CheckMobile.LucesParametros.ListaLuces;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 08-jun-17.
 */

public interface ListaAccesoriosService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("parametrosDet")
    Call<ListaAccesorios> getListaAccesorios();

}
