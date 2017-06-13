package com.example.prueba.CheckMobile.ProductoServicio;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Prueba on 02-jun-17.
 */

public interface ProductoService {

    @Headers("Cache-Control: max-age=1") //eso es para limpiar la cache
    @POST("productoEnc")
    Call<ProductoServicio> getListaProductos();


}
