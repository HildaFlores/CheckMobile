package com.example.prueba.CheckMobile.CondicionPago;

import com.example.prueba.CheckMobile.Pais.Pais;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 23-may-17.
 */

public interface CondicionService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("condicion")
    Call<CondicionPago> getCondicionPagos();
}
