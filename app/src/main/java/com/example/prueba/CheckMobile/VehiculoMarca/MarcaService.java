package com.example.prueba.CheckMobile.VehiculoMarca;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 22-may-17.
 */

public interface MarcaService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("marca")
    Call<Marca> getMarcas();


    @PUT("marca")
    Call<String> setMarca(@Body ArrayList<Marca> marcas);
}
