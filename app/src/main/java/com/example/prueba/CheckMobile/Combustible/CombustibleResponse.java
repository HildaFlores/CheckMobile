package com.example.prueba.CheckMobile.Combustible;

import com.example.prueba.CheckMobile.Cliente.Cliente;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static android.R.id.message;

/**
 * Created by Prueba on 30-may-17.
 */

public class CombustibleResponse {

    @SerializedName("data")
    private ArrayList<Combustible> combustibles;
    private String responseCode;
    private String message;
    private int rows;


    @Override
    public String toString() {
        return "CombustibleResponse{" +
                "combustibles=" + combustibles +
                ", responseCode='" + responseCode + '\'' +
                ", message='" + message + '\'' +
                ", rows=" + rows +
                '}';
    }

    public ArrayList<Combustible> getCombustibles() {
        return combustibles;
    }

    public void setCombustibles(ArrayList<Combustible> combustibles) {
        this.combustibles = combustibles;
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
