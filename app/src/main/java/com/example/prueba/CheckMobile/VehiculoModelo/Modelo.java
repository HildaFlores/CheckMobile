package com.example.prueba.CheckMobile.VehiculoModelo;

/**
 * Created by Prueba on 22-may-17.
 */

public class Modelo extends ModeloResponse {

    private String id;
    private String idMarca;
    private String id_empresa;
    private String descripcion;
    private String estado;
    private String fechaInsercion;
    private String usuarioInsercion;
    private String fechaActualizacion;
    private String usuarioActualizacion;
    private String idTipoVehiculo;


    public Modelo(String idMarca, String descripcion) {
        this.idMarca = idMarca;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
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

    public String getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(String idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id='" + id + '\'' +
                ", idMarca='" + idMarca + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                ", idTipoVehiculo='" + idTipoVehiculo + '\'' +
                '}';
    }

}
