package com.example.prueba.CheckMobile.Vehiculo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 22-jun-17.
 */

public interface VehiculoClienteService {

    @POST("cteVehiculo")
    Call<String> updatesVehiculos();
}
