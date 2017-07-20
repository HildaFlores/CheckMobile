package com.example.prueba.CheckMobile.OrdenTrabajo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 10-jul-17.
 */

public class OrdenTrabajoDetResponse {
    @SerializedName("data")
    private ArrayList<OrdenTrabajoDet> ordenes;
    private String responseCode;
    private String message;
    private int rows;


    public ArrayList<OrdenTrabajoDet> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(ArrayList<OrdenTrabajoDet> ordenes) {
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
