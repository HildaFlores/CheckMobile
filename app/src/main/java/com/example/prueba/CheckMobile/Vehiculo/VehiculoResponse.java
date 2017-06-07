package com.example.prueba.CheckMobile.Vehiculo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 28-may-17.
 */

public class VehiculoResponse {
    @SerializedName("data")
    private ArrayList<Vehiculo> vehiculos;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "VehiculoResponse{" +
                "vehiculos=" + vehiculos +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(ArrayList<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
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
