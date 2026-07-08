package LOGICA;

import java.math.BigDecimal;

public class DetallePedidoClass extends DetalleVentaClass {
    public DetallePedidoClass() {
        super();
    }

    public DetallePedidoClass(int idPedido, int idProducto, BigDecimal precioUnidad, int cantidad) {
        this();
        setIdPedido(idPedido);
        setIdProducto(idProducto);
        setPrecioUnidad(precioUnidad);
        setCantidad(cantidad);
        calcularSubtotal();
    }

    public int getIdPedido() {
        return (int) getIdVenta();
    }

    public void setIdPedido(int idPedido) {
        setIdVenta(idPedido);
    }

    public int getIdProducto() {
        return getIdMedicamento();
    }

    public void setIdProducto(int idProducto) {
        setIdMedicamento(idProducto);
    }

    public BigDecimal getPrecioUnidad() {
        return getPrecioUnitario();
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        setPrecioUnitario(precioUnidad);
    }
}
