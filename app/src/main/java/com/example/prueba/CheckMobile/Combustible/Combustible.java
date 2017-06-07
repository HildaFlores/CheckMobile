package com.example.prueba.CheckMobile.Combustible;

/**
 * Created by Prueba on 22-may-17.
 */

public class Combustible extends CombustibleResponse {

    private String idCombustible;
    private String descripcion;
    private String estado;
    private String fechaInsercion;
    private String usuarioInsercion;
    private String fechaActualizacion;
    private String usuarioActualizacion;

    @Override
    public String toString() {
        return "Combustible{" +
                "idCombustible='" + idCombustible + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                '}';
    }

    public String getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(String idCombustible) {
        this.idCombustible = idCombustible;
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

    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
}
