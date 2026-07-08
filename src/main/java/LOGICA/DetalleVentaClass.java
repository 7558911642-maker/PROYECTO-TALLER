package LOGICA;

import java.math.BigDecimal;

public class DetalleVentaClass {
    private long idDetalleVenta;
    private long idVenta;
    private int idMedicamento;
    private Long idLote;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal costoUnitario;
    private BigDecimal descuento;
    private BigDecimal subtotal;

    // Campos auxiliares para mostrar en JTable.
    private String codigoMedicamento;
    private String nombreMedicamento;
    private String numeroLote;

    public DetalleVentaClass() {
        this.precioUnitario = BigDecimal.ZERO;
        this.costoUnitario = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.subtotal = BigDecimal.ZERO;
    }

    public long getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(long idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getCodigoMedicamento() {
        return codigoMedicamento;
    }

    public void setCodigoMedicamento(String codigoMedicamento) {
        this.codigoMedicamento = codigoMedicamento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public void calcularSubtotal() {
        BigDecimal bruto = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        this.subtotal = bruto.subtract(descuento != null ? descuento : BigDecimal.ZERO);
    }
}
