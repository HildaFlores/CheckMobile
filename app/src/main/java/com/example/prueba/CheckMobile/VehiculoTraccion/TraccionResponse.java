package com.example.prueba.CheckMobile.VehiculoTraccion;

import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 31-may-17.
 */

public class TraccionResponse {

    @SerializedName("data")
    private ArrayList<Traccion> tracciones;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "TraccionResponse{" +
                "tracciones=" + tracciones +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<Traccion> getTracciones() {
        return tracciones;
    }

    public void setTracciones(ArrayList<Traccion> tracciones) {
        this.tracciones = tracciones;
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
