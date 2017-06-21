package com.example.prueba.CheckMobile.Vehiculo;

/**
 * Created by Prueba on 22-may-17.
 */

public class Vehiculo extends VehiculoResponse {
    private String id;
    private String id_empresa;
    private String chasis;
    private String idTipoVehiculo;
    private String idMarca;
    private String idModelo;
    private String idEstilo;
    private String nota;
    private String color;
    private String colorInterior;
    private String ano;
    private String filaAsiento;
    private String cantPuerta;
    private String idCombustible;
    private String idTraccion;
    private String cilindraje;
    private String referencia;
    private String idMoneda;
    private String placa;
    private String idCliente;
    private String estado;
    private String fechaInsercion;
    private String usuarioInsercion;
    private String fechaActualizacion;
    private String usuarioActualizacion;
    private String condicionVehi;
    private String estadoVeh;
    private String carga;
    private Short pasajeros;
    private int cilindros;
    private String idEntGarantia;
    private Long kmGarantia;
    private String tiempoGarantia;
    private String garantia;
    private String notasGarantia;
    private String idUso;
    private Short noVelocidades;
    private String vmUbicacion;
    private String desc_modelo;
    private String desc_marca;
    private String desc_estilo;
    private String idTransmision;
    private String secuenciaEntrada;



    public Vehiculo(String secuenciaEntrada,  String chasis, String idTipoVehiculo, String idMarca, String idModelo, String idEstilo, String nota, String color, String colorInterior, String ano, String filaAsiento, String cantPuerta, String idCombustible, String idTraccion, String cilindraje, String referencia, String placa, String estadoVeh, int cilindros, String garantia, String idTransmision) {
        this.secuenciaEntrada = secuenciaEntrada;
        this.chasis = chasis;
        this.idTipoVehiculo = idTipoVehiculo;
        this.idMarca = idMarca;
        this.idModelo = idModelo;
        this.idEstilo = idEstilo;
        this.nota = nota;
        this.color = color;
        this.colorInterior = colorInterior;
        this.ano = ano;
        this.filaAsiento = filaAsiento;
        this.cantPuerta = cantPuerta;
        this.idCombustible = idCombustible;
        this.idTraccion = idTraccion;
        this.cilindraje = cilindraje;
        this.referencia = referencia;
        this.placa = placa;
        this.estadoVeh = estadoVeh;
        this.cilindros = cilindros;
        this.garantia = garantia;
        this.idTransmision = idTransmision;

    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", chasis='" + chasis + '\'' +
                ", idTipoVehiculo='" + idTipoVehiculo + '\'' +
                ", idMarca='" + idMarca + '\'' +
                ", idModelo='" + idModelo + '\'' +
                ", idEstilo='" + idEstilo + '\'' +
                ", nota='" + nota + '\'' +
                ", color='" + color + '\'' +
                ", colorInterior='" + colorInterior + '\'' +
                ", ano='" + ano + '\'' +
                ", filaAsiento='" + filaAsiento + '\'' +
                ", cantPuerta='" + cantPuerta + '\'' +
                ", idCombustible='" + idCombustible + '\'' +
                ", idTraccion='" + idTraccion + '\'' +
                ", cilindraje='" + cilindraje + '\'' +
                ", referencia='" + referencia + '\'' +
                ", idMoneda='" + idMoneda + '\'' +
                ", placa='" + placa + '\'' +
                ", idCliente='" + idCliente + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                ", condicionVehi='" + condicionVehi + '\'' +
                ", estadoVeh='" + estadoVeh + '\'' +
                ", carga='" + carga + '\'' +
                ", pasajeros=" + pasajeros +
                ", cilindros=" + getCilindros() +
                ", idEntGarantia='" + idEntGarantia + '\'' +
                ", kmGarantia=" + kmGarantia +
                ", tiempoGarantia='" + tiempoGarantia + '\'' +
                ", garantia=" + garantia +
                ", notasGarantia='" + notasGarantia + '\'' +
                ", idUso='" + idUso + '\'' +
                ", noVelocidades=" + noVelocidades +
                ", vmUbicacion='" + vmUbicacion + '\'' +
                ", desc_modelo='" + desc_modelo + '\'' +
                ", desc_marca='" + desc_marca + '\'' +
                ", desc_estilo='" + desc_estilo + '\'' +
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

    public String getChasis() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis = chasis;
    }

    public String getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(String idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(String idMarca) {
        this.idMarca = idMarca;
    }

    public String getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    public String getIdEstilo() {
        return idEstilo;
    }

    public void setIdEstilo(String idEstilo) {
        this.idEstilo = idEstilo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorInterior() {
        return colorInterior;
    }

    public void setColorInterior(String colorInterior) {
        this.colorInterior = colorInterior;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getFilaAsiento() {
        return filaAsiento;
    }

    public void setFilaAsiento(String filaAsiento) {
        this.filaAsiento = filaAsiento;
    }

    public String getCantPuerta() {
        return cantPuerta;
    }

    public void setCantPuerta(String cantPuerta) {
        this.cantPuerta = cantPuerta;
    }

    public String getIdCombustible() {
        return idCombustible;
    }

    public void setIdCombustible(String idCombustible) {
        this.idCombustible = idCombustible;
    }

    public String getIdTraccion() {
        return idTraccion;
    }

    public void setIdTraccion(String idTraccion) {
        this.idTraccion = idTraccion;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        this.cilindraje = cilindraje;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public String getCondicionVehi() {
        return condicionVehi;
    }

    public void setCondicionVehi(String condicionVehi) {
        this.condicionVehi = condicionVehi;
    }

    public String getEstadoVeh() {
        return estadoVeh;
    }

    public void setEstadoVeh(String estadoVeh) {
        this.estadoVeh = estadoVeh;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public Short getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(Short pasajeros) {
        this.pasajeros = pasajeros;
    }


    public String getIdEntGarantia() {
        return idEntGarantia;
    }

    public void setIdEntGarantia(String idEntGarantia) {
        this.idEntGarantia = idEntGarantia;
    }

    public Long getKmGarantia() {
        return kmGarantia;
    }

    public void setKmGarantia(Long kmGarantia) {
        this.kmGarantia = kmGarantia;
    }

    public String getTiempoGarantia() {
        return tiempoGarantia;
    }

    public void setTiempoGarantia(String tiempoGarantia) {
        this.tiempoGarantia = tiempoGarantia;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getNotasGarantia() {
        return notasGarantia;
    }

    public void setNotasGarantia(String notasGarantia) {
        this.notasGarantia = notasGarantia;
    }

    public String getIdUso() {
        return idUso;
    }

    public void setIdUso(String idUso) {
        this.idUso = idUso;
    }

    public Short getNoVelocidades() {
        return noVelocidades;
    }

    public void setNoVelocidades(Short noVelocidades) {
        this.noVelocidades = noVelocidades;
    }

    public String getVmUbicacion() {
        return vmUbicacion;
    }

    public void setVmUbicacion(String vmUbicacion) {
        this.vmUbicacion = vmUbicacion;
    }


    public String getDesc_modelo() {
        return desc_modelo;
    }

    public void setDesc_modelo(String desc_modelo) {
        this.desc_modelo = desc_modelo;
    }

    public String getDesc_marca() {
        return desc_marca;
    }

    public void setDesc_marca(String desc_marca) {
        this.desc_marca = desc_marca;
    }

    public String getDesc_estilo() {
        return desc_estilo;
    }

    public void setDesc_estilo(String desc_estilo) {
        this.desc_estilo = desc_estilo;
    }

    public int getCilindros() {
        return cilindros;
    }

    public void setCilindros(int cilindros) {
        this.cilindros = cilindros;
    }

    public String getIdTransmision() {
        return idTransmision;
    }

    public void setIdTransmision(String idTransmision) {
        this.idTransmision = idTransmision;
    }

    public String getSecuencia_entrada() {
        return secuenciaEntrada;
    }

    public void setSecuencia_entrada(String secuencia_entrada) {
        this.secuenciaEntrada = secuencia_entrada;
    }
}
