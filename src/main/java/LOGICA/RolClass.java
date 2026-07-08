package LOGICA;

import java.sql.Timestamp;

public class RolClass {
    private int idRol;
    private String nombre;
    private String descripcion;
    private String estado;
    private Timestamp creadoEn;

    public RolClass() {
        this.estado = "Activo";
    }

    public int getIdRol() { return idRol; }
    public void setIdRol(int idRol) { this.idRol = idRol; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
