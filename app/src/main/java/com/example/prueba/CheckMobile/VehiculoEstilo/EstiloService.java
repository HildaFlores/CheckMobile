package com.example.prueba.CheckMobile.VehiculoEstilo;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import static com.example.prueba.CheckMobile.R.string.id_modelo;

/**
 * Created by Prueba on 22-may-17.
 */

public interface EstiloService {

//   @FormUrlEncoded
//    @POST("estilos")
//   Call<ArrayList<Estilo>> getEstilos(@Field("id_modelo") String id_modelo);


    @POST("estilos")
    Call<ArrayList<Estilo>> getEstilos();


    @PUT("estilos")
    Call<String> setEstilo(@Body ArrayList<Estilo> estilos);
//
//    @FormUrlEncoded
//    @POST("estilos")
//    void getEstilos(@Field("id_modelo") String id_modelo, Callback<ArrayList<Estilo>> callback);

}
