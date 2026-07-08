package LOGICA;

import java.sql.Date;
import java.sql.Timestamp;

public class ClienteClass {
    private int idCliente;
    private String tipoCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private String razonSocial;
    private String nombreComercial;
    private String representanteLegal;
    private String tipoDocumentoRepresentante;
    private String numeroDocumentoRepresentante;
    private String telefono;
    private String correo;
    private String direccion;
    private String direccionFiscal;
    private String distrito;
    private String provincia;
    private String departamento;
    private String estado;
    private String nombreCliente;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    // Campos conservados por compatibilidad con formularios anteriores.
    private String nombreContacto;
    private String cargoContacto;
    private String ciudad;
    private String region;
    private String pais;

    public ClienteClass() {
        this.tipoCliente = "PERSONA_NATURAL";
        this.tipoDocumento = "DNI";
        this.estado = "Activo";
    }

    public ClienteClass(int idCliente, String documento, String nombreCliente, String nombreContacto, String cargoContacto, String direccion, String ciudad, String region, String pais, String telefono) {
        this();
        this.idCliente = idCliente;
        this.numeroDocumento = documento;
        this.nombres = nombreCliente;
        this.nombreCliente = nombreCliente;
        this.nombreContacto = nombreContacto;
        this.cargoContacto = cargoContacto;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.region = region;
        this.pais = pais;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getDocumento() {
        return numeroDocumento;
    }

    public void setDocumento(String documento) {
        this.numeroDocumento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombreCliente() {
        if (nombreCliente != null && !nombreCliente.trim().isEmpty()) return nombreCliente;
        if ("EMPRESA".equalsIgnoreCase(tipoCliente)) return razonSocial != null ? razonSocial : "";
        return ((nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "")).trim();
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.nombres = nombreCliente;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getTipoDocumentoRepresentante() {
        return tipoDocumentoRepresentante;
    }

    public void setTipoDocumentoRepresentante(String tipoDocumentoRepresentante) {
        this.tipoDocumentoRepresentante = tipoDocumentoRepresentante;
    }

    public String getNumeroDocumentoRepresentante() {
        return numeroDocumentoRepresentante;
    }

    public void setNumeroDocumentoRepresentante(String numeroDocumentoRepresentante) {
        this.numeroDocumentoRepresentante = numeroDocumentoRepresentante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getCargoContacto() {
        return cargoContacto;
    }

    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
    }

    public String getCiudad() {
        return ciudad != null ? ciudad : distrito;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
        this.distrito = ciudad;
    }

    public String getRegion() {
        return region != null ? region : departamento;
    }

    public void setRegion(String region) {
        this.region = region;
        this.departamento = region;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public boolean esEmpresa() {
        return "EMPRESA".equalsIgnoreCase(tipoCliente);
    }

    public boolean esPersonaNatural() {
        return "PERSONA_NATURAL".equalsIgnoreCase(tipoCliente);
    }

    @Override
    public String toString() {
        return getNombreCliente();
    }
}
