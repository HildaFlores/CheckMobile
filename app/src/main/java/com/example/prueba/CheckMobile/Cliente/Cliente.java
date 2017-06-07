package com.example.prueba.CheckMobile.Cliente;



/**
 * Created by Prueba on 19-may-17.
 */

public class Cliente extends ClienteResponse {
    private String id;
    private String id_empresa;
    private String estadoCliente;
    private String idTipoCliente;
    private String estado;
    private String usuarioInsercion;
    private String nombres;
    private String apellidos;
    private Short edad;
    private String sexo;
    private String estadoCivil;
    private String documentoIdentidad;
    private String rnc;
    private String nombreEmpresa;
    private String contactoPrincipal;
    private String usuarioActualizacion;
    private String idCondicion;
    private String nacionalidad;
    private String apodo;
    private String ocupacion;
    private String notas;
    private String pais;
    private String idCondicionVend;
    private String idClasificacion;
    private Short descFactura;
    private String fechaInsercion;
    private String fechaActualizacion;
    private String fechaNacimiento;
    private String idTipoCategoria;

    private String razonSocial;
    private String noPasaporte;

    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                ", estadoCliente='" + estadoCliente + '\'' +
                ", idTipoCliente='" + idTipoCliente + '\'' +
                ", estado='" + estado + '\'' +
                ", usuarioInsercion='" + usuarioInsercion + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", sexo='" + sexo + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", documentoIdentidad='" + documentoIdentidad + '\'' +
                ", rnc='" + rnc + '\'' +
                ", nombreEmpresa='" + nombreEmpresa + '\'' +
                ", contactoPrincipal='" + contactoPrincipal + '\'' +
                ", usuarioActualizacion='" + usuarioActualizacion + '\'' +
                ", idCondicion='" + idCondicion + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", apodo='" + apodo + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", notas='" + notas + '\'' +
                ", pais='" + pais + '\'' +
                ", idCondicionVend='" + idCondicionVend + '\'' +
                ", idClasificacion='" + idClasificacion + '\'' +
                ", descFactura=" + descFactura +
                ", fechaInsercion='" + fechaInsercion + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", idTipoCategoria='" + idTipoCategoria + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", noPasaporte='" + noPasaporte + '\'' +
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

    public String getEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(String estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public String getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(String idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
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

    public Short getEdad() {
        return edad;
    }

    public void setEdad(Short edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getRnc() {
        return rnc;
    }

    public void setRnc(String rnc) {
        this.rnc = rnc;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public String getIdCondicion() {
        return idCondicion;
    }

    public void setIdCondicion(String idCondicion) {
        this.idCondicion = idCondicion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public String getIdCondicionVend() {
        return idCondicionVend;
    }

    public void setIdCondicionVend(String idCondicionVend) {
        this.idCondicionVend = idCondicionVend;
    }

    public String getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(String idClasificacion) {
        this.idClasificacion = idClasificacion;
    }


    public Short getDescFactura() {
        return descFactura;
    }

    public void setDescFactura(Short descFactura) {
        this.descFactura = descFactura;
    }




    public String getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(String idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNoPasaporte() {
        return noPasaporte;
    }

    public void setNoPasaporte(String noPasaporte) {
        this.noPasaporte = noPasaporte;
    }



    public String getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(String fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


}
