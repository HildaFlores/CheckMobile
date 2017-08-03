package com.example.prueba.CheckMobile.Inspeccion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Prueba on 21-jun-17.
 */

public class InspeccionVehiculo extends InspeccionVehiculoResponse implements Parcelable {


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
    private String placa;
    private int dias;

    protected InspeccionVehiculo(Parcel in) {
        id = in.readString();
        id_empresa = in.readString();
        idVehiculo = in.readString();
        chasis = in.readString();
        referencia = in.readString();
        fechaInspeccion = in.readString();
        serieGomas = in.readString();
        nivelCombustible = in.readString();
        idMecanico = in.readString();
        supervisor = in.readString();
        estado = in.readString();
        usuarioInsercion = in.readString();
        usuarioActualizacion = in.readString();
        observaciones = in.readString();
        idAsesor = in.readString();
        idCliente = in.readString();
        kilometraje = in.readString();
        motor = in.readString();
        estado_inspeccion = in.readString();
        nombre_vehiculo = in.readString();
        nombre_cliente = in.readString();
        tipo_veh = in.readString();
        id_condicion = in.readString();
        placa = in.readString();
        dias = in.readInt();
        color = in.readString();
        docIdentidad = in.readString();
        telefono = in.readString();
        celular = in.readString();
    }

    public static final Creator<InspeccionVehiculo> CREATOR = new Creator<InspeccionVehiculo>() {
        @Override
        public InspeccionVehiculo createFromParcel(Parcel in) {
            return new InspeccionVehiculo(in);
        }

        @Override
        public InspeccionVehiculo[] newArray(int size) {
            return new InspeccionVehiculo[size];
        }
    };

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDocIdentidad() {
        return docIdentidad;
    }

    public void setDocIdentidad(String docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    private String color;
    private String docIdentidad;
    private String telefono;
    private String celular;


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


    public InspeccionVehiculo(String id, String serieGomas, String nivelCombustible, String observaciones, String kilometraje, String motor) {
        this.id = id;
        this.serieGomas = serieGomas;
        this.nivelCombustible = nivelCombustible;
        this.observaciones = observaciones;
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

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(id_empresa);
        parcel.writeString(idVehiculo);
        parcel.writeString(chasis);
        parcel.writeString(referencia);
        parcel.writeString(fechaInspeccion);
        parcel.writeString(serieGomas);
        parcel.writeString(nivelCombustible);
        parcel.writeString(idMecanico);
        parcel.writeString(supervisor);
        parcel.writeString(estado);
        parcel.writeString(usuarioInsercion);
        parcel.writeString(usuarioActualizacion);
        parcel.writeString(observaciones);
        parcel.writeString(idAsesor);
        parcel.writeString(idCliente);
        parcel.writeString(kilometraje);
        parcel.writeString(motor);
        parcel.writeString(estado_inspeccion);
        parcel.writeString(nombre_vehiculo);
        parcel.writeString(nombre_cliente);
        parcel.writeString(tipo_veh);
        parcel.writeString(id_condicion);
        parcel.writeString(placa);
        parcel.writeInt(dias);
        parcel.writeString(color);
        parcel.writeString(docIdentidad);
        parcel.writeString(telefono);
        parcel.writeString(celular);
    }
}
