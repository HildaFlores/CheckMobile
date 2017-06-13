package com.example.prueba.CheckMobile.OtrosParametros;

import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesorios;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static com.example.prueba.CheckMobile.R.id.listaAccesorios;

/**
 * Created by Prueba on 09-jun-17.
 */

public class OtrosParametrosResponse {


    @SerializedName("data")
    private ArrayList<OtrosParametros> otrosParametros;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "OtrosParametrosResponse{" +
                "otrosParametros=" + otrosParametros +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<OtrosParametros> getOtrosParametros() {
        return otrosParametros;
    }

    public void setOtrosParametros(ArrayList<OtrosParametros> otrosParametros) {
        this.otrosParametros = otrosParametros;
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