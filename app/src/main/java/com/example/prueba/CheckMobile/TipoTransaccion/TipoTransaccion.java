package com.example.prueba.CheckMobile.TipoTransaccion;

/**
 * Created by Prueba on 22-jun-17.
 */

public class TipoTransaccion {

    private String id;
    private String id_empresa;
    private String descripcion;
    private String estado;
    private Long secuencia;


    @Override
    public String toString() {
        return "TipoTransaccion{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", secuencia=" + secuencia +
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

    public Long getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Long secuencia) {
        this.secuencia = secuencia;
    }
}
