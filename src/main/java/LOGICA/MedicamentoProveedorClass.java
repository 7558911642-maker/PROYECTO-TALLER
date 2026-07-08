package LOGICA;

import java.math.BigDecimal;

public class MedicamentoProveedorClass {
    private int idMedicamento;
    private int idProveedor;
    private String codigoProveedor;
    private BigDecimal ultimoCosto;
    private boolean proveedorPrincipal;
    private String estado;
    private String medicamentoNombre;
    private String proveedorNombre;

    public MedicamentoProveedorClass() {
        this.ultimoCosto = BigDecimal.ZERO;
        this.estado = "Activo";
    }

    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public String getCodigoProveedor() { return codigoProveedor; }
    public void setCodigoProveedor(String codigoProveedor) { this.codigoProveedor = codigoProveedor; }
    public BigDecimal getUltimoCosto() { return ultimoCosto; }
    public void setUltimoCosto(BigDecimal ultimoCosto) { this.ultimoCosto = ultimoCosto; }
    public boolean isProveedorPrincipal() { return proveedorPrincipal; }
    public boolean getProveedorPrincipal() { return proveedorPrincipal; }
    public void setProveedorPrincipal(boolean proveedorPrincipal) { this.proveedorPrincipal = proveedorPrincipal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMedicamentoNombre() { return medicamentoNombre; }
    public void setMedicamentoNombre(String medicamentoNombre) { this.medicamentoNombre = medicamentoNombre; }
    public String getProveedorNombre() { return proveedorNombre; }
    public void setProveedorNombre(String proveedorNombre) { this.proveedorNombre = proveedorNombre; }
}
