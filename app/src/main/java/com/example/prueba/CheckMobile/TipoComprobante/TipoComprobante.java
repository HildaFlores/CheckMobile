package com.example.prueba.CheckMobile.TipoComprobante;

/**
 * Created by Prueba on 23-may-17.
 */

public class TipoComprobante {

    private String idTipoNcf;
    private String descripcion;
    private Boolean valorFiscal;
    private String documento;
    private String fechaInsercion;
    private String fechaActualizacion;
    private String usuarioInsercion;
    private String usuarioActualizacion;

    @Override
    public String toString() {
        return "TipoComprobante{" +
                "idTipoNcf='" + idTipoNcf + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", valorFiscal=" + valorFiscal +
                ", documento='" + documento + '\'' +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                '}';
    }

    public String getIdTipoNcf() {
        return idTipoNcf;
    }

    public void setIdTipoNcf(String idTipoNcf) {
        this.idTipoNcf = idTipoNcf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getValorFiscal() {
        return valorFiscal;
    }

    public void setValorFiscal(Boolean valorFiscal) {
        this.valorFiscal = valorFiscal;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }
}
