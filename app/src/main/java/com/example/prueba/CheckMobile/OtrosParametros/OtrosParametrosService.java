package com.example.prueba.CheckMobile.OtrosParametros;

import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesorios;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 09-jun-17.
 */

public interface OtrosParametrosService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("parametrosDetOtros")
    Call<OtrosParametros> getOtrosParametros();


}
