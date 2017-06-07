package com.example.prueba.CheckMobile.VehiculoModelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 30-may-17.
 */

public class ModeloResponse {

    @SerializedName("data")
    private ArrayList<Modelo> modelos;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "ModeloResponse{" +
                "modelos=" + modelos +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(ArrayList<Modelo> modelos) {
        this.modelos = modelos;
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
