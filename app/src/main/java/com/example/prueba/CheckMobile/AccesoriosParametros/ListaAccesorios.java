package com.example.prueba.CheckMobile.AccesoriosParametros;

/**
 * Created by Prueba on 09-jun-17.
 */

public class ListaAccesorios extends ListaAccesoriosResponse {

    private String id;
    private String id_empresa;
    private String valor;
    private String descripcion;
    private String estado;
    private String rutaImagen;

    @Override
    public String toString() {
        return "ListaLuces{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", valor='" + valor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
