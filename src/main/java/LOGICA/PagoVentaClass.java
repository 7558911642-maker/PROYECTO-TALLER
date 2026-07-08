package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PagoVentaClass {
    private long idPago;
    private long idVenta;
    private int idMetodoPago;
    private BigDecimal monto;
    private String referencia;
    private String estado;
    private Timestamp fechaPago;
    private String metodoPagoNombre;

    public PagoVentaClass() {
        this.monto = BigDecimal.ZERO;
        this.estado = "Aprobado";
        this.fechaPago = new Timestamp(System.currentTimeMillis());
    }

    public long getIdPago() { return idPago; }
    public void setIdPago(long idPago) { this.idPago = idPago; }
    public long getIdVenta() { return idVenta; }
    public void setIdVenta(long idVenta) { this.idVenta = idVenta; }
    public int getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getFechaPago() { return fechaPago; }
    public void setFechaPago(Timestamp fechaPago) { this.fechaPago = fechaPago; }
    public String getMetodoPagoNombre() { return metodoPagoNombre; }
    public void setMetodoPagoNombre(String metodoPagoNombre) { this.metodoPagoNombre = metodoPagoNombre; }
}
