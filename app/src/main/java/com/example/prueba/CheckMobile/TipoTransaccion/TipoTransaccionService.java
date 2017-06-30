package com.example.prueba.CheckMobile.TipoTransaccion;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 22-jun-17.
 */

public interface TipoTransaccionService {


    @POST("tipoTrans")
    Call<ArrayList<TipoTransaccion>> getTipoTransaccion();
}
