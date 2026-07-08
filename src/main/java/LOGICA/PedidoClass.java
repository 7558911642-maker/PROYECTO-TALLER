package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PedidoClass extends VentaClass {
    private Date fechaEntrega;
    private Date fechaEnvio;
    private String formaEnvio;

    public PedidoClass() {
        super();
    }

    public PedidoClass(int idPedido, int idCliente, int idEmpleado, Date fechaPedido, Date fechaEntrega, Date fechaEnvio, String formaEnvio, BigDecimal montoPagado, String estado) {
        this();
        setIdPedido(idPedido);
        setIdCliente(idCliente);
        setIdEmpleado(idEmpleado);
        setFechaPedido(fechaPedido);
        this.fechaEntrega = fechaEntrega;
        this.fechaEnvio = fechaEnvio;
        this.formaEnvio = formaEnvio;
        setMontoPagado(montoPagado);
        setEstado(estado);
    }

    public int getIdPedido() {
        return (int) getIdVenta();
    }

    public void setIdPedido(int idPedido) {
        setIdVenta(idPedido);
    }

    public int getIdEmpleado() {
        return getIdUsuario();
    }

    public void setIdEmpleado(int idEmpleado) {
        setIdUsuario(idEmpleado);
    }

    public Date getFechaPedido() {
        Timestamp fecha = getFecha();
        return fecha != null ? new Date(fecha.getTime()) : null;
    }

    public void setFechaPedido(Date fechaPedido) {
        setFecha(fechaPedido != null ? new Timestamp(fechaPedido.getTime()) : null);
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public BigDecimal getMontoPagado() {
        return getEfectivo();
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        setEfectivo(montoPagado);
    }
}
