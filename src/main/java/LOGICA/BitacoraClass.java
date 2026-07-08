package LOGICA;

import java.sql.Timestamp;

public class BitacoraClass {
    private long idBitacora;
    private Integer idUsuario;
    private String accion;
    private String modulo;
    private String tablaAfectada;
    private String idRegistro;
    private String detalle;
    private String direccionIp;
    private Timestamp fecha;
    private String usuarioNombre;

    public BitacoraClass() {
        this.fecha = new Timestamp(System.currentTimeMillis());
    }

    public long getIdBitacora() { return idBitacora; }
    public void setIdBitacora(long idBitacora) { this.idBitacora = idBitacora; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }
    public String getTablaAfectada() { return tablaAfectada; }
    public void setTablaAfectada(String tablaAfectada) { this.tablaAfectada = tablaAfectada; }
    public String getIdRegistro() { return idRegistro; }
    public void setIdRegistro(String idRegistro) { this.idRegistro = idRegistro; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public String getDireccionIp() { return direccionIp; }
    public void setDireccionIp(String direccionIp) { this.direccionIp = direccionIp; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getUsuarioNombre() { return usuarioNombre; }
    public void setUsuarioNombre(String usuarioNombre) { this.usuarioNombre = usuarioNombre; }
}
