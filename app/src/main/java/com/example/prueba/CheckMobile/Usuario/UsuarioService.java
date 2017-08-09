package com.example.prueba.CheckMobile.Usuario;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Prueba on 22-may-17.
 */

public interface UsuarioService {

    @POST("admin-users")
    Call<Usuario> getUsuario();

    @POST("user-supervisor")
    Call<Usuario> getPersonal();


    @POST("validacion-clave")
    Call<Usuario> getValidacion();


}
