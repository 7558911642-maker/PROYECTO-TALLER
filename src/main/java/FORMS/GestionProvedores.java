/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.ProveedorDAO;
import LOGICA.ProveedorClass;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestionProvedores extends javax.swing.JInternalFrame {

    private ProveedorDAO proveedorDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;;

    public GestionProvedores() {
        initComponents();
        proveedorDAO = new ProveedorDAO();
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "RUC", "Razón Social", "Nombre Comercial", "Teléfono", "Correo", "Dirección", "Estado", "Modificar", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReporteFechas1.setModel(modeloTabla);
        tablaReporteFechas1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablaReporteFechas1MouseClicked(e);
            }
        });
    }
    
    private void mostrarProveedores(List<ProveedorClass> proveedores) {
        modeloTabla.setRowCount(0);
        int total = 0, activos = 0, inactivos = 0;

        for (ProveedorClass p : proveedores) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(),
                p.getRuc(),
                p.getRazonSocial(),
                p.getNombreComercial(),
                p.getTelefono(),
                p.getCorreo(),
                p.getDireccion(),
                p.getEstado(),
                "<html><font color='blue'>Modificar</font></html>",
                "<html><font color='red'>Eliminar</font></html>"
            });
            total++;
            if ("Activo".equalsIgnoreCase(p.getEstado())) activos++; else inactivos++;
        }

        txtTotalProvee.setText(String.valueOf(total));
        txtActivos.setText(String.valueOf(activos));
        txtInactivo.setText(String.valueOf(inactivos));
        filaSeleccionada = -1;
    }
    
    private void cargarDatos() {
        try {
            List<ProveedorClass> proveedores = proveedorDAO.listar();
            mostrarProveedores(proveedores);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProveedor() {
        String textoBusqueda = txtBuscar.getText().trim();
        try {
            List<ProveedorClass> proveedores = textoBusqueda.isEmpty() ? proveedorDAO.listar() : proveedorDAO.buscar(textoBusqueda);
            if (proveedores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron proveedores.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            mostrarProveedores(proveedores);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarProveedor() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        ProveedorClass proveedor = proveedorDAO.buscarPorId(id);
        if (proveedor == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la información del proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtRucDlg = new JTextField(valor(proveedor.getRuc()));
        JTextField txtRazonDlg = new JTextField(valor(proveedor.getRazonSocial()));
        JTextField txtComercialDlg = new JTextField(valor(proveedor.getNombreComercial()));
        JTextField txtTelefonoDlg = new JTextField(valor(proveedor.getTelefono()));
        JTextField txtCorreoDlg = new JTextField(valor(proveedor.getCorreo()));
        JTextField txtDireccionDlg = new JTextField(valor(proveedor.getDireccion()));
        JComboBox<String> cbxEstadoDlg = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cbxEstadoDlg.setSelectedItem(valor(proveedor.getEstado()).isEmpty() ? "Activo" : proveedor.getEstado());

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.add(new JLabel("RUC:")); panel.add(txtRucDlg);
        panel.add(new JLabel("Razón social:")); panel.add(txtRazonDlg);
        panel.add(new JLabel("Nombre comercial:")); panel.add(txtComercialDlg);
        panel.add(new JLabel("Teléfono:")); panel.add(txtTelefonoDlg);
        panel.add(new JLabel("Correo:")); panel.add(txtCorreoDlg);
        panel.add(new JLabel("Dirección:")); panel.add(txtDireccionDlg);
        panel.add(new JLabel("Estado:")); panel.add(cbxEstadoDlg);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar proveedor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            String ruc = txtRucDlg.getText().trim();
            String razon = txtRazonDlg.getText().trim();

            if (ruc.isEmpty() || razon.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El RUC y la razón social son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!ruc.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "El RUC debe tener 11 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            proveedor.setRuc(ruc);
            proveedor.setRazonSocial(razon);
            proveedor.setNombreProveedor(razon);
            proveedor.setNombreComercial(txtComercialDlg.getText().trim());
            proveedor.setTelefono(txtTelefonoDlg.getText().trim());
            proveedor.setCorreo(txtCorreoDlg.getText().trim());
            proveedor.setDireccion(txtDireccionDlg.getText().trim());
            proveedor.setEstado(cbxEstadoDlg.getSelectedItem().toString());

            if (proveedorDAO.actualizar(proveedor)) {
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProveedor() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        String nombre = valor(modeloTabla.getValueAt(filaSeleccionada, 2));
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar/inactivar al proveedor: " + nombre + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (proveedorDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tablaReporteFechas1MouseClicked(MouseEvent evt) {
        filaSeleccionada = tablaReporteFechas1.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int columna = tablaReporteFechas1.getSelectedColumn();
            if (columna == 8) modificarProveedor();
            else if (columna == 9) eliminarProveedor();
            else if (evt.getClickCount() == 2) modificarProveedor();
        }
    }

    private String valor(Object dato) {
        return dato == null ? "" : dato.toString();
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaReporteFechas1 = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTotalProvee = new javax.swing.JTextField();
        txtInactivo = new javax.swing.JTextField();
        txtActivos = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tablaReporteFechas1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tablaReporteFechas1);

        btnNuevo.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(47, 47, 47))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText(" Total de Provedores ");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText("Provedores Inactivos");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel6.setText("Provedores activos");

        txtTotalProvee.addActionListener(this::txtTotalProveeActionPerformed);

        txtInactivo.addActionListener(this::txtInactivoActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Proveedor</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Administración de Proveedor</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-32.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/check_grenn32.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user-two-blue32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addGap(75, 75, 75))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalProvee, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(93, 93, 93))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTotalProvee, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addComponent(txtActivos)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(19, 19, 19))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInactivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInactivoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
        buscarProveedor();
    }

    private void txtTotalRecaudadoActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
        NuevoProvedor formulario = new NuevoProvedor();
        if (getDesktopPane() != null) {
            getDesktopPane().add(formulario);
            formulario.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Abra este formulario desde la ventana principal.");
        }
    }
    
    private void txtTotalProveeActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaReporteFechas1;
    private javax.swing.JTextField txtActivos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtInactivo;
    private javax.swing.JTextField txtTotalProvee;
    // End of variables declaration//GEN-END:variables

    

    
}
