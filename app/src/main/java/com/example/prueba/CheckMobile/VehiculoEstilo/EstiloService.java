package com.example.prueba.CheckMobile.VehiculoEstilo;



import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
//
//    @FormUrlEncoded
//    @POST("estilos")
//    void getEstilos(@Field("id_modelo") String id_modelo, Callback<ArrayList<Estilo>> callback);

}
