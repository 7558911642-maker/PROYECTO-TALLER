package FORMS;

import DAO.ProveedorDAO;
import LOGICA.ProveedorClass;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmMantenimientoProveedores extends javax.swing.JInternalFrame {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private DefaultTableModel modeloTabla;
    private int idSeleccionado = -1;

    public FrmMantenimientoProveedores() {
        initComponents();
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Activo", "Inactivo"}));
        initListeners();
        configurarTabla();
    }

    private void initListeners() {
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        jButton1.addActionListener(this::btnActualizarActionPerformed);
        jButton2.addActionListener(this::btnCancelarActionPerformed);
        jButton3.addActionListener(this::btnLimpiarActionPerformed);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        tblListaProveedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCamposDesdeTabla();
            }
        });
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "RUC", "Razón Social", "Teléfono", "Correo", "Dirección", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblListaProveedores.setModel(modeloTabla);
    }

    private void buscarProveedor() {
        String ruc = txtBuscarRuc.getText().trim();
        if (ruc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un RUC para buscar");
            return;
        }
        modeloTabla.setRowCount(0);
        for (ProveedorClass p : proveedorDAO.buscar(ruc)) {
            modeloTabla.addRow(new Object[]{
                p.getIdProveedor(), p.getRuc(), p.getNombreProveedor(),
                p.getTelefono(), p.getCorreo(), p.getDireccion(), p.getEstado()
            });
        }
        if (modeloTabla.getRowCount() > 0) {
            tblListaProveedores.setRowSelectionInterval(0, 0);
        } else {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
        }
    }

    private void cargarCamposDesdeTabla() {
        int fila = tblListaProveedores.getSelectedRow();
        if (fila < 0) return;
        idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        txtRuc.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtRazon.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 5).toString());
        String estado = modeloTabla.getValueAt(fila, 6).toString();
        for (int i = 0; i < cbxEstado.getItemCount(); i++) {
            if (cbxEstado.getItemAt(i).equals(estado)) {
                cbxEstado.setSelectedIndex(i);
                break;
            }
        }
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        buscarProveedor();
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la tabla");
            return;
        }
        String ruc = txtRuc.getText().trim();
        String razonSocial = txtRazon.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String estado = cbxEstado.getSelectedItem().toString();
        if (ruc.isEmpty() || razonSocial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "RUC y Razón Social son obligatorios");
            return;
        }
        ProveedorClass p = new ProveedorClass();
        p.setIdProveedor(idSeleccionado);
        p.setRuc(ruc);
        p.setNombreProveedor(razonSocial);
        p.setTelefono(telefono);
        p.setCorreo(correo);
        p.setDireccion(direccion);
        p.setEstado(estado);
        if (proveedorDAO.actualizar(p)) {
            JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente");
            limpiarCampos();
            txtBuscarRuc.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el proveedor");
        }
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void limpiarCampos() {
        idSeleccionado = -1;
        txtRuc.setText("");
        txtRazon.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        cbxEstado.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtRuc = new javax.swing.JTextField();
        txtRazon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCorreo1 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtDireccion1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cbxEstado1 = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(101, 65, 136));
        jLabel1.setText("<html>\n<table width=\"320\" cellpadding=\"0\" cellspacing=\"0\">\n\n<tr bgcolor=\"#FFFFFF\">\n    <td>\n        <font face=\"Segoe UI\" color=\"#092B70\" size=\"5\">\n            <b>Nuevo Provedor</b>\n        </font>\n\n        <br>\n\n        <font face=\"Segoe UI\" color=\"#6B7280\" size=\"3\">\n            Registre un nuevo cliente\n        </font>\n    </td>\n</tr>\n\n<tr bgcolor=\"#FFFFFF\">\n    <td height=\"18\"></td>\n</tr>\n\n</table>\n</html>");

        jButton2.setText("CANCELAR");

        jButton3.setText("LIMPIAR");

        jPanel3.setBackground(new java.awt.Color(226, 226, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(200, 96, 222));
        jLabel4.setText("informacion empresarial ");

        jLabel5.setText("RUC:");

        jLabel6.setText("Razón Social:");

        jLabel8.setText("Telefono");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)
                    .addComponent(txtRuc, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(txtRazon)
                    .addComponent(txtTelefono))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(226, 226, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(200, 96, 222));
        jLabel7.setText("Datos de contacto");

        jLabel15.setText("Correo:");

        jLabel16.setText("Direccion:");

        jLabel17.setText("Estado:");

        cbxEstado1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxEstado1, 0, 251, Short.MAX_VALUE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel7)
                    .addComponent(txtDireccion1)
                    .addComponent(txtCorreo1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(24, 24, 24)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addGap(7, 7, 7)
                .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(44, 44, 44)
                .addComponent(jButton3)
                .addGap(31, 31, 31))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap())
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxEstado1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtCorreo1;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtRazon;
    private javax.swing.JTextField txtRuc;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
