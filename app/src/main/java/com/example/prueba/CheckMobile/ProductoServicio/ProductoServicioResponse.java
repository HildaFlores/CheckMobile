package com.example.prueba.CheckMobile.ProductoServicio;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 02-jun-17.
 */

public class ProductoServicioResponse {

    @SerializedName("data")
    private ArrayList<ProductoServicio> productoServicios;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "ProductoServicioResponse{" +
                "productoServicios=" + productoServicios +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<ProductoServicio> getProductoServicios() {
        return productoServicios;
    }

    public void setProductoServicios(ArrayList<ProductoServicio> productoServicios) {
        this.productoServicios = productoServicios;
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
