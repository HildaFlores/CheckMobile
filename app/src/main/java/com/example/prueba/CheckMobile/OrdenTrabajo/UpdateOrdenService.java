package com.example.prueba.CheckMobile.OrdenTrabajo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 10-jul-17.
 */

public interface UpdateOrdenService {

    @POST("anularPedido")
    Call<String> setAnulacion();


    @PUT("actualizarPedido")
    Call<String> setupdateOrden(@Body ArrayList<OrdenTrabajoEnc> ordenes);

}
