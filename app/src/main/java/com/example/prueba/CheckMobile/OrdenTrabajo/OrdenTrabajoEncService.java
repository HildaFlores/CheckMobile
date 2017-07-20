package com.example.prueba.CheckMobile.OrdenTrabajo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 07-jul-17.
 */

public interface OrdenTrabajoEncService {


    @PUT("pedidoEnc")
    Call<String> setOrdenTrabajo(@Body ArrayList<OrdenTrabajoEnc> ordenes);

    @POST("pedidoEnc")
    Call<OrdenTrabajoEnc> getOrdenTrabajo();
}
