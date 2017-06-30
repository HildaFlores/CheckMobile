package com.example.prueba.CheckMobile.Inspeccion;

import com.example.prueba.CheckMobile.Pais.Pais;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 25-jun-17.
 */

public class InspeccionVehiculoResponse {

    @SerializedName("data")
    private ArrayList<InspeccionVehiculo> inspecciones;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "InspeccionVehiculoResponse{" +
                "inspecciones=" + inspecciones +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<InspeccionVehiculo> getInspecciones() {
        return inspecciones;
    }

    public void setInspecciones(ArrayList<InspeccionVehiculo> inspecciones) {
        this.inspecciones = inspecciones;
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
