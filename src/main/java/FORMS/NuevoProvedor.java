package FORMS;

import DAO.ProveedorDAO;
import LOGICA.ProveedorClass;

public class NuevoProvedor extends javax.swing.JInternalFrame {

    public NuevoProvedor() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEliminar9 = new javax.swing.JButton();
        btnGuardar5 = new javax.swing.JButton();
        btnEliminar10 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        btnEliminar11 = new javax.swing.JButton();
        btnGuardar6 = new javax.swing.JButton();
        btnEliminar12 = new javax.swing.JButton();

        btnEliminar9.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar9.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar9.setText("Guardar");
        btnEliminar9.addActionListener(this::btnEliminar9ActionPerformed);

        btnGuardar5.setBackground(new java.awt.Color(237, 246, 254));
        btnGuardar5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnGuardar5.setForeground(new java.awt.Color(0, 100, 248));
        btnGuardar5.setText("Cancelar");
        btnGuardar5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 248), 2, true));
        btnGuardar5.addActionListener(this::btnGuardar5ActionPerformed);

        btnEliminar10.setBackground(new java.awt.Color(248, 251, 254));
        btnEliminar10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar10.setForeground(new java.awt.Color(255, 102, 102));
        btnEliminar10.setText("limpiar");
        btnEliminar10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 2, true));
        btnEliminar10.addActionListener(this::btnEliminar10ActionPerformed);

        setForeground(new java.awt.Color(51, 51, 255));

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(101, 65, 136));
        jLabel1.setText("<html>\n<table cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td style=\"padding-left:18px;\">\n            <span style=\"font-family:Segoe UI; font-size:28px; color:#075DEB;\">\n                <b>Nuevo Provedor</b>\n            </span>\n            <br>\n            <span style=\"font-family:Segoe UI; font-size:13px; color:#5A6E8C;\">\n                Registre un nuevo Provedor\n            </span>\n        </td>\n    </tr>\n    <tr>\n        <td>\n            <hr color=\"#0066FF\" size=\"1\">\n        </td>\n    </tr>\n</table>\n</html>");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 100, 248));
        jLabel4.setText("Informacion empresarial ");

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
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 100, 248));
        jLabel7.setText("Datos de contacto");

        jLabel15.setText("Correo:");

        jLabel16.setText("Direccion:");

        jLabel17.setText("Estado:");

        cbxEstado1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Activo", "Inactivo" }));

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
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addGap(7, 7, 7)
                .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxEstado1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btnEliminar11.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar11.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar11.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar11.setText("Guardar");
        btnEliminar11.addActionListener(this::btnEliminar11ActionPerformed);

        btnGuardar6.setBackground(new java.awt.Color(237, 246, 254));
        btnGuardar6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnGuardar6.setForeground(new java.awt.Color(0, 100, 248));
        btnGuardar6.setText("Cancelar");
        btnGuardar6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 248), 2, true));
        btnGuardar6.addActionListener(this::btnGuardar6ActionPerformed);

        btnEliminar12.setBackground(new java.awt.Color(248, 251, 254));
        btnEliminar12.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar12.setForeground(new java.awt.Color(255, 102, 102));
        btnEliminar12.setText("limpiar");
        btnEliminar12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 2, true));
        btnEliminar12.addActionListener(this::btnEliminar12ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEliminar11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar12, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar11)
                    .addComponent(btnGuardar6)
                    .addComponent(btnEliminar12))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guardarProveedor() {
        String ruc = txtRuc.getText().trim();
        String razon = txtRazon.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo1.getText().trim();
        String direccion = txtDireccion1.getText().trim();

        if (ruc.isEmpty() || razon.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "RUC y Razón Social son obligatorios",
                "Campos vacíos",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        ProveedorClass prov = new ProveedorClass();
        prov.setRuc(ruc);
        prov.setNombreProveedor(razon);
        prov.setTelefono(telefono);
        prov.setCorreo(correo);
        prov.setDireccion(direccion);

        ProveedorDAO dao = new ProveedorDAO();
        if (dao.registrar(prov)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Proveedor registrado correctamente",
                "Éxito",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Error al registrar el proveedor",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtRuc.setText("");
        txtRazon.setText("");
        txtTelefono.setText("");
        txtCorreo1.setText("");
        txtDireccion1.setText("");
        cbxEstado1.setSelectedIndex(0);
    }

    private void btnEliminar9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar9ActionPerformed
        guardarProveedor();
    }//GEN-LAST:event_btnEliminar9ActionPerformed

    private void btnGuardar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar5ActionPerformed
        dispose();
    }//GEN-LAST:event_btnGuardar5ActionPerformed

    private void btnEliminar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar10ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnEliminar10ActionPerformed

    private void btnEliminar11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar11ActionPerformed
        guardarProveedor();
    }//GEN-LAST:event_btnEliminar11ActionPerformed

    private void btnGuardar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar6ActionPerformed
        dispose();
    }//GEN-LAST:event_btnGuardar6ActionPerformed

    private void btnEliminar12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar12ActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnEliminar12ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar10;
    private javax.swing.JButton btnEliminar11;
    private javax.swing.JButton btnEliminar12;
    private javax.swing.JButton btnEliminar9;
    private javax.swing.JButton btnGuardar5;
    private javax.swing.JButton btnGuardar6;
    private javax.swing.JComboBox<String> cbxEstado1;
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
