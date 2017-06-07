package com.example.prueba.CheckMobile.TipoVehiculo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 29-may-17.
 */

public class TipoVehiculoResponse {

    @SerializedName("data")
    private ArrayList<TipoVehiculo> tipoVehiculos;
    private String responseCode;
    private String message;
    private int rows;


    @Override
    public String toString() {
        return "TipoVehiculoResponse{" +
                "tipoVehiculos=" + tipoVehiculos +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
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

    public ArrayList<TipoVehiculo> getTipoVehiculos() {
        return tipoVehiculos;
    }

    public void setTipoVehiculos(ArrayList<TipoVehiculo> tipoVehiculos) {
        this.tipoVehiculos = tipoVehiculos;
    }
}
