package com.example.prueba.CheckMobile.VehiculoEstilo;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Prueba on 29-may-17.
 */

public class EstiloResponse {

    @SerializedName("data")
   private ArrayList<Estilo> estilos;
    private String responseCode;
    private String message;
    private int rows;


    @Override
    public String toString() {
        return "MarcaResponse{" +
                "estilos=" + estilos +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<Estilo> getEstilos() {
        return estilos;
    }

    public void setEstilos(ArrayList<Estilo> estilos) {
        this.estilos = estilos;
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
