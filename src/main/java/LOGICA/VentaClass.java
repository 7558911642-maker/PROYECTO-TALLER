package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VentaClass {
    private long idVenta;
    private String numeroVenta;
    private int idSerie;
    private int numeroComprobante;
    private Timestamp fecha;
    private int idCliente;
    private int idUsuario;
    private String clienteTipo;
    private String clienteDocumento;
    private String clienteNombre;
    private String clienteDireccion;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal porcentajeIgv;
    private BigDecimal igv;
    private BigDecimal total;
    private BigDecimal efectivo;
    private BigDecimal vuelto;
    private String estado;
    private String observaciones;
    private Timestamp fechaAnulacion;
    private String motivoAnulacion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private List<DetalleVentaClass> detalles;
    private List<PagoVentaClass> pagos;

    public VentaClass() {
        this.fecha = new Timestamp(System.currentTimeMillis());
        this.clienteTipo = "PERSONA_NATURAL";
        this.subtotal = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.porcentajeIgv = new BigDecimal("18.00");
        this.igv = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.efectivo = BigDecimal.ZERO;
        this.vuelto = BigDecimal.ZERO;
        this.estado = "Pagada";
        this.detalles = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }

    public int getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(int numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getClienteTipo() {
        return clienteTipo;
    }

    public void setClienteTipo(String clienteTipo) {
        this.clienteTipo = clienteTipo;
    }

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteDireccion() {
        return clienteDireccion;
    }

    public void setClienteDireccion(String clienteDireccion) {
        this.clienteDireccion = clienteDireccion;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getPorcentajeIgv() {
        return porcentajeIgv;
    }

    public void setPorcentajeIgv(BigDecimal porcentajeIgv) {
        this.porcentajeIgv = porcentajeIgv;
    }

    public BigDecimal getIgv() {
        return igv;
    }

    public void setIgv(BigDecimal igv) {
        this.igv = igv;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getVuelto() {
        return vuelto;
    }

    public void setVuelto(BigDecimal vuelto) {
        this.vuelto = vuelto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
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

    public List<DetalleVentaClass> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaClass> detalles) {
        this.detalles = detalles;
    }

    public List<PagoVentaClass> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoVentaClass> pagos) {
        this.pagos = pagos;
    }

    public void agregarDetalle(DetalleVentaClass detalle) {
        if (this.detalles == null) this.detalles = new ArrayList<>();
        this.detalles.add(detalle);
    }

    public void agregarPago(PagoVentaClass pago) {
        if (this.pagos == null) this.pagos = new ArrayList<>();
        this.pagos.add(pago);
    }
}
