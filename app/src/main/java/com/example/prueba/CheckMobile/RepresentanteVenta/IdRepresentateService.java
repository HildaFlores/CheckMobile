package com.example.prueba.CheckMobile.RepresentanteVenta;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 23-ago-17.
 */

public interface IdRepresentateService {

    @POST("idMecanico")
    Call<String> getIdMecanico();

}




