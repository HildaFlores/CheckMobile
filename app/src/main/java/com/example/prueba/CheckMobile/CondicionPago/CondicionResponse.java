package com.example.prueba.CheckMobile.CondicionPago;

import com.example.prueba.CheckMobile.Pais.Pais;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 01-jun-17.
 */

public class CondicionResponse {
    @SerializedName("data")
    private ArrayList<CondicionPago> condicionPagos;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "CondicionResponse{" +
                "condicionPagos=" + condicionPagos +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<CondicionPago> getCondicionPagos() {
        return condicionPagos;
    }

    public void setCondicionPagos(ArrayList<CondicionPago> condicionPagos) {
        this.condicionPagos = condicionPagos;
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
