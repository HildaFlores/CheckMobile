package com.example.prueba.CheckMobile.TipoVehiculo;

/**
 * Created by Prueba on 23-may-17.
 */

public class TipoVehiculo extends TipoVehiculoResponse {

    private String idTipoVehiculo;
    private String descripcion;
    private String estado;
    private String fechaInsercion;
    private String usuarioInsercion;
    private String fechaActualizacion;
    private String usuarioActualizacion;

    @Override
    public String toString() {
        return "TipoVehiculo{" +
                "idTipoVehiculo='" + idTipoVehiculo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                '}';
    }

    public String getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(String idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
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
