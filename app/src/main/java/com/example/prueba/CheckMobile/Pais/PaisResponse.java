package com.example.prueba.CheckMobile.Pais;

import com.example.prueba.CheckMobile.Cliente.Cliente;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Prueba on 31-may-17.
 */

public class PaisResponse {


    @SerializedName("data")
    private ArrayList<Pais> paises;
    private String responseCode;
    private String message;
    private int rows;

    @Override
    public String toString() {
        return "PaisResponse{" +
                "paises=" + paises +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public void setPaises(ArrayList<Pais> paises) {
        this.paises = paises;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public ArrayList<Pais> getPaises() {
        return paises;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public int getRows() {
        return rows;
    }
}
