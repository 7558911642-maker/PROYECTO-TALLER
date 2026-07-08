/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.ClienteDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.SerieComprobanteDAO;
import LOGICA.ClienteClass;
import LOGICA.DetalleVentaClass;
import LOGICA.PagoVentaClass;
import LOGICA.ProductoClass;
import LOGICA.SerieComprobanteClass;
import LOGICA.VentaClass;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NuevaVenta extends javax.swing.JInternalFrame {

    private ClienteDAO clienteDAO;
    private ProductoDAO productoDAO;
    private PedidoDAO pedidoDAO;
    private SerieComprobanteDAO serieDAO;
    private DefaultTableModel modeloDetalle;
    private ClienteClass clienteSeleccionado;
    private ProductoClass productoSeleccionado;
    private SerieComprobanteClass serieActual;
    private String tipoComprobante = "BOLETA";
    
    public NuevaVenta() {
        initComponents();
        clienteDAO = new ClienteDAO();
        productoDAO = new ProductoDAO();
        pedidoDAO = new PedidoDAO();
        serieDAO = new SerieComprobanteDAO();
        configurarTablaDetalle();
        prepararVentaInicial();
    }

    private void configurarTablaDetalle() {
        String[] columnas = {"ID Medicamento", "Código", "Producto", "Cantidad", "Precio", "Descuento", "Subtotal", "Stock"};
        modeloDetalle = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDetalleVenta.setModel(modeloDetalle);
    }

    private void prepararVentaInicial() {
        jDateChooser1.setDate(new java.util.Date());
        if (txtusuario.getText().trim().isEmpty()) {
            txtusuario.setText("1");
        }
        txtsubtotal.setEditable(false);
        txtigv.setEditable(false);
        txtVuelto.setEditable(false);
        seleccionarComprobante("BOLETA");
        actualizarTotales();
    }

    private void seleccionarComprobante(String tipo) {
        tipoComprobante = tipo;
        serieActual = serieDAO.buscarSerieActiva(tipo);
        if (serieActual != null) {
            txtSerie.setText(serieActual.getSerie());
            txtComprobante.setText(String.valueOf(pedidoDAO.obtenerSiguienteCorrelativo(serieActual.getIdSerie())));
        } else {
            txtSerie.setText(tipo.equals("FACTURA") ? "F001" : "B001");
            txtComprobante.setText("1");
        }
    }

    private void buscarCliente() {
        String documento = txtruc.getText().trim();
        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el documento del cliente.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtruc.requestFocus();
            return;
        }

        clienteSeleccionado = clienteDAO.buscarPorDocumento(documento);
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un cliente con ese documento.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String nombreCliente = clienteSeleccionado.getTipoCliente() != null && clienteSeleccionado.getTipoCliente().equals("EMPRESA")
                ? clienteSeleccionado.getRazonSocial()
                : (valor(clienteSeleccionado.getNombres()) + " " + valor(clienteSeleccionado.getApellidos())).trim();

        txtrazonsocial.setText(nombreCliente);
        txtDireccion.setText(clienteSeleccionado.getDireccionFiscal() != null ? clienteSeleccionado.getDireccionFiscal() : clienteSeleccionado.getDireccion());
    }

    private void buscarProductoPorCodigo() {
        String codigo = txtCodigoProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del producto.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCodigoProducto.requestFocus();
            return;
        }

        productoSeleccionado = productoDAO.buscarPorCodigo(codigo);
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un medicamento con ese código.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!"Activo".equalsIgnoreCase(productoSeleccionado.getEstado())) {
            JOptionPane.showMessageDialog(this, "El medicamento seleccionado está inactivo.", "Validación", JOptionPane.WARNING_MESSAGE);
            productoSeleccionado = null;
            return;
        }

        txtProducto.setText(productoSeleccionado.getNombreComercial());
        txtPrecio.setText(productoSeleccionado.getPrecio().toPlainString());
    }

    private void agregarProducto() {
        if (productoSeleccionado == null || !txtCodigoProducto.getText().trim().equals(productoSeleccionado.getCodigo())) {
            buscarProductoPorCodigo();
        }
        if (productoSeleccionado == null) {
            return;
        }

        String cantidadTexto = txtCantidad.getText().trim();
        if (cantidadTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cantidad.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCantidad.requestFocus();
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (cantidad > productoSeleccionado.getStock()) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock disponible: " + productoSeleccionado.getStock(), "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal precio = new BigDecimal(txtPrecio.getText().trim());
            BigDecimal descuento = txtDescuento.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtDescuento.getText().trim());
            BigDecimal subtotalProducto = precio.multiply(BigDecimal.valueOf(cantidad)).subtract(descuento);

            if (subtotalProducto.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El subtotal del producto debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            modeloDetalle.addRow(new Object[]{
                productoSeleccionado.getIdMedicamento(),
                productoSeleccionado.getCodigo(),
                productoSeleccionado.getNombreComercial(),
                cantidad,
                precio,
                descuento,
                subtotalProducto,
                productoSeleccionado.getStock()
            });

            limpiarProductoVenta();
            actualizarTotales();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad, precio y descuento deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarProductoSeleccionado() {
        int fila = tblDetalleVenta.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modeloDetalle.removeRow(fila);
        actualizarTotales();
    }

    private void actualizarTotales() {
        BigDecimal totalProductos = BigDecimal.ZERO;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            totalProductos = totalProductos.add(new BigDecimal(modeloDetalle.getValueAt(i, 6).toString()));
        }

        BigDecimal descuentoGeneral = BigDecimal.ZERO;
        if (!txtDescuento.getText().trim().isEmpty()) {
            try {
                descuentoGeneral = new BigDecimal(txtDescuento.getText().trim());
            } catch (NumberFormatException e) {
                descuentoGeneral = BigDecimal.ZERO;
            }
        }

        BigDecimal total = totalProductos.subtract(descuentoGeneral);
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
        BigDecimal base = total.divide(new BigDecimal("1.18"), 2, RoundingMode.HALF_UP);
        BigDecimal igv = total.subtract(base).setScale(2, RoundingMode.HALF_UP);

        txtsubtotal.setText(base.toPlainString());
        txtigv.setText(igv.toPlainString());
        jlbTotalPagar.setText(total.setScale(2, RoundingMode.HALF_UP).toPlainString());
        calcularVuelto();
    }

    private void calcularVuelto() {
        try {
            BigDecimal total = new BigDecimal(jlbTotalPagar.getText().trim().isEmpty() ? "0" : jlbTotalPagar.getText().trim());
            BigDecimal recibido = txtMontoRecibido.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtMontoRecibido.getText().trim());
            txtVuelto.setText(recibido.subtract(total).setScale(2, RoundingMode.HALF_UP).toPlainString());
        } catch (NumberFormatException e) {
            txtVuelto.setText("0.00");
        }
    }

    private void guardarVenta() {
        if (clienteSeleccionado == null) {
            buscarCliente();
        }
        if (clienteSeleccionado == null) {
            return;
        }
        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la venta.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal total = new BigDecimal(jlbTotalPagar.getText().trim());
            BigDecimal efectivo = txtMontoRecibido.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtMontoRecibido.getText().trim());
            if (efectivo.compareTo(total) < 0) {
                JOptionPane.showMessageDialog(this, "El monto recibido no puede ser menor al total.", "Validación", JOptionPane.WARNING_MESSAGE);
                txtMontoRecibido.requestFocus();
                return;
            }

            VentaClass venta = new VentaClass();
            venta.setNumeroVenta("VTA-" + System.currentTimeMillis());
            venta.setIdSerie(serieActual != null ? serieActual.getIdSerie() : (tipoComprobante.equals("FACTURA") ? 2 : 1));
            venta.setNumeroComprobante(Integer.parseInt(txtComprobante.getText().trim()));
            venta.setIdCliente(clienteSeleccionado.getIdCliente());
            venta.setIdUsuario(Integer.parseInt(txtusuario.getText().trim().isEmpty() ? "1" : txtusuario.getText().trim()));
            venta.setClienteTipo(clienteSeleccionado.getTipoCliente());
            venta.setClienteDocumento(clienteSeleccionado.getNumeroDocumento());
            venta.setClienteNombre(txtrazonsocial.getText().trim());
            venta.setClienteDireccion(txtDireccion.getText().trim());
            venta.setSubtotal(new BigDecimal(txtsubtotal.getText().trim()));
            venta.setDescuento(BigDecimal.ZERO);
            venta.setPorcentajeIgv(new BigDecimal("18.00"));
            venta.setIgv(new BigDecimal(txtigv.getText().trim()));
            venta.setTotal(total);
            venta.setEfectivo(efectivo);
            venta.setVuelto(efectivo.subtract(total));
            venta.setEstado("Pagada");
            venta.setObservaciones(txaObservaciones.getText().trim());

            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                DetalleVentaClass detalle = new DetalleVentaClass();
                detalle.setIdMedicamento(Integer.parseInt(modeloDetalle.getValueAt(i, 0).toString()));
                detalle.setCantidad(Integer.parseInt(modeloDetalle.getValueAt(i, 3).toString()));
                detalle.setPrecioUnitario(new BigDecimal(modeloDetalle.getValueAt(i, 4).toString()));
                detalle.setCostoUnitario(BigDecimal.ZERO);
                detalle.setDescuento(new BigDecimal(modeloDetalle.getValueAt(i, 5).toString()));
                detalle.setSubtotal(new BigDecimal(modeloDetalle.getValueAt(i, 6).toString()));
                venta.agregarDetalle(detalle);
            }

            PagoVentaClass pago = new PagoVentaClass();
            pago.setIdMetodoPago(obtenerMetodoPago());
            pago.setMonto(total);
            pago.setEstado("Aprobado");
            venta.agregarPago(pago);

            if (pedidoDAO.registrarVenta(venta)) {
                JOptionPane.showMessageDialog(this, "Venta registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarVenta();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique los valores numéricos de la venta.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int obtenerMetodoPago() {
        String metodo = jComboBox1.getSelectedItem() == null ? "Efectivo" : jComboBox1.getSelectedItem().toString().toLowerCase();
        if (metodo.contains("yape")) return 5;
        if (metodo.contains("tarjeta")) return 2;
        return 1;
    }

    private void limpiarProductoVenta() {
        txtCodigoProducto.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        productoSeleccionado = null;
        txtCodigoProducto.requestFocus();
    }

    private void limpiarVenta() {
        modeloDetalle.setRowCount(0);
        clienteSeleccionado = null;
        productoSeleccionado = null;
        txtruc.setText("");
        txtrazonsocial.setText("");
        txtDireccion.setText("");
        limpiarProductoVenta();
        txtDescuento.setText("");
        txtMontoRecibido.setText("");
        txtVuelto.setText("");
        txaObservaciones.setText("");
        prepararVentaInicial();
    }

    private String valor(String texto) {
        return texto == null ? "" : texto;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelDatos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        txtComprobante = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnEliminar2 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        panelDetalle = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoProducto = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtProducto = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        panelTabla = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleVenta = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        panelTotales = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        panelDatos1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtruc = new javax.swing.JTextField();
        txtrazonsocial = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtDireccion = new javax.swing.JTextField();
        panelTotales1 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtVuelto = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtMontoRecibido = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jlbTotalPagar = new javax.swing.JLabel();
        txtigv = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelarVenta = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        panelPrincipal.setBackground(new java.awt.Color(237, 246, 254));

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 100, 248));
        jLabel2.setText("1. Datos de la Venta:");

        jLabel3.setText("Serie");

        jLabel4.setText("N° Comprobante:");

        jLabel6.setText("Fecha:");

        jLabel7.setText("Usuario:");

        btnEliminar2.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar2.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar2.setText("Factura");

        btnEliminar3.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar3.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar3.setText("Boleta");

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnEliminar3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEliminar2)
                                .addComponent(btnEliminar3))))
                    .addComponent(jLabel2))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        panelDetalle.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(11, 60, 104));
        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 100, 248));
        jLabel8.setText("3. Agregar Productos");

        jLabel9.setText("Código:");

        jLabel10.setText("Precio (S/):");

        jLabel11.setText("Cantidad:");

        jLabel12.setText("Producto:");

        btnAgregar.setBackground(new java.awt.Color(0, 100, 248));
        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");

        javax.swing.GroupLayout panelDetalleLayout = new javax.swing.GroupLayout(panelDetalle);
        panelDetalle.setLayout(panelDetalleLayout);
        panelDetalleLayout.setHorizontalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel9)
                        .addGap(32, 32, 32)
                        .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnAgregar))
                    .addGroup(panelDetalleLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel8)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDetalleLayout.setVerticalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        panelTabla.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 100, 248));
        jLabel14.setText("4. Detalle de venta");

        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblDetalleVenta);

        jLabel33.setForeground(new java.awt.Color(0, 100, 248));
        jLabel33.setText("Total de Productos:");

        jLabel34.setText("0");

        javax.swing.GroupLayout panelTablaLayout = new javax.swing.GroupLayout(panelTabla);
        panelTabla.setLayout(panelTablaLayout);
        panelTablaLayout.setHorizontalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 851, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(62, 62, 62))
        );
        panelTablaLayout.setVerticalGroup(
            panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addContainerGap())
        );

        panelTotales.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 100, 248));
        jLabel15.setText("5. Observaciones");

        txaObservaciones.setColumns(20);
        txaObservaciones.setRows(5);
        jScrollPane2.setViewportView(txaObservaciones);

        javax.swing.GroupLayout panelTotalesLayout = new javax.swing.GroupLayout(panelTotales);
        panelTotales.setLayout(panelTotalesLayout);
        panelTotalesLayout.setHorizontalGroup(
            panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalesLayout.createSequentialGroup()
                .addGroup(panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTotalesLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel15))
                    .addGroup(panelTotalesLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTotalesLayout.setVerticalGroup(
            panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 255));
        jLabel20.setText("<html> <table cellpadding=\"0\" cellspacing=\"0\">     <tr>        \n <td style=\"padding-left:18px;\">           \n  <span style=\"font-family:Segoe UI; font-size:16px; color:#075DEB;\">          \n       <b>Nueva Venta</b>             </span>             <br>        \n     <span style=\"font-family:Segoe UI; font-size:13px; color:#5A6E8C;\">         \n        Registre una nueva Venta         </span>         </td>     </tr>     <tr>    \n     <td>             <hr color=\"#0066FF\" size=\"1\">         </td>     </tr> </table> </html>");

        panelDatos1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 100, 248));
        jLabel21.setText("2. Datos del Cliente (Factura)");

        jLabel22.setText("Ruc:");

        jLabel23.setText("Razon social:");

        jLabel24.setText("Direccion Fiscal:");

        btnBuscar.setBackground(new java.awt.Color(0, 100, 248));
        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar.setText("Buscar");

        javax.swing.GroupLayout panelDatos1Layout = new javax.swing.GroupLayout(panelDatos1);
        panelDatos1.setLayout(panelDatos1Layout);
        panelDatos1Layout.setHorizontalGroup(
            panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatos1Layout.createSequentialGroup()
                .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatos1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel21))
                    .addGroup(panelDatos1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDatos1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(24, 24, 24)
                                .addComponent(txtDireccion))
                            .addGroup(panelDatos1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txtrazonsocial, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, 31))
        );
        panelDatos1Layout.setVerticalGroup(
            panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatos1Layout.createSequentialGroup()
                .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatos1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtruc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar)))
                    .addGroup(panelDatos1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtrazonsocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDatos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTotales1.setBackground(new java.awt.Color(255, 255, 255));
        panelTotales1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 100, 248));
        jLabel26.setText("6. Pago:");

        jLabel27.setText("Subtotal.");

        jLabel28.setText("Descuento:");

        jLabel30.setText("Monto recibido:");

        jLabel31.setText("Vuelto:");

        jLabel32.setText("Total a Pagar:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Yape" }));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jlbTotalPagar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 36)); // NOI18N
        jlbTotalPagar.setForeground(new java.awt.Color(0, 100, 248));
        jlbTotalPagar.setText("0");

        jLabel36.setText("IGV:");

        jLabel37.setText("Metodo Pago:");

        javax.swing.GroupLayout panelTotales1Layout = new javax.swing.GroupLayout(panelTotales1);
        panelTotales1.setLayout(panelTotales1Layout);
        panelTotales1Layout.setHorizontalGroup(
            panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotales1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTotales1Layout.createSequentialGroup()
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addGap(32, 32, 32)
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtsubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                    .addComponent(txtDescuento)
                                    .addComponent(txtigv)))
                            .addComponent(jLabel36))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(54, 54, 54)
                                .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel37))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMontoRecibido))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel32))
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jlbTotalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelTotales1Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelTotales1Layout.setVerticalGroup(
            panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotales1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTotales1Layout.createSequentialGroup()
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtMontoRecibido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)))
                    .addGroup(panelTotales1Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel28)
                                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel36)
                                    .addComponent(txtigv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelTotales1Layout.createSequentialGroup()
                                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbTotalPagar))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(jSeparator1))))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnGuardar.setBackground(new java.awt.Color(0, 100, 248));
        btnGuardar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar y cobrar");

        btnCancelarVenta.setBackground(new java.awt.Color(237, 246, 254));
        btnCancelarVenta.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnCancelarVenta.setForeground(new java.awt.Color(0, 100, 248));
        btnCancelarVenta.setText("Cancelar");
        btnCancelarVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 248), 2, true));
        btnCancelarVenta.addActionListener(this::btnCancelarVentaActionPerformed);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XJ96v-logo.png"))); // NOI18N

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(panelTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelTotales1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatos1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnGuardar)
                .addGap(71, 71, 71))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDatos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTotales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTotales1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelarVenta))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarVentaActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarVentaActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregarProducto();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarCliente();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        seleccionarComprobante("FACTURA");
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void btnEliminar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar3ActionPerformed
        seleccionarComprobante("BOLETA");
    }//GEN-LAST:event_btnEliminar3ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarVenta();
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarVenta;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel jlbTotalPagar;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelDatos1;
    private javax.swing.JPanel panelDetalle;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelTotales;
    private javax.swing.JPanel panelTotales1;
    private javax.swing.JTable tblDetalleVenta;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtComprobante;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtMontoRecibido;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtVuelto;
    private javax.swing.JTextField txtigv;
    private javax.swing.JTextField txtrazonsocial;
    private javax.swing.JTextField txtruc;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
