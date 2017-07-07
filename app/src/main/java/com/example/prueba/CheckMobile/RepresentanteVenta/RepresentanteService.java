package com.example.prueba.CheckMobile.RepresentanteVenta;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 06-jul-17.
 */

public interface RepresentanteService {

    @POST("mecanico")
    Call<RepresentanteVenta> getMecanico();



}
