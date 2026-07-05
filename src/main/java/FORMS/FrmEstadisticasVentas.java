package FORMS;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FrmEstadisticasVentas extends javax.swing.JInternalFrame {

    DefaultTableModel modeloProductos;
    DefaultTableModel modeloClientes;
    DAO.PedidoDAO pedidoDAO = new DAO.PedidoDAO();
    
    public FrmEstadisticasVentas() {
        initComponents();
        this.setTitle("Módulo de Analítica y Estadísticas Gerenciales");
        configurarTablas();
        cargarDatosEstadisticos();
    }

    private void configurarTablas() {
        // Configuración de la tabla de productos (Top 5)
        String[] cabeceraProd = {"Código", "Medicamento / Fármaco", "Unidades Vendidas"};
        modeloProductos = new DefaultTableModel(null, cabeceraProd) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTopProductos1.setModel(modeloProductos);

        // Configuración de la tabla de clientes
        String[] cabeceraCli = {"Documento (DNI/RUC)", "Cliente / Asegurado", "N° Visitas/Compras", "Total Invertido (S/.)"};
        modeloClientes = new DefaultTableModel(null, cabeceraCli) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEstadisticasClientes1.setModel(modeloClientes);
    }
    
    private void cargarDatosEstadisticos() {
        // 1. Cargar Top 5 Productos
        modeloProductos.setRowCount(0);
        List<Object[]> topProductos = pedidoDAO.obtenerTop5Medicamentos();
        for (Object[] fila : topProductos) {
            modeloProductos.addRow(fila);
        }

        // 2. Cargar Estadísticas Clientes
        modeloClientes.setRowCount(0);
        List<Object[]> estadisticasCli = pedidoDAO.obtenerEstadisticasClientes();
        for (Object[] fila : estadisticasCli) {
            modeloClientes.addRow(fila);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dcFechaInicio3 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaTopProductos1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        btnEliminar11 = new javax.swing.JButton();
        dcFechaInicio4 = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        lblCatInactiva = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCatActivas = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalCat = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCatActivas1 = new javax.swing.JLabel();

        dcFechaInicio3.setDateFormatString("yyyy-MM-dd");

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel9.setText("Fecha:");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 100, 248));
        jLabel4.setText("Top 5 Medicamentos Más Demandados");

        tablaTopProductos1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaTopProductos1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 255));
        jLabel6.setText("<html>\n<table cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td style=\"padding-left:18px;\">\n            <span style=\"font-family:Segoe UI; font-size:22px; color:#075DEB;\">\n                <b>Nuevo Medicamentos</b>\n            </span>\n            <br>\n            <span style=\"font-family:Segoe UI; font-size:13px; color:#5A6E8C;\">\n                Registre un nuevo Medicamento\n            </span>\n        </td>\n    </tr>\n    <tr>\n        <td>\n            <hr color=\"#0066FF\" size=\"1\">\n        </td>\n    </tr>\n</table>\n</html>");

        btnEliminar11.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar11.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar11.setText("Refrescar");
        btnEliminar11.addActionListener(this::btnEliminar11ActionPerformed);

        dcFechaInicio4.setDateFormatString("yyyy-MM-dd");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel10.setText("Fecha:");

        lblCatInactiva.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblCatInactiva.setForeground(new java.awt.Color(245, 165, 36));
        lblCatInactiva.setText("0");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel7.setText("Clientes Atendidos");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText("Unidades Vendidas");

        lblCatActivas.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblCatActivas.setForeground(new java.awt.Color(34, 165, 90));
        lblCatActivas.setText("0");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Ventas Totales");

        lblTotalCat.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblTotalCat.setForeground(new java.awt.Color(0, 100, 248));
        lblTotalCat.setText("0");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel8.setText("tikets Promedio");

        lblCatActivas1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblCatActivas1.setForeground(new java.awt.Color(34, 165, 90));
        lblCatActivas1.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(dcFechaInicio4, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEliminar11)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(lblTotalCat)))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(lblCatActivas)))
                                .addGap(72, 72, 72)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblCatInactiva)
                                        .addGap(55, 55, 55)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblCatActivas1)
                                        .addGap(48, 48, 48)))))
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar11)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(dcFechaInicio4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCat)
                    .addComponent(lblCatActivas)
                    .addComponent(lblCatInactiva)
                    .addComponent(lblCatActivas1))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar11;
    private com.toedter.calendar.JDateChooser dcFechaInicio3;
    private com.toedter.calendar.JDateChooser dcFechaInicio4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCatActivas;
    private javax.swing.JLabel lblCatActivas1;
    private javax.swing.JLabel lblCatInactiva;
    private javax.swing.JLabel lblTotalCat;
    private javax.swing.JTable tablaTopProductos1;
    // End of variables declaration//GEN-END:variables
}
