package com.example.prueba.CheckMobile.Inspeccion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 04-jul-17.
 */

public interface updateInspeccionService {

    @POST("anulacionInspeccion")
    Call<String> setUpdate();


    @POST("convert")
    Call<String> setConvert();

    @PUT("updateInspeccion")
    Call<String> setupdateInspeccion(@Body ArrayList<InspeccionVehiculo> inspeccion);
}
