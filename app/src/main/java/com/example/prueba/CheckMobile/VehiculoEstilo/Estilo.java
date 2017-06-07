package com.example.prueba.CheckMobile.VehiculoEstilo;

/**
 * Created by Prueba on 22-may-17.
 */

public class Estilo  {

    private String id;
    private String id_modelo;
    private String descripcion;
    private String estado;

    public Estilo(String idModelo)
    {
        this.id_modelo = idModelo;

    }

    @Override
    public String toString() {
        return "Estilo{" +
                "id='" + id + '\'' +
                ", id_modelo='" + id_modelo + '\'' +
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

    public String getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(String id_modelo) {
        this.id_modelo = id_modelo;
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
