package com.example.prueba.CheckMobile.TipoVehiculo;

import com.example.prueba.CheckMobile.VehiculoMarca.Marca;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 23-may-17.
 */

public interface TipoVehiculoService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("tipoVeh")
    Call<TipoVehiculo> getTiposVeh();
}
