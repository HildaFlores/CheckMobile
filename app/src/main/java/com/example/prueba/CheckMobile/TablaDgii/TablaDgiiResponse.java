package com.example.prueba.CheckMobile.TablaDgii;

import com.example.prueba.CheckMobile.Cliente.Cliente;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 14-jun-17.
 */

public class TablaDgiiResponse {

    @SerializedName("data")
    private ArrayList<TablaDgii> ClienteDgii;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "TablaDgiiResponse{" +
                "ClienteDgii=" + ClienteDgii +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<TablaDgii> getClienteDgii() {
        return ClienteDgii;
    }

    public void setClienteDgii(ArrayList<TablaDgii> clienteDgii) {
        ClienteDgii = clienteDgii;
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

