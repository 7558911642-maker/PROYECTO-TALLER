package FORMS;

import DAO.PedidoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionarVentas extends javax.swing.JInternalFrame {

    private PedidoDAO pedidoDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;
    
    public GestionarVentas() {
        initComponents();
        pedidoDAO = new PedidoDAO();
        configurarTabla();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "N° Venta", "Comprobante", "Fecha", "Cliente", "Usuario", "Subtotal", "Descuento", "IGV", "Total", "Estado", "Ver Detalle", "Anular"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblGestionVentas.setModel(modeloTabla);
        tblGestionVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblGestionVentasMouseClicked(e);
            }
        });
    }

    private void mostrarVentas(List<Object[]> ventas) {
        modeloTabla.setRowCount(0);
        BigDecimal totalVentas = BigDecimal.ZERO;
        int ventasAnuladas = 0;

        for (Object[] v : ventas) {
            modeloTabla.addRow(new Object[]{
                v[0], v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8], v[9], v[10],
                "<html><font color='blue'>Detalle</font></html>",
                "<html><font color='red'>Anular</font></html>"
            });

            if ("Anulada".equalsIgnoreCase(valor(v[10]))) ventasAnuladas++;
            else totalVentas = totalVentas.add(toBigDecimal(v[9]));
        }

        txtTotalVentas.setText(String.valueOf(ventas.size()));
        txtVentasAnuladas.setText(String.valueOf(ventasAnuladas));
        txtTotalGanancias.setText(totalVentas.toString());
        filaSeleccionada = -1;
    }

    private void buscarVentasPorFecha() {
        Date inicio = fchInicio.getDate();
        Date fin = fchFin.getDate();

        if (inicio == null || fin == null) {
            JOptionPane.showMessageDialog(this, "Seleccione fecha de inicio y fecha de fin.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (inicio.after(fin)) {
            JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser mayor que la fecha de fin.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Object[]> ventas = pedidoDAO.consultarVentasPorFechas(inicio, fin);
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron ventas en el rango seleccionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            mostrarVentas(ventas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verDetalleVenta() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idVenta = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        List<Object[]> detalles = pedidoDAO.buscarDetalleVenta(idVenta);

        String[] columnas = {"ID Detalle", "ID Medicamento", "Código", "Medicamento", "Cantidad", "Precio", "Costo", "Descuento", "Subtotal"};
        DefaultTableModel modeloDetalle = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] d : detalles) modeloDetalle.addRow(d);

        JTable tablaDetalle = new JTable(modeloDetalle);
        JScrollPane scroll = new JScrollPane(tablaDetalle);
        scroll.setPreferredSize(new java.awt.Dimension(850, 300));
        JOptionPane.showMessageDialog(this, scroll, "Detalle de venta N° " + idVenta, JOptionPane.INFORMATION_MESSAGE);
    }

    private void anularVenta() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una venta para anular.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idVenta = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        String estado = valor(modeloTabla.getValueAt(filaSeleccionada, 10));

        if ("Anulada".equalsIgnoreCase(estado)) {
            JOptionPane.showMessageDialog(this, "La venta ya se encuentra anulada.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de anular la venta seleccionada?", "Confirmar anulación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (pedidoDAO.anularVenta(idVenta)) {
                JOptionPane.showMessageDialog(this, "Venta anulada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                buscarVentasPorFecha();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo anular la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tblGestionVentasMouseClicked(MouseEvent evt) {
        filaSeleccionada = tblGestionVentas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int columna = tblGestionVentas.getSelectedColumn();
            if (columna == 11) verDetalleVenta();
            else if (columna == 12) anularVenta();
            else if (evt.getClickCount() == 2) verDetalleVenta();
        }
    }

    private BigDecimal toBigDecimal(Object valor) {
        if (valor == null) return BigDecimal.ZERO;
        if (valor instanceof BigDecimal) return (BigDecimal) valor;
        try { return new BigDecimal(valor.toString()); } catch (Exception e) { return BigDecimal.ZERO; }
    }

    private String valor(Object dato) {
        return dato == null ? "" : dato.toString();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGestionVentas = new javax.swing.JTable();
        btnBuscar1 = new javax.swing.JButton();
        fchFin = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        fchInicio = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        txtTotalGanancias = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTotalVentas = new javax.swing.JTextField();
        txtVentasAnuladas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionVentas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionVentas);

        btnBuscar1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(this::btnBuscar1ActionPerformed);

        fchFin.setDateFormatString("yyyy-MM-dd");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("fecha de fin:");

        fchInicio.setDateFormatString("yyyy-MM-dd");

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel1.setText("fecha de inicio:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 678, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(fchInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(83, 83, 83)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(fchFin, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar1)
                        .addGap(57, 57, 57))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fchInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fchFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnBuscar1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText(" Total Ganancia ");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Total ventas");

        txtTotalVentas.addActionListener(this::txtTotalVentasActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel6.setText("Ventas Anuladas");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Ventas</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Controle de Ventas</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText("VOLVER");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(txtTotalGanancias, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtVentasAnuladas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(123, 123, 123))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addComponent(btnVolver)
                .addGap(87, 87, 87))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalGanancias, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtVentasAnuladas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTotalVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
        buscarVentasPorFecha();
    }
    private void txtTotalRecaudadoActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnVolver;
    private com.toedter.calendar.JDateChooser fchFin;
    private com.toedter.calendar.JDateChooser fchInicio;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblGestionVentas;
    private javax.swing.JTextField txtTotalGanancias;
    private javax.swing.JTextField txtTotalVentas;
    private javax.swing.JTextField txtVentasAnuladas;
    // End of variables declaration//GEN-END:variables

    private void txtTotalVentasActionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
