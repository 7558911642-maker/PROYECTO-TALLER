package FORMS;

import DAO.UsuarioDAO;
import LOGICA.UsuarioClass;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestionarUsuarios extends javax.swing.JInternalFrame {

    private UsuarioDAO usuarioDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;

    public GestionarUsuarios() {
        initComponents();
        usuarioDAO = new UsuarioDAO();
        configurarTabla();
        cargarDatos();
    }

    private void configurarTabla() {
        String[] columnas = {"ID", "Usuario", "Nombres", "Rol", "Estado", "Modificar", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblGestionUsuarios.setModel(modeloTabla);
        tblGestionUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblGestionUsuariosMouseClicked(e);
            }
        });
    }

    private void mostrarUsuarios(List<UsuarioClass> usuarios) {
        modeloTabla.setRowCount(0);
        for (UsuarioClass u : usuarios) {
            modeloTabla.addRow(new Object[]{
                u.getIdUsuario(),
                u.getUsuario(),
                u.getNombres(),
                u.getRol(),
                u.getEstado(),
                "<html><font color='blue'>Modificar</font></html>",
                "<html><font color='red'>Eliminar</font></html>"
            });
        }
        filaSeleccionada = -1;
    }

    private void cargarDatos() {
        try {
            List<UsuarioClass> usuarios = usuarioDAO.listar();
            mostrarUsuarios(usuarios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarUsuario() {
        String textoBusqueda = txtBuscar.getText().trim();
        try {
            List<UsuarioClass> usuarios = textoBusqueda.isEmpty() ? usuarioDAO.listar() : usuarioDAO.buscar(textoBusqueda);
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron usuarios.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            mostrarUsuarios(usuarios);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al buscar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarUsuario() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        UsuarioClass usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la información del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtUsuarioDlg = new JTextField(valor(usuario.getUsuario()));
        JPasswordField txtClaveDlg = new JPasswordField(valor(usuario.getContrasena()));
        JTextField txtNombresDlg = new JTextField(valor(usuario.getNombres()));
        JComboBox<String> cbxRolDlg = new JComboBox<>(new String[]{"Administrador", "Vendedor", "Cajero", "Almacenero"});
        JComboBox<String> cbxEstadoDlg = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        cbxRolDlg.setSelectedItem(valor(usuario.getRol()).isEmpty() ? "Vendedor" : usuario.getRol());
        cbxEstadoDlg.setSelectedItem(valor(usuario.getEstado()).isEmpty() ? "Activo" : usuario.getEstado());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Usuario:")); panel.add(txtUsuarioDlg);
        panel.add(new JLabel("Contraseña:")); panel.add(txtClaveDlg);
        panel.add(new JLabel("Nombres:")); panel.add(txtNombresDlg);
        panel.add(new JLabel("Rol:")); panel.add(cbxRolDlg);
        panel.add(new JLabel("Estado:")); panel.add(cbxEstadoDlg);

        int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opcion == JOptionPane.OK_OPTION) {
            String user = txtUsuarioDlg.getText().trim();
            String clave = new String(txtClaveDlg.getPassword()).trim();
            String nombres = txtNombresDlg.getText().trim();

            if (user.isEmpty() || clave.isEmpty() || nombres.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Usuario, contraseña y nombres son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (clave.length() < 4) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 4 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuario.setUsuario(user);
            usuario.setContrasena(clave);
            usuario.setNombres(nombres);
            usuario.setNombre(nombres);
            usuario.setRol(cbxRolDlg.getSelectedItem().toString());
            usuario.setEstado(cbxEstadoDlg.getSelectedItem().toString());

            if (usuarioDAO.actualizar(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUsuario() {
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
        String usuario = valor(modeloTabla.getValueAt(filaSeleccionada, 1));
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar/inactivar al usuario: " + usuario + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (usuarioDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void tblGestionUsuariosMouseClicked(MouseEvent evt) {
        filaSeleccionada = tblGestionUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int columna = tblGestionUsuarios.getSelectedColumn();
            if (columna == 5) modificarUsuario();
            else if (columna == 6) eliminarUsuario();
            else if (evt.getClickCount() == 2) modificarUsuario();
        }
    }

    private String valor(Object dato) {
        return dato == null ? "" : dato.toString();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGestionUsuarios = new javax.swing.JTable();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionUsuarios);

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Inventario General" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Tipo de Usuario:");

        btnBuscar1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(this::btnBuscar1ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                        .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(102, 102, 102)))
                .addGap(36, 36, 36))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar1)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Usuarios</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Controle de Usuarios</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addGap(74, 74, 74))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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

    private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1ActionPerformed
        buscarUsuario();
    }//GEN-LAST:event_btnBuscar1ActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblGestionUsuarios;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
