package com.example.prueba.CheckMobile.Inspeccion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 21-jun-17.
 */

public interface InspeccionDetalleService {


    @PUT("inspeccionDet")
    Call<String> insertInspeccionDetalle(@Body ArrayList<InspeccionVehiculoDetalle> inspeccion);

    @POST("inspeccionDet")
    Call<InspeccionVehiculoDetalle> getInspeccionDetalle();

}
