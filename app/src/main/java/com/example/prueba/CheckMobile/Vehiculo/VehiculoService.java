package com.example.prueba.CheckMobile.Vehiculo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import static android.R.attr.data;

/**
 * Created by Prueba on 22-may-17.
 */

public interface VehiculoService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("vehiculoDet")
    Call<Vehiculo> getVehiculos();

    @PUT("vehiculo")
    Call<String> insertVehiculos(@Body ArrayList<Vehiculo> veh);

}
