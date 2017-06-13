package com.example.prueba.CheckMobile.ProductoServicio;

/**
 * Created by Prueba on 02-jun-17.
 */

public class ProductoServicio extends ProductoServicioResponse {

    String id_empresa;
    String id_producto;
    String id_clasificacion;
    String desc_servicio;
    String fecha_insercion;
    String usuario_insercion;
    String fecha_actualizacion;
    String usaurio_actualizacion;
    String estado;
    String esmodificable;
    String id_marca;
    String referencia;
    String otras_especificaciones;
    String nota;
    String permite_descuento;
    String precio_servicio;
    String id_impuesto;

    @Override
    public String toString() {
        return "ProductoServicio{" +
                "id_empresa='" + id_empresa + '\'' +
                ", id_producto='" + id_producto + '\'' +
                ", id_clasificacion='" + id_clasificacion + '\'' +
                ", desc_servicio='" + desc_servicio + '\'' +
                ", fecha_insercion='" + fecha_insercion + '\'' +
                ", usuario_insercion='" + usuario_insercion + '\'' +
                ", fecha_actualizacion='" + fecha_actualizacion + '\'' +
                ", usaurio_actualizacion='" + usaurio_actualizacion + '\'' +
                ", estado='" + estado + '\'' +
                ", esmodificable='" + esmodificable + '\'' +
                ", id_marca='" + id_marca + '\'' +
                ", referencia='" + referencia + '\'' +
                ", otras_especificaciones='" + otras_especificaciones + '\'' +
                ", nota='" + nota + '\'' +
                ", permite_descuento='" + permite_descuento + '\'' +
                ", precio_servicio='" + precio_servicio + '\'' +
                ", id_impuesto='" + id_impuesto + '\'' +
                '}';
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_clasificacion() {
        return id_clasificacion;
    }

    public void setId_clasificacion(String id_clasificacion) {
        this.id_clasificacion = id_clasificacion;
    }

    public String getDesc_servicio() {
        return desc_servicio;
    }

    public void setDesc_servicio(String desc_servicio) {
        this.desc_servicio = desc_servicio;
    }

    public String getFecha_insercion() {
        return fecha_insercion;
    }

    public void setFecha_insercion(String fecha_insercion) {
        this.fecha_insercion = fecha_insercion;
    }

    public String getUsuario_insercion() {
        return usuario_insercion;
    }

    public void setUsuario_insercion(String usuario_insercion) {
        this.usuario_insercion = usuario_insercion;
    }

    public String getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getUsaurio_actualizacion() {
        return usaurio_actualizacion;
    }

    public void setUsaurio_actualizacion(String usaurio_actualizacion) {
        this.usaurio_actualizacion = usaurio_actualizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEsmodificable() {
        return esmodificable;
    }

    public void setEsmodificable(String esmodificable) {
        this.esmodificable = esmodificable;
    }

    public String getId_marca() {
        return id_marca;
    }

    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOtras_especificaciones() {
        return otras_especificaciones;
    }

    public void setOtras_especificaciones(String otras_especificaciones) {
        this.otras_especificaciones = otras_especificaciones;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getPermite_descuento() {
        return permite_descuento;
    }

    public void setPermite_descuento(String permite_descuento) {
        this.permite_descuento = permite_descuento;
    }

    public String getPrecio_servicio() {
        return precio_servicio;
    }

    public void setPrecio_servicio(String precio_servicio) {
        this.precio_servicio = precio_servicio;
    }

    public String getId_impuesto() {
        return id_impuesto;
    }

    public void setId_impuesto(String id_impuesto) {
        this.id_impuesto = id_impuesto;
    }
}
