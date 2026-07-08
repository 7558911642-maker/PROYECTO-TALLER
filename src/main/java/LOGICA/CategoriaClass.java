package LOGICA;

import java.sql.Timestamp;

public class CategoriaClass {
    private int idCategoria;
    private String codigo;
    private String nombreCategoria;
    private String descripcion;
    private String estado;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public CategoriaClass() {
        this.estado = "Activo";
    }

    public CategoriaClass(int idCategoria, String codigo, String nombreCategoria, String descripcion, String estado) {
        this.idCategoria = idCategoria;
        this.codigo = codigo;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public boolean esActiva() {
        return "Activo".equalsIgnoreCase(estado);
    }

    @Override
    public String toString() {
        return nombreCategoria != null ? nombreCategoria : "";
    }
}
