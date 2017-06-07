package com.example.prueba.CheckMobile.Vehiculo;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 22-may-17.
 */

public interface VehiculoService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("vehiculoDet")
    Call<Vehiculo> getVehiculos();
}
