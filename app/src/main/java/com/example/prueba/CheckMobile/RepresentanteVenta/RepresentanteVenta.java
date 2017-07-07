package com.example.prueba.CheckMobile.RepresentanteVenta;

/**
 * Created by Prueba on 06-jul-17.
 */

public class RepresentanteVenta extends RepresentanteVentaResponse {

    private String id;
    private String estado;
    private String nombres;
    private String apellidos;
    private String documentoIdentidad;
    private String notas;
    private String certificadoMagna;
    private String certificadoDelta;
    private String mecanico;


    @Override
    public String toString() {
        return "RepresentanteVenta{" +
                "id='" + id + '\'' +
                ", estado='" + estado + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", notas='" + notas + '\'' +
                ", certificadoMagna='" + certificadoMagna + '\'' +
                ", certificadoDelta='" + certificadoDelta + '\'' +
                ", mecanico='" + mecanico + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getCertificadoMagna() {
        return certificadoMagna;
    }

    public void setCertificadoMagna(String certificadoMagna) {
        this.certificadoMagna = certificadoMagna;
    }

    public String getCertificadoDelta() {
        return certificadoDelta;
    }

    public void setCertificadoDelta(String certificadoDelta) {
        this.certificadoDelta = certificadoDelta;
    }

    public String getMecanico() {
        return mecanico;
    }

    public void setMecanico(String mecanico) {
        this.mecanico = mecanico;
    }



}
