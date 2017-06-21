package com.example.prueba.CheckMobile.TablaDgii;

/**
 * Created by Prueba on 22-may-17.
 */

public class TablaDgii extends TablaDgiiResponse {

    private String rncCedula;
    private String razonSocial;
    private String siglas;
    private String tipoNegocio;
    private String calle;
    private String numCalle;

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public String getTipoNegocio() {
        return tipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        this.tipoNegocio = tipoNegocio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumCalle() {
        return numCalle;
    }

    public void setNumCalle(String numCalle) {
        this.numCalle = numCalle;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaOperaciones() {
        return fechaOperaciones;
    }

    public void setFechaOperaciones(String fechaOperaciones) {
        this.fechaOperaciones = fechaOperaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private String sector;
    private String telefono;
    private String fechaOperaciones;
    private String estado;
    private String fecha;
    private String tipo;


    @Override
    public String toString() {
        return "TablaDgii{" +
                "rncCedula='" + rncCedula + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", siglas='" + siglas + '\'' +
                ", tipoNegocio='" + tipoNegocio + '\'' +
                ", calle='" + calle + '\'' +
                ", numCalle='" + numCalle + '\'' +
                ", sector='" + sector + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaOperaciones='" + fechaOperaciones + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public String getRncCedula() {
        return rncCedula;
    }

    public void setRncCedula(String rncCedula) {
        this.rncCedula = rncCedula;
    }
}
