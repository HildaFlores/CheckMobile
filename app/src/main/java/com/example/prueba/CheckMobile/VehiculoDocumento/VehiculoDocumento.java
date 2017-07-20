package com.example.prueba.CheckMobile.VehiculoDocumento;

/**
 * Created by Prueba on 27-jun-17.
 */

public class VehiculoDocumento extends VehiculoDocumentoResponse {

    private String id_vehiculo;
    private String id_empresa;
    private String id_tipo_trans;
    private String rutaDocumento;
    private String id_documento;
    private String nota;
    private String estado;
    private String id_lado;

    public VehiculoDocumento(String id_vehiculo,String rutaDocumento, String id_documento, String nota, String id_lado) {
        this.id_vehiculo = id_vehiculo;
        this.rutaDocumento = rutaDocumento;
        this.id_documento = id_documento;
        this.nota = nota;
        this.id_lado = id_lado;
    }

    @Override
    public String toString() {
        return "VehiculoDocumento{" +
                "id_vehiculo='" + id_vehiculo + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", id_tipo_trans='" + id_tipo_trans + '\'' +
                ", rutaDocumento='" + rutaDocumento + '\'' +
                ", id_documento='" + id_documento + '\'' +
                ", nota='" + nota + '\'' +
                ", estado='" + estado + '\'' +
                ", id_lado='" + id_lado + '\'' +
                '}';
    }

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_tipo_trans() {
        return id_tipo_trans;
    }

    public void setId_tipo_trans(String id_tipo_trans) {
        this.id_tipo_trans = id_tipo_trans;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_lado() {
        return id_lado;
    }

    public void setId_lado(String id_lado) {
        this.id_lado = id_lado;
    }
}
