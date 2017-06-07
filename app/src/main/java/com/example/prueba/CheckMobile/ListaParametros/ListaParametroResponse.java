package com.example.prueba.CheckMobile.ListaParametros;

import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 05-jun-17.
 */

public class ListaParametroResponse {

    @SerializedName("data")
    private ArrayList<ListaParametros> listaParametros;
    private String responseCode;
    private String message;
    private int rows;


    @Override
    public String toString() {
        return "ListaParametroResponse{" +
                "listaParametros=" + listaParametros +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<ListaParametros> getListaParametros() {
        return listaParametros;
    }

    public void setListaParametros(ArrayList<ListaParametros> listaParametros) {
        this.listaParametros = listaParametros;
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
