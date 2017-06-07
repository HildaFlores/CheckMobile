package com.example.prueba.CheckMobile.Combustible;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 31-may-17.
 */

public interface CombustibleService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("combustible")
    Call<Combustible> getCombustibles();
}
