package com.example.prueba.CheckMobile.Inspeccion;

import java.util.Date;

/**
 * Created by Prueba on 21-jun-17.
 */

public class InspeccionVehiculoDetalle extends InspeccionVehDetalleResponse {
    private String id;
    private String id_empresa;
    private String idVehiculo;
    private String idTipoTrans;
    private String idElementoInspeccion;
    private String grado;
    private String puntuacion;
    private String observacion;
    private String estado;
    private Date fechaInsercion;
    private String usuarioInsercion;
    private Date fechaActualizacion;
    private String usuarioActualizacion;
    private Long trOrigen;
    private String idRespuesta;
    private String id_lista_parametro;
    private String desc_respuesta;
    private String desc__elemento;



    public InspeccionVehiculoDetalle(String id, String idVehiculo, String idElementoInspeccion, String observacion, String idRespuesta, String idListaParametro) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idElementoInspeccion = idElementoInspeccion;
        this.observacion = observacion;
        this.idRespuesta = idRespuesta;
        this.id_lista_parametro = idListaParametro;

    }

    public InspeccionVehiculoDetalle(String id, String idVehiculo, String idElementoInspeccion, String observacion, String idRespuesta, String idListaParametro, String puntuacion) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.idElementoInspeccion = idElementoInspeccion;
        this.observacion = observacion;
        this.idRespuesta = idRespuesta;
        this.id_lista_parametro = idListaParametro;
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "InspeccionVehiculoDetalle{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", idVehiculo='" + idVehiculo + '\'' +
                ", idTipoTrans='" + idTipoTrans + '\'' +
                ", idElementoInspeccion='" + idElementoInspeccion + '\'' +
                ", grado='" + grado + '\'' +
                ", puntuacion='" + puntuacion + '\'' +
                ", observacion='" + observacion + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion=" + fechaInsercion +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion=" + fechaActualizacion +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                ", trOrigen=" + trOrigen +
                ", idRespuesta='" + idRespuesta + '\'' +
                ", id_lista_parametro='" + id_lista_parametro + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(String idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getIdTipoTrans() {
        return idTipoTrans;
    }

    public void setIdTipoTrans(String idTipoTrans) {
        this.idTipoTrans = idTipoTrans;
    }

    public String getIdElementoInspeccion() {
        return idElementoInspeccion;
    }

    public void setIdElementoInspeccion(String idElementoInspeccion) {
        this.idElementoInspeccion = idElementoInspeccion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Long getTrOrigen() {
        return trOrigen;
    }

    public void setTrOrigen(Long trOrigen) {
        this.trOrigen = trOrigen;
    }

    public String getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(String idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getId_lista_parametro() {
        return id_lista_parametro;
    }

    public void setId_lista_parametro(String id_lista_parametro) {
        this.id_lista_parametro = id_lista_parametro;
    }

    public String getDesc_respuesta() {
        return desc_respuesta;
    }

    public void setDesc_respuesta(String desc_respuesta) {
        this.desc_respuesta = desc_respuesta;
    }

    public String getDesc__elemento() {
        return desc__elemento;
    }

    public void setDesc__elemento(String desc__elemento) {
        this.desc__elemento = desc__elemento;
    }
}
