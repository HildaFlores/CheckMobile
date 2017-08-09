package com.example.prueba.CheckMobile.Inspeccion;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 21-jun-17.
 */

public interface InspeccionService {

    @PUT("inspeccion")
    Call<String> insertInspeccion(@Body ArrayList<InspeccionVehiculo> inspeccion);

    @POST("inspeccionDetallada")
    Call<InspeccionVehiculo> getInspecciones();

    @POST("inspeccionFecha")
    Call<InspeccionVehiculo> getInspeccionesPorFecha();

    @POST("inspeccionVehiculo")
    Call<InspeccionVehiculo> getInspeccionesPorReferencia();


}
