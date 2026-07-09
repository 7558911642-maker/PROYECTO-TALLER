/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.CompraDAO;
import DAO.ProductoDAO;
import LOGICA.CompraClass;
import LOGICA.DetalleCompraClass;
import LOGICA.ProductoClass;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class NuevioPedido extends javax.swing.JInternalFrame {

    private CompraDAO compraDAO;
    private ProductoDAO productoDAO;
    private DefaultTableModel modeloDetalle;
    private ProductoClass productoSeleccionado;

    /**
     * Creates new form NuevioPedido
     */
    public NuevioPedido() {
        initComponents();
        compraDAO = new CompraDAO();
        productoDAO = new ProductoDAO();
        configurarTablaDetalleCompra();
        prepararCompraInicial();
    }



    private void configurarTablaDetalleCompra() {
        String[] columnas = {"ID Medicamento", "Código", "Medicamento", "Cantidad", "Costo", "Descuento", "Subtotal", "Lote"};
        modeloDetalle = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDetalleVenta.setModel(modeloDetalle);
    }

    private void prepararCompraInicial() {
        if (txtSerie.getText().trim().isEmpty()) {
            txtSerie.setText("COM-" + System.currentTimeMillis());
        }
        if (txtusuario.getText().trim().isEmpty()) {
            txtusuario.setText("1");
        }
        if (jDateChooser1 != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.YEAR, 1);
            jDateChooser1.setDate(cal.getTime());
        }
        txtsubtotal.setEditable(false);
        txtigv.setEditable(false);
        actualizarTotalesCompra();
    }

    private void buscarMedicamentoCompra() {
        String codigo = txtCodigoProducto.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del medicamento.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCodigoProducto.requestFocus();
            return;
        }

        productoSeleccionado = productoDAO.buscarPorCodigo(codigo);
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un medicamento con ese código.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        txtProducto.setText(productoSeleccionado.getNombreComercial());
        txtPrecio.setText(productoSeleccionado.getPrecioCompra() != null ? productoSeleccionado.getPrecioCompra().toPlainString() : "0.00");
    }

    private void agregarProductoCompra() {
        if (productoSeleccionado == null || !txtCodigoProducto.getText().trim().equals(productoSeleccionado.getCodigo())) {
            buscarMedicamentoCompra();
        }
        if (productoSeleccionado == null) {
            return;
        }

        String cantidadTexto = txtCantidad.getText().trim();
        String costoTexto = txtPrecio.getText().trim();
        if (cantidadTexto.isEmpty() || costoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cantidad y costo unitario son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            BigDecimal costo = new BigDecimal(costoTexto);
            BigDecimal descuento = txtDescuento.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtDescuento.getText().trim());

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (costo.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "El costo unitario debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal subtotal = costo.multiply(BigDecimal.valueOf(cantidad)).subtract(descuento);
            String lote = "LOTE-" + productoSeleccionado.getCodigo() + "-" + System.currentTimeMillis();

            modeloDetalle.addRow(new Object[]{
                productoSeleccionado.getIdMedicamento(),
                productoSeleccionado.getCodigo(),
                productoSeleccionado.getNombreComercial(),
                cantidad,
                costo,
                descuento,
                subtotal,
                lote
            });

            limpiarProductoCompra();
            actualizarTotalesCompra();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad, costo y descuento deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void quitarProductoCompra() {
        int fila = tblDetalleVenta.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del detalle.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modeloDetalle.removeRow(fila);
        actualizarTotalesCompra();
    }

    private void actualizarTotalesCompra() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
            total = total.add(new BigDecimal(modeloDetalle.getValueAt(i, 6).toString()));
        }
        BigDecimal base = total.divide(new BigDecimal("1.18"), 2, RoundingMode.HALF_UP);
        BigDecimal igv = total.subtract(base).setScale(2, RoundingMode.HALF_UP);
        txtsubtotal.setText(base.toPlainString());
        txtigv.setText(igv.toPlainString());
    }

    private void guardarCompra() {
        if (modeloDetalle.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un medicamento al pedido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (txtComprobante.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el ID del proveedor.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtComprobante.requestFocus();
            return;
        }

        try {
            int idProveedor = Integer.parseInt(txtComprobante.getText().trim());
            int idUsuario = Integer.parseInt(txtusuario.getText().trim().isEmpty() ? "1" : txtusuario.getText().trim());
            BigDecimal subtotalBase = new BigDecimal(txtsubtotal.getText().trim());
            BigDecimal igv = new BigDecimal(txtigv.getText().trim());
            BigDecimal total = subtotalBase.add(igv);

            CompraClass compra = new CompraClass();
            compra.setNumeroCompra(txtSerie.getText().trim().isEmpty() ? "COM-" + System.currentTimeMillis() : txtSerie.getText().trim());
            compra.setFechaRecepcion(new Timestamp(System.currentTimeMillis()));
            compra.setIdProveedor(idProveedor);
            compra.setIdUsuario(idUsuario);
            compra.setDocumentoProveedor(txtComprobante.getText().trim());
            compra.setSubtotal(subtotalBase);
            compra.setDescuento(BigDecimal.ZERO);
            compra.setIgv(igv);
            compra.setTotal(total);
            compra.setEstado("Recibida");
            compra.setObservaciones(txaObservaciones.getText().trim());

            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                DetalleCompraClass detalle = new DetalleCompraClass();
                detalle.setIdMedicamento(Integer.parseInt(modeloDetalle.getValueAt(i, 0).toString()));
                detalle.setCantidad(Integer.parseInt(modeloDetalle.getValueAt(i, 3).toString()));
                detalle.setCostoUnitario(new BigDecimal(modeloDetalle.getValueAt(i, 4).toString()));
                detalle.setDescuento(new BigDecimal(modeloDetalle.getValueAt(i, 5).toString()));
                detalle.setSubtotal(new BigDecimal(modeloDetalle.getValueAt(i, 6).toString()));
                detalle.setNumeroLote(modeloDetalle.getValueAt(i, 7).toString());
                detalle.setFechaVencimiento(obtenerFechaVencimientoSQL());
                compra.agregarDetalle(detalle);
            }

            if (compraDAO.registrarCompra(compra)) {
                JOptionPane.showMessageDialog(this, "Pedido/compra registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCompra();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el pedido/compra.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de proveedor, usuario y montos deben ser numéricos.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private java.sql.Date obtenerFechaVencimientoSQL() {
        java.util.Date fecha = jDateChooser1 != null ? jDateChooser1.getDate() : null;
        if (fecha == null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.YEAR, 1);
            fecha = cal.getTime();
        }
        return new java.sql.Date(fecha.getTime());
    }

    private void limpiarProductoCompra() {
        txtCodigoProducto.setText("");
        txtProducto.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtDescuento.setText("");
        productoSeleccionado = null;
        txtCodigoProducto.requestFocus();
    }

    private void limpiarCompra() {
        modeloDetalle.setRowCount(0);
        limpiarProductoCompra();
        txaObservaciones.setText("");
        txtComprobante.setText("");
        txtSerie.setText("COM-" + System.currentTimeMillis());
        prepararCompraInicial();
    }



  

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        agregarProductoCompra();
    }


    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {
        quitarProductoCompra();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        panelTotales1 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        txtigv = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        btnEliminar11 = new javax.swing.JButton();
        btnGuardar6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        panelPrincipal.setBackground(new java.awt.Color(237, 246, 254));

        panelDatos.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 100, 248));
        jLabel2.setText("1. Datos de la Venta:");

        jLabel3.setText("Pedio");

        jLabel4.setText(" Provedor");

        jLabel6.setText("Fecha:");

        jLabel7.setText("Responsable");

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3))
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(24, 24, 24)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel4)
                        .addComponent(jLabel7))
                    .addComponent(jLabel3))
                .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelDatosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        panelDetalle.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(11, 60, 104));
        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 100, 248));
        jLabel8.setText("3. Agregar Productos");

        jLabel9.setText("Código:");

        jLabel10.setText("Costo unitario");

        jLabel11.setText("Cantidad:");

        jLabel12.setText("Producto:");

        btnAgregar.setBackground(new java.awt.Color(0, 100, 248));
        btnAgregar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        javax.swing.GroupLayout panelDetalleLayout = new javax.swing.GroupLayout(panelDetalle);
        panelDetalle.setLayout(panelDetalleLayout);
        panelDetalleLayout.setHorizontalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addGap(29, 29, 29))
        );
        panelDetalleLayout.setVerticalGroup(
            panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDetalleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addGap(16, 16, 16))
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel33)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addGap(88, 88, 88))
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
                .addContainerGap()
                .addGroup(panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelTotalesLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTotalesLayout.setVerticalGroup(
            panelTotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 255));
        jLabel20.setText("<html>\n<body style=\"font-family: sans-serif; background-color: transparent;\">\n    <font color=\"#0022cc\" size=\"5\"><b>Nuevo Pedido a Proveedor</b></font>\n    <br>\n    <font color=\"#667085\" size=\"3\">Registre una nueva compra/pedido</font>\n</body>\n</html>\n");

        panelTotales1.setBackground(new java.awt.Color(255, 255, 255));
        panelTotales1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel26.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 100, 248));
        jLabel26.setText("6. Resumen:");

        jLabel27.setText("Subtotal.");

        jLabel28.setText("Impuestos:");

        jLabel36.setText("Total:");

        javax.swing.GroupLayout panelTotales1Layout = new javax.swing.GroupLayout(panelTotales1);
        panelTotales1.setLayout(panelTotales1Layout);
        panelTotales1Layout.setHorizontalGroup(
            panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotales1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addGroup(panelTotales1Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(32, 32, 32)
                        .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(txtDescuento)
                            .addComponent(txtigv)))
                    .addComponent(jLabel36)
                    .addComponent(jLabel26))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        panelTotales1Layout.setVerticalGroup(
            panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotales1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTotales1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addComponent(txtsubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnEliminar11.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar11.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disk.png"))); // NOI18N
        btnEliminar11.setText("Guardar");
        btnEliminar11.addActionListener(this::btnEliminar11ActionPerformed);

        btnGuardar6.setBackground(new java.awt.Color(237, 246, 254));
        btnGuardar6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnGuardar6.setForeground(new java.awt.Color(0, 100, 248));
        btnGuardar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-24.png"))); // NOI18N
        btnGuardar6.setText("Cancelar");
        btnGuardar6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 248), 2, true));
        btnGuardar6.addActionListener(this::btnGuardar6ActionPerformed);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XJ96v-logo.png"))); // NOI18N

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(panelTotales1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEliminar11)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTotales1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTotales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar11)
                    .addComponent(btnGuardar6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnEliminar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar11ActionPerformed
        guardarCompra();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar11ActionPerformed

    private void btnGuardar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar6ActionPerformed
            dispose();// TODO add your handling code here:
    }//GEN-LAST:event_btnGuardar6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar11;
    private javax.swing.JButton btnGuardar6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelDatos;
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
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtigv;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
