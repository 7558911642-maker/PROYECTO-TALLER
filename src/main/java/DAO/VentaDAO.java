package DAO;

import LOGICA.VentaClass;
import java.util.List;

public class VentaDAO extends PedidoDAO {

    // REGISTRAR VENTA USANDO EL DAO PRINCIPAL DE VENTAS
    @Override
    public boolean registrarVenta(VentaClass venta) {
        return super.registrarVenta(venta);
    }

    // CONSULTAR VENTAS POR FECHA
    @Override
    public List<Object[]> consultarVentasPorFechas(java.util.Date fechaInicio, java.util.Date fechaFin) {
        return super.consultarVentasPorFechas(fechaInicio, fechaFin);
    }
}
