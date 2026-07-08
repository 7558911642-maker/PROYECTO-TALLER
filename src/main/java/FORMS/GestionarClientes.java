/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.ClienteDAO;
import LOGICA.ClienteClass;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestionarClientes extends javax.swing.JInternalFrame {

    private ClienteDAO clienteDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;

    public GestionarClientes() {
        initComponents();
        clienteDAO = new ClienteDAO();
        configurarTabla();
        cargarDatos();
    }


// 4) MÉTODOS: pegar antes de // Variables declaration
private void configurarTabla() {
    String[] columnas = {"ID", "Tipo", "Documento", "Cliente / Razón Social", "Teléfono", "Correo", "Dirección", "Estado", "Modificar", "Eliminar"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblGestionClientes.setModel(modeloTabla);
    tblGestionClientes.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            tblGestionClientesMouseClicked(e);
        }
    });
}

private void mostrarClientes(List<ClienteClass> clientes) {
    modeloTabla.setRowCount(0);
    for (ClienteClass c : clientes) {
        modeloTabla.addRow(new Object[]{
            c.getIdCliente(),
            c.getTipoCliente(),
            c.getNumeroDocumento(),
            obtenerNombreCliente(c),
            c.getTelefono(),
            c.getCorreo(),
            c.getDireccion(),
            c.getEstado(),
            "<html><font color='blue'>Modificar</font></html>",
            "<html><font color='red'>Eliminar</font></html>"
        });
    }
    filaSeleccionada = -1;
}

private void cargarDatos() {
    try {
        List<ClienteClass> clientes = clienteDAO.listarClientes();
        mostrarClientes(filtrarClientes(clientes));
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void buscarCliente() {
    String textoBusqueda = txtBuscar.getText().trim();
    try {
        List<ClienteClass> clientes = textoBusqueda.isEmpty() ? clienteDAO.listarClientes() : clienteDAO.buscarClientes(textoBusqueda);
        clientes = filtrarClientes(clientes);
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron clientes.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        mostrarClientes(clientes);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private List<ClienteClass> filtrarClientes(List<ClienteClass> lista) {
    List<ClienteClass> filtrados = new ArrayList<>();
    String tipo = cbxTipo.getSelectedItem() == null ? "" : cbxTipo.getSelectedItem().toString();
    String estado = cbxEstado.getSelectedItem() == null ? "" : cbxEstado.getSelectedItem().toString();

    for (ClienteClass c : lista) {
        boolean cumpleTipo = tipo.equalsIgnoreCase("Todos") || tipo.equalsIgnoreCase("Tipo") || tipo.trim().isEmpty()
                || c.getTipoCliente() == null || c.getTipoCliente().toLowerCase().contains(tipo.toLowerCase())
                || (tipo.toLowerCase().contains("natural") && "PERSONA_NATURAL".equalsIgnoreCase(c.getTipoCliente()))
                || (tipo.toLowerCase().contains("empresa") && "EMPRESA".equalsIgnoreCase(c.getTipoCliente()));

        boolean cumpleEstado = estado.equalsIgnoreCase("Todos") || estado.equalsIgnoreCase("Estado") || estado.trim().isEmpty()
                || (c.getEstado() != null && c.getEstado().equalsIgnoreCase(estado));

        if (cumpleTipo && cumpleEstado) filtrados.add(c);
    }
    return filtrados;
}

private void modificarCliente() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    ClienteClass cliente = clienteDAO.buscarPorId(id);
    if (cliente == null) {
        JOptionPane.showMessageDialog(this, "No se pudo cargar la información del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<String> cbxTipoDlg = new JComboBox<>(new String[]{"PERSONA_NATURAL", "EMPRESA"});
    JComboBox<String> cbxDocumentoDlg = new JComboBox<>(new String[]{"DNI", "RUC", "CE", "PASAPORTE"});
    JTextField txtDocumentoDlg = new JTextField(valor(cliente.getNumeroDocumento()));
    JTextField txtNombresDlg = new JTextField(valor(cliente.getNombres()));
    JTextField txtApellidosDlg = new JTextField(valor(cliente.getApellidos()));
    JTextField txtRazonDlg = new JTextField(valor(cliente.getRazonSocial()));
    JTextField txtTelefonoDlg = new JTextField(valor(cliente.getTelefono()));
    JTextField txtCorreoDlg = new JTextField(valor(cliente.getCorreo()));
    JTextField txtDireccionDlg = new JTextField(valor(cliente.getDireccion()));
    JComboBox<String> cbxEstadoDlg = new JComboBox<>(new String[]{"Activo", "Inactivo"});

    cbxTipoDlg.setSelectedItem(valor(cliente.getTipoCliente()).isEmpty() ? "PERSONA_NATURAL" : cliente.getTipoCliente());
    cbxDocumentoDlg.setSelectedItem(valor(cliente.getTipoDocumento()).isEmpty() ? "DNI" : cliente.getTipoDocumento());
    cbxEstadoDlg.setSelectedItem(valor(cliente.getEstado()).isEmpty() ? "Activo" : cliente.getEstado());

    JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
    panel.add(new JLabel("Tipo cliente:")); panel.add(cbxTipoDlg);
    panel.add(new JLabel("Tipo documento:")); panel.add(cbxDocumentoDlg);
    panel.add(new JLabel("N° documento:")); panel.add(txtDocumentoDlg);
    panel.add(new JLabel("Nombres:")); panel.add(txtNombresDlg);
    panel.add(new JLabel("Apellidos:")); panel.add(txtApellidosDlg);
    panel.add(new JLabel("Razón social:")); panel.add(txtRazonDlg);
    panel.add(new JLabel("Teléfono:")); panel.add(txtTelefonoDlg);
    panel.add(new JLabel("Correo:")); panel.add(txtCorreoDlg);
    panel.add(new JLabel("Dirección:")); panel.add(txtDireccionDlg);

    int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar cliente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (opcion == JOptionPane.OK_OPTION) {
        String tipoCliente = cbxTipoDlg.getSelectedItem().toString();
        String documento = txtDocumentoDlg.getText().trim();
        String nombres = txtNombresDlg.getText().trim();
        String apellidos = txtApellidosDlg.getText().trim();
        String razonSocial = txtRazonDlg.getText().trim();

        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El documento es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tipoCliente.equals("PERSONA_NATURAL") && nombres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los nombres son obligatorios para persona natural.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tipoCliente.equals("EMPRESA") && razonSocial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La razón social es obligatoria para empresa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        cliente.setTipoCliente(tipoCliente);
        cliente.setTipoDocumento(cbxDocumentoDlg.getSelectedItem().toString());
        cliente.setNumeroDocumento(documento);
        cliente.setDocumento(documento);
        cliente.setNombres(nombres);
        cliente.setNombreCliente(nombres);
        cliente.setApellidos(apellidos);
        cliente.setRazonSocial(razonSocial);
        cliente.setTelefono(txtTelefonoDlg.getText().trim());
        cliente.setCorreo(txtCorreoDlg.getText().trim());
        cliente.setDireccion(txtDireccionDlg.getText().trim());
        cliente.setEstado(cbxEstadoDlg.getSelectedItem().toString());

        if (clienteDAO.actualizar(cliente)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
            txtBuscar.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void eliminarCliente() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    String nombre = valor(modeloTabla.getValueAt(filaSeleccionada, 3));
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar/inactivar al cliente: " + nombre + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        if (clienteDAO.eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
            txtBuscar.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void tblGestionClientesMouseClicked(MouseEvent evt) {
    filaSeleccionada = tblGestionClientes.getSelectedRow();
    if (filaSeleccionada >= 0) {
        int columna = tblGestionClientes.getSelectedColumn();
        if (columna == 8) modificarCliente();
        else if (columna == 9) eliminarCliente();
        else if (evt.getClickCount() == 2) modificarCliente();
    }
}

private String obtenerNombreCliente(ClienteClass c) {
    if ("EMPRESA".equalsIgnoreCase(c.getTipoCliente())) return valor(c.getRazonSocial());
    return (valor(c.getNombres()) + " " + valor(c.getApellidos())).trim();
}

private String valor(Object dato) {
    return dato == null ? "" : dato.toString();
}

// 5) EVENTOS EXISTENTES: reemplazar el contenido
private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
    buscarCliente();
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
        tblGestionClientes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        btnBuscar1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionClientes);

        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel27.setText("Tipo Doc:");

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI:", "PASAPORTE:", " " }));
        cbxTipo.addActionListener(this::cbxTipoActionPerformed);

        jLabel33.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel33.setText("Estado:");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Pendiente", "Inactivo" }));
        cbxEstado.addActionListener(this::cbxEstadoActionPerformed);

        btnBuscar1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(this::btnBuscar1ActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(btnBuscar1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(jLabel33)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxEstado)
                    .addComponent(cbxTipo)
                    .addComponent(txtBuscar)
                    .addComponent(btnBuscar1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Clientes</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Administración de Clientes</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(btnVolver)
                .addGap(64, 64, 64))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed

    buscarCliente();

        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void cbxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoActionPerformed
     buscarCliente();
// TODO add your handling code here:
    }//GEN-LAST:event_cbxTipoActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed

    dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnVolverActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblGestionClientes;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
