package com.example.prueba.CheckMobile.RepresentanteVenta;

import com.example.prueba.CheckMobile.ProductoServicio.ProductoServicio;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 06-jul-17.
 */

public class RepresentanteVentaResponse {

    @SerializedName("data")
    private ArrayList<RepresentanteVenta> representanteVentas;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "RepresentanteVentaResponse{" +
                "representanteVentas=" + representanteVentas +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<RepresentanteVenta> getRepresentanteVentas() {
        return representanteVentas;
    }

    public void setRepresentanteVentas(ArrayList<RepresentanteVenta> representanteVentas) {
        this.representanteVentas = representanteVentas;
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
