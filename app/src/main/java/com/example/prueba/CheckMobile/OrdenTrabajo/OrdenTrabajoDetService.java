package com.example.prueba.CheckMobile.OrdenTrabajo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 08-jul-17.
 */

public interface OrdenTrabajoDetService {

    @PUT("pedidoDet")
    Call<String> setPedidoDetalle(@Body ArrayList<OrdenTrabajoDet> ordenes);

    @POST("pedidoDet")
    Call<OrdenTrabajoDet> getPedidoDetalle();


}
