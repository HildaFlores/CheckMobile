package com.example.prueba.CheckMobile.Inspeccion;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 04-jul-17.
 */

public interface updateInspeccionService {

    @POST("anulacionInspeccion")
    Call<String> setUpdate();


    @POST("convert")
    Call<String> setConvert();

}
