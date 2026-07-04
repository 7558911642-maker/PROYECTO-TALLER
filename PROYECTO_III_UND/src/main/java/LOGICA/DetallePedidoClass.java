package LOGICA;

import java.math.BigDecimal;

public class DetallePedidoClass {
    
    private int idPedido;
    private int idProducto;
    private BigDecimal precioUnidad; // Precio histórico congelado al momento de la venta
    private int cantidad;

    public DetallePedidoClass() {
    }

    public DetallePedidoClass(int idPedido, int idProducto, BigDecimal precioUnidad, int cantidad) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.precioUnidad = precioUnidad;
        this.cantidad = cantidad;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
