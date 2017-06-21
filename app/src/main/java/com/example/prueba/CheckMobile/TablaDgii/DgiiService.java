package com.example.prueba.CheckMobile.TablaDgii;

import com.example.prueba.CheckMobile.Cliente.Cliente;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 22-may-17.
 */

public interface DgiiService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("dgii")
    Call<TablaDgii> getCliente();

}
