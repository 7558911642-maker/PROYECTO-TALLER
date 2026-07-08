package LOGICA;

import java.math.BigDecimal;
import java.sql.Date;

public class DetalleCompraClass {
    private long idDetalleCompra;
    private long idCompra;
    private int idMedicamento;
    private Long idLote;
    private String numeroLote;
    private Date fechaVencimiento;
    private int cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private String nombreMedicamento;

    public DetalleCompraClass() {
        this.costoUnitario = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }

    public long getIdDetalleCompra() { return idDetalleCompra; }
    public void setIdDetalleCompra(long idDetalleCompra) { this.idDetalleCompra = idDetalleCompra; }
    public long getIdCompra() { return idCompra; }
    public void setIdCompra(long idCompra) { this.idCompra = idCompra; }
    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }
    public Long getIdLote() { return idLote; }
    public void setIdLote(Long idLote) { this.idLote = idLote; }
    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }

    public void calcularSubtotal() {
        BigDecimal bruto = costoUnitario.multiply(BigDecimal.valueOf(cantidad));
        this.subtotal = bruto.subtract(descuento != null ? descuento : BigDecimal.ZERO);
    }
}
