package LOGICA;

import java.sql.Timestamp;

public class UsuarioClass {
    private int idUsuario;
    private String usuario;
    private String contrasena;
    private String nombres;
    private String rol;
    private String estado;
    private Timestamp ultimoAcceso;
    private int intentosFallidos;
    private boolean requiereCambioClave;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public UsuarioClass() {
        this.rol = "Vendedor";
        this.estado = "Activo";
        this.requiereCambioClave = true;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasenia() {
        return contrasena;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasena = contrasenia;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombre() {
        return nombres;
    }

    public void setNombre(String nombre) {
        this.nombres = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Timestamp ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    public boolean isRequiereCambioClave() {
        return requiereCambioClave;
    }

    public boolean getRequiereCambioClave() {
        return requiereCambioClave;
    }

    public void setRequiereCambioClave(boolean requiereCambioClave) {
        this.requiereCambioClave = requiereCambioClave;
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

    public boolean estaActivo() {
        return "Activo".equalsIgnoreCase(estado);
    }

    @Override
    public String toString() {
        return nombres != null ? nombres : "";
    }
}
