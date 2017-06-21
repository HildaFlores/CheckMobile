package com.example.prueba.CheckMobile.Cliente;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Prueba on 19-may-17.
 */

public interface ClienteService {

   // @FormUrlEncoded
     @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("clientes")
     Call<Cliente> getClientes();

   @PUT("clientes")
    Call<String> insertClientes(@Body ArrayList<Cliente> clientes);
}
