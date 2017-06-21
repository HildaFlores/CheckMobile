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
    private int edad;
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
    private String fechaNac;
    private String idTipoCategoria;
    private String razonSocial;
    private String noPasaporte;
    private String limiteCredito;
    private String telefono;
    private String telefono_movil;
    private String direccion_email;
    private String linea1;
    private String ciudad_provincia;
    private String linea2;
    private String descripcion_condicion;


    public Cliente( String idTipoCliente, String nombres, String apellidos, int edad, String sexo, String documentoIdentidad, String rnc, String nombreEmpresa, String idCondicion, String nacionalidad, String apodo,  String notas, String pais, String fechaNac,  String limiteCredito, String telefono, String telefono_movil, String direccion_email, String linea1, String ciudad_provincia, String linea2) {
        this.idTipoCliente = idTipoCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.sexo = sexo;
        this.documentoIdentidad = documentoIdentidad;
        this.rnc = rnc;
        this.nombreEmpresa = nombreEmpresa;
        this.idCondicion = idCondicion;
        this.nacionalidad = nacionalidad;
        this.apodo = apodo;
        this.notas = notas;
        this.pais = pais;
        this.fechaNac = fechaNac;
        this.limiteCredito = limiteCredito;
        this.telefono = telefono;
        this.telefono_movil = telefono_movil;
        this.direccion_email = direccion_email;
        this.linea1 = linea1;
        this.ciudad_provincia = ciudad_provincia;
        this.linea2 = linea2;
    }

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
                ", fechaNac='" + fechaNac + '\'' +
                ", idTipoCategoria='" + idTipoCategoria + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                ", noPasaporte='" + noPasaporte + '\'' +
                ", limiteCredito='" + limiteCredito + '\'' +
                ", telefono='" + telefono + '\'' +
                ", telefono_movil='" + telefono_movil + '\'' +
                ", direccion_email='" + direccion_email + '\'' +
                ", linea1='" + linea1 + '\'' +
                ", ciudad_provincia='" + ciudad_provincia + '\'' +
                ", linea2='" + linea2 + '\'' +
                ", descripcion_condicion='" + descripcion_condicion + '\'' +
                '}';
    }

    public String getDescripcion_condicion() {
        return descripcion_condicion;
    }

    public void setDescripcion_condicion(String descripcion_condicion) {
        this.descripcion_condicion = descripcion_condicion;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono_movil() {
        return telefono_movil;
    }

    public void setTelefono_movil(String telefono_movil) {
        this.telefono_movil = telefono_movil;
    }

    public String getDireccion_email() {
        return direccion_email;
    }

    public void setDireccion_email(String direccion_email) {
        this.direccion_email = direccion_email;
    }

    public String getLinea1() {
        return linea1;
    }

    public void setLinea1(String linea1) {
        this.linea1 = linea1;
    }

    public String getCiudad_provincia() {
        return ciudad_provincia;
    }

    public void setCiudad_provincia(String ciudad_provincia) {
        this.ciudad_provincia = ciudad_provincia;
    }

    public String getLinea2() {
        return linea2;
    }

    public void setLinea2(String linea2) {
        this.linea2 = linea2;
    }

    public String getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(String limiteCredito) {
        this.limiteCredito = limiteCredito;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
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

    public String getfechaNac() {
        return fechaNac;
    }

    public void setfechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }


}
