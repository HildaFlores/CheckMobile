package com.example.prueba.CheckMobile.AccesoriosParametros;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 09-jun-17.
 */

public class ListaAccesoriosResponse {

    @SerializedName("data")
    private ArrayList<ListaAccesorios> listaAccesorios;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "ListaAccesoriosResponse{" +
                "listaAccesorios=" + listaAccesorios +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<ListaAccesorios> getListaAccesorios() {
        return listaAccesorios;
    }

    public void setListaAccesorios(ArrayList<ListaAccesorios> listaAccesorios) {
        this.listaAccesorios = listaAccesorios;
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



