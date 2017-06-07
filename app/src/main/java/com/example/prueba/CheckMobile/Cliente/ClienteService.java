package com.example.prueba.CheckMobile.Cliente;


import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 19-may-17.
 */

public interface ClienteService {

   // @FormUrlEncoded
     @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("clientes")
     Call<Cliente> getClientes();

   /* void getAllCliente( Callback<Cliente> callback);*/
}
