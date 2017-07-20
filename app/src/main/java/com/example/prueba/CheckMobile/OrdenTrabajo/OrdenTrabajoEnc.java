package com.example.prueba.CheckMobile.OrdenTrabajo;

/**
 * Created by Prueba on 07-jul-17.
 */

public class OrdenTrabajoEnc  extends OrdenTrabajoEncResponse {
    private String id;
    private String id_empresa;
    private String id_centroCostos;
    private String cliente;
    private String id_documento;
    private String estadoFactura;
    private String tipoTransaccion;
    private String nombreCliente;
    private String apellidosCte;
    private String montoBruto;
    private String montoNeto;
    private String porcDescuento;
    private String montoDesc;
    private String montoImpuestos;
    private String montoGravado;
    private String montoExento;
    private String idAsesor;
    private String notas;
    private String idCondicion;
    private String idMoneda;
    private Long referencia;
    private String calDesc;
    private String idMecanico;
    private Long trOrigen;
    private Integer noPagos;
    private Boolean efectivo;
    private Boolean cheque;
    private Boolean tarjeta;
    private String idTipoTransFac;
    private String idDocumento;
    private String idTipoNcf;
    private String notaDescuento;
    private String noOrden;
    private String fechaPedido;
    private Short numeroDias;
    private String idTipoServicio;
    private String idSupervisor;
    private String recibidoPor;
    private String realizadoPor;
    private String id_inspeccion;
    private String fechaDocumento;
    private String permite_pieza_reemplazo;
    private String nombre_mecanico;
    private String condicion;


    public OrdenTrabajoEnc(String cliente, String nombreCliente, String apellidosCte, String montoBruto, String montoNeto, String porcDescuento, String montoDesc, String montoImpuestos, String montoGravado, String montoExento, String notas, String idCondicion, String idMoneda, String idMecanico, String fechaPedido, String idSupervisor, String id_inspeccion, String permite_pieza_reemplazo) {
        this.cliente = cliente;
        this.nombreCliente = nombreCliente;
        this.apellidosCte = apellidosCte;
        this.montoBruto = montoBruto;
        this.montoNeto = montoNeto;
        this.porcDescuento = porcDescuento;
        this.montoDesc = montoDesc;
        this.montoImpuestos = montoImpuestos;
        this.montoGravado = montoGravado;
        this.montoExento = montoExento;
        this.notas = notas;
        this.idCondicion = idCondicion;
        this.idMoneda = idMoneda;
        this.idMecanico = idMecanico;
        this.fechaPedido = fechaPedido;
        this.idSupervisor = idSupervisor;
        this.id_inspeccion = id_inspeccion;
        this.permite_pieza_reemplazo = permite_pieza_reemplazo;
    }

    public OrdenTrabajoEnc(String id, String notas, String idCondicion, String idMecanico, String fechaPedido, String permite_pieza_reemplazo) {
        this.id = id;
        this.notas = notas;
        this.idCondicion = idCondicion;
        this.idMecanico = idMecanico;
        this.fechaPedido = fechaPedido;
        this.permite_pieza_reemplazo = permite_pieza_reemplazo;
    }

    @Override
    public String toString() {
        return "OrdenTrabajoEnc{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", id_centroCostos='" + id_centroCostos + '\'' +
                ", cliente='" + cliente + '\'' +
                ", id_documento='" + id_documento + '\'' +
                ", estadoFactura='" + estadoFactura + '\'' +
                ", tipoTransaccion='" + tipoTransaccion + '\'' +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", apellidosCte='" + apellidosCte + '\'' +
                ", montoBruto='" + montoBruto + '\'' +
                ", montoNeto='" + montoNeto + '\'' +
                ", porcDescuento='" + porcDescuento + '\'' +
                ", montoDesc='" + montoDesc + '\'' +
                ", montoImpuestos='" + montoImpuestos + '\'' +
                ", montoGravado='" + montoGravado + '\'' +
                ", montoExento='" + montoExento + '\'' +
                ", idAsesor='" + idAsesor + '\'' +
                ", notas='" + notas + '\'' +
                ", idCondicion='" + idCondicion + '\'' +
                ", idMoneda='" + idMoneda + '\'' +
                ", referencia=" + referencia +
                ", calDesc='" + calDesc + '\'' +
                ", idMecanico='" + idMecanico + '\'' +
                ", trOrigen=" + trOrigen +
                ", noPagos=" + noPagos +
                ", efectivo=" + efectivo +
                ", cheque=" + cheque +
                ", tarjeta=" + tarjeta +
                ", idTipoTransFac='" + idTipoTransFac + '\'' +
                ", idDocumento='" + idDocumento + '\'' +
                ", idTipoNcf='" + idTipoNcf + '\'' +
                ", notaDescuento='" + notaDescuento + '\'' +
                ", noOrden='" + noOrden + '\'' +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", numeroDias=" + numeroDias +
                ", idTipoServicio='" + idTipoServicio + '\'' +
                ", idSupervisor='" + idSupervisor + '\'' +
                ", recibidoPor='" + recibidoPor + '\'' +
                ", realizadoPor='" + realizadoPor + '\'' +
                ", id_inspeccion='" + id_inspeccion + '\'' +
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

    public String getId_centroCostos() {
        return id_centroCostos;
    }

    public void setId_centroCostos(String id_centroCostos) {
        this.id_centroCostos = id_centroCostos;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getId_documento() {
        return id_documento;
    }

    public void setId_documento(String id_documento) {
        this.id_documento = id_documento;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidosCte() {
        return apellidosCte;
    }

    public void setApellidosCte(String apellidosCte) {
        this.apellidosCte = apellidosCte;
    }

    public String getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(String montoBruto) {
        this.montoBruto = montoBruto;
    }

    public String getMontoNeto() {
        return montoNeto;
    }

    public void setMontoNeto(String montoNeto) {
        this.montoNeto = montoNeto;
    }

    public String getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(String porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public String getMontoDesc() {
        return montoDesc;
    }

    public void setMontoDesc(String montoDesc) {
        this.montoDesc = montoDesc;
    }

    public String getMontoImpuestos() {
        return montoImpuestos;
    }

    public void setMontoImpuestos(String montoImpuestos) {
        this.montoImpuestos = montoImpuestos;
    }

    public String getMontoGravado() {
        return montoGravado;
    }

    public void setMontoGravado(String montoGravado) {
        this.montoGravado = montoGravado;
    }

    public String getMontoExento() {
        return montoExento;
    }

    public void setMontoExento(String montoExento) {
        this.montoExento = montoExento;
    }

    public String getIdAsesor() {
        return idAsesor;
    }

    public void setIdAsesor(String idAsesor) {
        this.idAsesor = idAsesor;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(String idCondicion) {
        this.idCondicion = idCondicion;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Long getReferencia() {
        return referencia;
    }

    public void setReferencia(Long referencia) {
        this.referencia = referencia;
    }

    public String getCalDesc() {
        return calDesc;
    }

    public void setCalDesc(String calDesc) {
        this.calDesc = calDesc;
    }

    public String getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(String idMecanico) {
        this.idMecanico = idMecanico;
    }

    public Long getTrOrigen() {
        return trOrigen;
    }

    public void setTrOrigen(Long trOrigen) {
        this.trOrigen = trOrigen;
    }

    public Integer getNoPagos() {
        return noPagos;
    }

    public void setNoPagos(Integer noPagos) {
        this.noPagos = noPagos;
    }

    public Boolean getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Boolean efectivo) {
        this.efectivo = efectivo;
    }

    public Boolean getCheque() {
        return cheque;
    }

    public void setCheque(Boolean cheque) {
        this.cheque = cheque;
    }

    public Boolean getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getIdTipoTransFac() {
        return idTipoTransFac;
    }

    public void setIdTipoTransFac(String idTipoTransFac) {
        this.idTipoTransFac = idTipoTransFac;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdTipoNcf() {
        return idTipoNcf;
    }

    public void setIdTipoNcf(String idTipoNcf) {
        this.idTipoNcf = idTipoNcf;
    }

    public String getNotaDescuento() {
        return notaDescuento;
    }

    public void setNotaDescuento(String notaDescuento) {
        this.notaDescuento = notaDescuento;
    }

    public String getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(String noOrden) {
        this.noOrden = noOrden;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Short getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Short numeroDias) {
        this.numeroDias = numeroDias;
    }

    public String getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(String idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public String getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(String idSupervisor) {
        this.idSupervisor = idSupervisor;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }

    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }

    public String getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(String realizadoPor) {
        this.realizadoPor = realizadoPor;
    }

    public String getId_inspeccion() {
        return id_inspeccion;
    }

    public void setId_inspeccion(String id_inspeccion) {
        this.id_inspeccion = id_inspeccion;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getPermite_pieza_reemplazo() {
        return permite_pieza_reemplazo;
    }

    public void setPermite_pieza_reemplazo(String permite_pieza_reemplazo) {
        this.permite_pieza_reemplazo = permite_pieza_reemplazo;
    }

    public String getNombre_mecanico() {
        return nombre_mecanico;
    }

    public void setNombre_mecanico(String nombre_mecanico) {
        this.nombre_mecanico = nombre_mecanico;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
