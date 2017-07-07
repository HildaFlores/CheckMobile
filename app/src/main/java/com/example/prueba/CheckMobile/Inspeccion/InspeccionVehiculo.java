package com.example.prueba.CheckMobile.Inspeccion;

import java.util.Date;

/**
 * Created by Prueba on 21-jun-17.
 */

public class InspeccionVehiculo extends InspeccionVehiculoResponse {


    private String id;
    private String id_empresa;
    private String idVehiculo;
    private String chasis;
    private String referencia;
    private String fechaInspeccion;
    private String serieGomas;
    private String nivelCombustible;
    private String idMecanico;
    private String supervisor;
    private String estado;
    private Date fechaInsercion;
    private String usuarioInsercion;
    private Date fechaActualizacion;
    private String usuarioActualizacion;
    private Long trOrigen;
    private String observaciones;
    private String idAsesor;
    private String idCliente;
    private String kilometraje;
    private String motor;
    private String estado_inspeccion;
    private String nombre_vehiculo;
    private String nombre_cliente;
    private String tipo_veh;
    private String id_condicion;


    public InspeccionVehiculo(String idVehiculo, String chasis, String referencia, String fechaInspeccion, String serieGomas, String idMecanico, String supervisor, String observaciones, String idAsesor, String idCliente, String kilometraje, String motor) {
        this.idVehiculo = idVehiculo;
        this.chasis = chasis;
        this.referencia = referencia;
        this.fechaInspeccion = fechaInspeccion;
        this.serieGomas = serieGomas;
        this.idMecanico = idMecanico;
        this.supervisor = supervisor;
        this.observaciones = observaciones;
        this.idAsesor = idAsesor;
        this.idCliente = idCliente;
        this.kilometraje = kilometraje;
        this.motor = motor;
    }

    @Override
    public String toString() {
        return "InspeccionVehiculo{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", idVehiculo='" + idVehiculo + '\'' +
                ", chasis='" + chasis + '\'' +
                ", referencia='" + referencia + '\'' +
                ", fechaInspeccion='" + fechaInspeccion + '\'' +
                ", serieGomas='" + serieGomas + '\'' +
                ", nivelCombustible='" + nivelCombustible + '\'' +
                ", idMecanico='" + idMecanico + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion=" + fechaInsercion +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion=" + fechaActualizacion +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                ", trOrigen=" + trOrigen +
                ", observaciones='" + observaciones + '\'' +
                ", idAsesor='" + idAsesor + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", kilometraje='" + kilometraje + '\'' +
                ", motor='" + motor + '\'' +
                ", estado_inspeccion='" + estado_inspeccion + '\'' +
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

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(String fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getSerieGomas() {
        return serieGomas;
    }

    public void setSerieGomas(String serieGomas) {
        this.serieGomas = serieGomas;
    }

    public String getNivelCombustible() {
        return nivelCombustible;
    }

    public void setNivelCombustible(String nivelCombustible) {
        this.nivelCombustible = nivelCombustible;
    }

    public String getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(String idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(String kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getEstado_inspeccion() {
        return estado_inspeccion;
    }

    public void setEstado_inspeccion(String estado_inspeccion) {
        this.estado_inspeccion = estado_inspeccion;
    }

    public String getNombre_vehiculo() {
        return nombre_vehiculo;
    }

    public void setNombre_vehiculo(String nombre_vehiculo) {
        this.nombre_vehiculo = nombre_vehiculo;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getTipo_veh() {
        return tipo_veh;
    }

    public void setTipo_veh(String tipo_veh) {
        this.tipo_veh = tipo_veh;
    }

    public String getId_condicion() {
        return id_condicion;
    }

    public void setId_condicion(String id_condicion) {
        this.id_condicion = id_condicion;
    }
}
