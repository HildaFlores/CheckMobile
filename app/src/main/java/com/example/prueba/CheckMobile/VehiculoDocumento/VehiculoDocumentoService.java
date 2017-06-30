package com.example.prueba.CheckMobile.VehiculoDocumento;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 27-jun-17.
 */

public interface VehiculoDocumentoService {

    @PUT("documento")
    Call<String> setVehiculoDocumentos(@Body ArrayList<VehiculoDocumento> veh);


    @POST("documento")
    Call<VehiculoDocumento> getVehiculoDocumento();

}
