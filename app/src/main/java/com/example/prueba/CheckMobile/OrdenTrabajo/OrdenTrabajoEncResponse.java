package com.example.prueba.CheckMobile.OrdenTrabajo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 07-jul-17.
 */

public class OrdenTrabajoEncResponse {
    @SerializedName("data")
    private ArrayList<OrdenTrabajoEnc> ordenes;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "OrdenTrabajoEncResponse{" +
                "ordenes=" + ordenes +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<OrdenTrabajoEnc> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(ArrayList<OrdenTrabajoEnc> ordenes) {
        this.ordenes = ordenes;
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
