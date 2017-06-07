package com.example.prueba.CheckMobile.Pais;

/**
 * Created by Prueba on 31-may-17.
 */

public class Pais extends PaisResponse {


    private String id_pais;
    private String desc_pais;
    private String nacionalidad;
    private String estado;



    @Override
    public String toString() {
        return "Pais{" +
                "id_pais='" + id_pais + '\'' +
                ", desc_pais='" + desc_pais + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getDesc_pais() {
        return desc_pais;
    }

    public void setDesc_pais(String desc_pais) {
        this.desc_pais = desc_pais;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

