package com.example.prueba.CheckMobile.Usuario;

import com.example.prueba.CheckMobile.Vehiculo.Vehiculo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 02-jul-17.
 */

public class UsuarioResponse {
    @SerializedName("data")
    private ArrayList<Usuario> usuarios;
    private String responseCode;
    private String message;
    private int rows;


    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
