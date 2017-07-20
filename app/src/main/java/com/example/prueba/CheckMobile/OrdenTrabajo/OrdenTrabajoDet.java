package com.example.prueba.CheckMobile.OrdenTrabajo;

/**
 * Created by Prueba on 07-jul-17.
 */

public class OrdenTrabajoDet extends OrdenTrabajoDetResponse {

    private String id_empresa;
    private String id_tipo_trans;
    private String id_documento;
    private String id_producto;
    private String cod_barra;
    private String cantidad;
    private String id_impuesto;
    private String descripcion_producto;
    private String precio;
    private String porc_descuento;
    private String monto_impuesto;
    private String monto_descuento;
    private String importe;
    private String importe_itbis;
    private String precio_neto;
    private String observaciones;
    private String precio_bruto;
    private String precio_final_sin_impuestos;
    private String impuestos;
    private String precio_final_con_impuestos;



    public OrdenTrabajoDet(String id_documento, String id_producto, String cantidad, String id_impuesto, String descripcion_producto, String precio, String porc_descuento, String monto_impuesto, String monto_descuento, String importe, String importe_itbis, String precio_neto, String observaciones, String precio_bruto, String precio_final_sin_impuestos, String impuestos, String precio_final_con_impuestos) {
        this.id_documento = id_documento;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.id_impuesto = id_impuesto;
        this.descripcion_producto = descripcion_producto;
        this.precio = precio;
        this.porc_descuento = porc_descuento;
        this.monto_impuesto = monto_impuesto;
        this.monto_descuento = monto_descuento;
        this.importe = importe;
        this.importe_itbis = importe_itbis;
        this.precio_neto = precio_neto;
        this.observaciones = observaciones;
        this.precio_bruto = precio_bruto;
        this.precio_final_sin_impuestos = precio_final_sin_impuestos;
        this.impuestos = impuestos;
        this.precio_final_con_impuestos = precio_final_con_impuestos;
    }

    @Override
    public String toString() {
        return "OrdenTrabajoDet{" +
                "id_empresa='" + id_empresa + '\'' +
                ", id_tipo_trans='" + id_tipo_trans + '\'' +
                ", id_documento='" + id_documento + '\'' +
                ", id_producto='" + id_producto + '\'' +
                ", cod_barra='" + cod_barra + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", id_impuesto='" + id_impuesto + '\'' +
                ", descripcion_producto='" + descripcion_producto + '\'' +
                ", precio='" + precio + '\'' +
                ", porc_descuento='" + porc_descuento + '\'' +
                ", monto_impuesto='" + monto_impuesto + '\'' +
                ", monto_descuento='" + monto_descuento + '\'' +
                ", importe='" + importe + '\'' +
                ", importe_itbis='" + importe_itbis + '\'' +
                ", precio_neto='" + precio_neto + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", precio_bruto='" + precio_bruto + '\'' +
                ", precio_final_sin_impuestos='" + precio_final_sin_impuestos + '\'' +
                ", impuestos='" + impuestos + '\'' +
                ", precio_final_con_impuestos='" + precio_final_con_impuestos + '\'' +
                '}';
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

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getId_impuesto() {
        return id_impuesto;
    }

    public void setId_impuesto(String id_impuesto) {
        this.id_impuesto = id_impuesto;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public void setDescripcion_producto(String descripcion_producto) {
        this.descripcion_producto = descripcion_producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getPorc_descuento() {
        return porc_descuento;
    }

    public void setPorc_descuento(String porc_descuento) {
        this.porc_descuento = porc_descuento;
    }

    public String getMonto_impuesto() {
        return monto_impuesto;
    }

    public void setMonto_impuesto(String monto_impuesto) {
        this.monto_impuesto = monto_impuesto;
    }

    public String getMonto_descuento() {
        return monto_descuento;
    }

    public void setMonto_descuento(String monto_descuento) {
        this.monto_descuento = monto_descuento;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getImporte_itbis() {
        return importe_itbis;
    }

    public void setImporte_itbis(String importe_itbis) {
        this.importe_itbis = importe_itbis;
    }

    public String getPrecio_neto() {
        return precio_neto;
    }

    public void setPrecio_neto(String precio_neto) {
        this.precio_neto = precio_neto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPrecio_bruto() {
        return precio_bruto;
    }

    public void setPrecio_bruto(String precio_bruto) {
        this.precio_bruto = precio_bruto;
    }

    public String getPrecio_final_sin_impuestos() {
        return precio_final_sin_impuestos;
    }

    public void setPrecio_final_sin_impuestos(String precio_final_sin_impuestos) {
        this.precio_final_sin_impuestos = precio_final_sin_impuestos;
    }

    public String getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(String impuestos) {
        this.impuestos = impuestos;
    }

    public String getPrecio_final_con_impuestos() {
        return precio_final_con_impuestos;
    }

    public void setPrecio_final_con_impuestos(String precio_final_con_impuestos) {
        this.precio_final_con_impuestos = precio_final_con_impuestos;
    }
}
