package com.example.prueba.CheckMobile.Inspeccion;

/**
 * Created by Prueba on 13-ago-17.
 */

public class ImageUpload {

    private String name;
    private String url;
    private String idInspeccion;


    @Override
    public String toString() {
        return "ImageUpload{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", idInspeccion='" + idInspeccion + '\'' +
                '}';
    }

    public ImageUpload(String name, String url, String idInspeccion) {
        this.name = name;
        this.url = url;
        this.idInspeccion = idInspeccion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getIdInspeccion() {
        return idInspeccion;
    }

    public void setIdInspeccion(String idInspeccion) {
        this.idInspeccion = idInspeccion;
    }
}
