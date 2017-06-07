package com.example.prueba.CheckMobile.CondicionPago;

/**
 * Created by Prueba on 23-may-17.
 */

public class CondicionPago extends CondicionResponse {

    private String id;
    private String dias;
    private String descripcion;
    private String estado;

    @Override
    public String toString() {
        return "CondicionPago{" +
                "id='" + id + '\'' +
                ", dias='" + dias + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}