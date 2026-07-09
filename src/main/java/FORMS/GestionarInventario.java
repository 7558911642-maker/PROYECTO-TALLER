/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;
import DAO.InventarioDAO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author HP
 */
public class GestionarInventario extends javax.swing.JInternalFrame {

    private InventarioDAO inventarioDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;
    /**
     * Creates new form GestionarInventario
     */
    public GestionarInventario() {
        initComponents();
         inventarioDAO = new InventarioDAO();
        configurarTabla();
        cargarDatos();
    }




// 4) MÉTODOS: pegar antes de // Variables declaration
private void configurarTabla() {
    String[] columnas = {"ID", "Código", "Medicamento", "Categoría", "Laboratorio", "Presentación", "P. Compra", "P. Venta", "Stock", "Stock Mín.", "Stock Máx.", "Estado Stock", "Próx. Vencimiento", "Estado"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblGestionInventario.setModel(modeloTabla); // Si tu tabla es tblGestionInventario, cambia aquí.
    tblGestionInventario.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            filaSeleccionada = tblGestionInventario.getSelectedRow();
        }
    });
}


private void mostrarInventario(List<Object[]> inventario) {
    modeloTabla.setRowCount(0);
    int total = 0, stockNormal = 0, stockBajo = 0;

    for (Object[] i : inventario) {
        modeloTabla.addRow(i);
        total++;
        String estadoStock = valor(i[11]);
        if (estadoStock.toLowerCase().contains("bajo") || estadoStock.toLowerCase().contains("sin")) stockBajo++; else stockNormal++;
    }

    txtTotalP.setText(String.valueOf(total));
    txtActivos.setText(String.valueOf(stockNormal));
    txtinactivos.setText(String.valueOf(stockBajo));
    filaSeleccionada = -1;
}

private void cargarDatos() {
    try {
        List<Object[]> inventario = inventarioDAO.listarInventario();
        mostrarInventario(filtrarInventario(inventario));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void buscarInventario() {
    try {
        List<Object[]> inventario = inventarioDAO.listarInventario();
        inventario = filtrarInventario(inventario);
        if (inventario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros de inventario.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        mostrarInventario(inventario);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al buscar inventario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private List<Object[]> filtrarInventario(List<Object[]> inventario) {
    String criterio = txtBuscar.getText().trim().toLowerCase();
    if (criterio.isEmpty()) return inventario;

    java.util.ArrayList<Object[]> filtrados = new java.util.ArrayList<>();
    for (Object[] fila : inventario) {
        String texto = (valor(fila[1]) + " " + valor(fila[2]) + " " + valor(fila[3]) + " " + valor(fila[4]) + " " + valor(fila[11])).toLowerCase();
        if (texto.contains(criterio)) filtrados.add(fila);
    }
    return filtrados;
}

private void imprimirTablaInventario() {
    try {
        tblGestionInventario.print(); // Si tu tabla es tblGestionInventario, cambia aquí.
    } catch (PrinterException e) {
        JOptionPane.showMessageDialog(this, "Error al imprimir: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void exportarInventarioCSV() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar reporte de inventario");
    chooser.setSelectedFile(new File("reporte_inventario.csv"));
    int opcion = chooser.showSaveDialog(this);
    if (opcion == JFileChooser.APPROVE_OPTION) {
        try (PrintWriter pw = new PrintWriter(chooser.getSelectedFile())) {
            for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                pw.print(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 1) pw.print(";");
            }
            pw.println();
            for (int f = 0; f < modeloTabla.getRowCount(); f++) {
                for (int c = 0; c < modeloTabla.getColumnCount(); c++) {
                    pw.print(valor(modeloTabla.getValueAt(f, c)));
                    if (c < modeloTabla.getColumnCount() - 1) pw.print(";");
                }
                pw.println();
            }
            JOptionPane.showMessageDialog(this, "Reporte exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private String valor(Object dato) {
    return dato == null ? "" : dato.toString();
}

// 5) EVENTOS EXISTENTES: reemplazar el contenido


private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
    buscarInventario();
}

private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {
    imprimirTablaInventario();
}

private void btnExportarExelActionPerformed(java.awt.event.ActionEvent evt) {
    exportarInventarioCSV();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGestionInventario = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalP = new javax.swing.JTextField();
        txtActivos = new javax.swing.JTextField();
        txtinactivos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionInventario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionInventario);

        txtBuscar.addActionListener(this::txtBuscarActionPerformed);

        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBuscar)
                        .addGap(57, 57, 57))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText(" Total Productos");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText("Activos");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel6.setText("Inactivos");

        txtTotalP.addActionListener(this::txtTotalPActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Inventario</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Controle de Inventario</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText(" VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(txtTotalP, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(143, 143, 143)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5))
                            .addComponent(txtActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtinactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel6)))
                        .addGap(123, 123, 123))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103)
                .addComponent(btnVolver)
                .addGap(57, 57, 57))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalP, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtinactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(34, Short.MAX_VALUE))
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

    private void txtTotalPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
            cargarDatos();
    }//GEN-LAST:event_btnBuscarActionPerformed


    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
            cargarDatos();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
           dispose();     // TODO add your handling code here:
    }//GEN-LAST:event_btnVolverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblGestionInventario;
    private javax.swing.JTextField txtActivos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtTotalP;
    private javax.swing.JTextField txtinactivos;
    // End of variables declaration//GEN-END:variables
}
