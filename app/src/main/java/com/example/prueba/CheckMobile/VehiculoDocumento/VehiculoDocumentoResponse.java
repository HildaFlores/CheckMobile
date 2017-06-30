package com.example.prueba.CheckMobile.VehiculoDocumento;

import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 27-jun-17.
 */

public class VehiculoDocumentoResponse {

    @SerializedName("data")
    private ArrayList<VehiculoDocumento> VehiculoDocumento;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "VehiculoDocumentoResponse{" +
                "VehiculoDocumento=" + VehiculoDocumento +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento> getVehiculoDocumento() {
        return VehiculoDocumento;
    }

    public void setVehiculoDocumento(ArrayList<com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento> vehiculoDocumento) {
        VehiculoDocumento = vehiculoDocumento;
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
