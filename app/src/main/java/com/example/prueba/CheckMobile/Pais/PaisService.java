package com.example.prueba.CheckMobile.Pais;

import com.example.prueba.CheckMobile.Cliente.Cliente;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 31-may-17.
 */

public interface PaisService {


    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("pais")
    Call<Pais> getPaises();


}
