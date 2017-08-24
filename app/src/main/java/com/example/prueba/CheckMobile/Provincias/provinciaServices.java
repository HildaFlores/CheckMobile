package com.example.prueba.CheckMobile.Provincias;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 11-ago-17.
 */

public interface provinciaServices {

    @POST("provincia")
    Call<ArrayList<provincias>> getProvincias();
}
