package com.example.prueba.CheckMobile.VehiculoTraccion;

import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 22-may-17.
 */

public interface TraccionService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("traccion")
    Call<Traccion> getTracciones();
}
