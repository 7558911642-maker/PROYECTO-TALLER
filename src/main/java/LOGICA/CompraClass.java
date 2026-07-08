package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CompraClass {
    private long idCompra;
    private String numeroCompra;
    private Timestamp fechaPedido;
    private Timestamp fechaRecepcion;
    private int idProveedor;
    private int idUsuario;
    private String documentoProveedor;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal igv;
    private BigDecimal total;
    private String estado;
    private String observaciones;
    private String motivoAnulacion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private String proveedorNombre;
    private List<DetalleCompraClass> detalles;

    public CompraClass() {
        this.fechaPedido = new Timestamp(System.currentTimeMillis());
        this.subtotal = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.igv = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.estado = "Pendiente";
        this.detalles = new ArrayList<>();
    }

    public long getIdCompra() { return idCompra; }
    public void setIdCompra(long idCompra) { this.idCompra = idCompra; }
    public String getNumeroCompra() { return numeroCompra; }
    public void setNumeroCompra(String numeroCompra) { this.numeroCompra = numeroCompra; }
    public Timestamp getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Timestamp fechaPedido) { this.fechaPedido = fechaPedido; }
    public Timestamp getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(Timestamp fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getDocumentoProveedor() { return documentoProveedor; }
    public void setDocumentoProveedor(String documentoProveedor) { this.documentoProveedor = documentoProveedor; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public BigDecimal getDescuento() { return descuento; }
    public void setDescuento(BigDecimal descuento) { this.descuento = descuento; }
    public BigDecimal getIgv() { return igv; }
    public void setIgv(BigDecimal igv) { this.igv = igv; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getMotivoAnulacion() { return motivoAnulacion; }
    public void setMotivoAnulacion(String motivoAnulacion) { this.motivoAnulacion = motivoAnulacion; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }
    public Timestamp getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }
    public String getProveedorNombre() { return proveedorNombre; }
    public void setProveedorNombre(String proveedorNombre) { this.proveedorNombre = proveedorNombre; }
    public List<DetalleCompraClass> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCompraClass> detalles) { this.detalles = detalles; }
    public void agregarDetalle(DetalleCompraClass detalle) { this.detalles.add(detalle); }
}
