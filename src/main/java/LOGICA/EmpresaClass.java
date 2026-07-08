package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class EmpresaClass {
    private int idEmpresa;
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String correo;
    private String logo;
    private BigDecimal porcentajeIgv;
    private int diasAlertaVencimiento;
    private String moneda;
    private String estado;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public EmpresaClass() {
        this.porcentajeIgv = new BigDecimal("18.00");
        this.diasAlertaVencimiento = 30;
        this.moneda = "PEN";
        this.estado = "Activo";
    }

    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public BigDecimal getPorcentajeIgv() { return porcentajeIgv; }
    public void setPorcentajeIgv(BigDecimal porcentajeIgv) { this.porcentajeIgv = porcentajeIgv; }
    public int getDiasAlertaVencimiento() { return diasAlertaVencimiento; }
    public void setDiasAlertaVencimiento(int diasAlertaVencimiento) { this.diasAlertaVencimiento = diasAlertaVencimiento; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }
    public Timestamp getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}
