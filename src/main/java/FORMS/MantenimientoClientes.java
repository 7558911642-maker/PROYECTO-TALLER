package FORMS;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MantenimientoClientes extends javax.swing.JInternalFrame {

    
    DefaultTableModel modeloTabla;
    DefaultTableModel modeloTabla2;
    DAO.ClienteDAO clienteDAO = new DAO.ClienteDAO();
    
    public MantenimientoClientes() {
        initComponents();
        this.setTitle("Mantenimiento de Clientes - Registro de Asegurados");
        configurarTabla();
        configurarTabla2();
        cargarTabla();
        btnEliminar1.addActionListener(this::btnEliminar1ActionPerformed);
        btnEliminar2.addActionListener(this::btnEliminar2ActionPerformed);
        btnEliminar3.addActionListener(this::btnEliminar3ActionPerformed);
        btnActualizarStock1.addActionListener(this::btnActualizarStock1ActionPerformed);
    }

    private void configurarTabla() {
        String[] cabecera = {"ID", "Documento", "Cliente", "Contacto", "Cargo", "Dirección", "Ciudad", "Teléfono"};
        modeloTabla = new DefaultTableModel(null, cabecera) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaClientes.setModel(modeloTabla);
    }

    private void configurarTabla2() {
        String[] cabecera = {"ID", "Documento", "Nombres", "Teléfono", "Dirección", "Estado"};
        modeloTabla2 = new DefaultTableModel(null, cabecera) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaMedicamentos2.setModel(modeloTabla2);
    }
    
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<LOGICA.ClienteClass> lista = clienteDAO.listarClientes();
        for (LOGICA.ClienteClass cli : lista) {
            Object[] fila = new Object[8];
            fila[0] = cli.getIdCliente();
            fila[1] = cli.getDocumento();
            fila[2] = cli.getNombreCliente();
            fila[3] = cli.getNombreContacto();
            fila[4] = cli.getCargoContacto();
            fila[5] = cli.getDireccion();
            fila[6] = cli.getCiudad();
            fila[7] = cli.getTelefono();
            modeloTabla.addRow(fila);
        }
    }
    
    private void limpiarCampos() {
        txtDocumento.setText("");
        txtNombreCliente.setText("");
        txtNombreContacto.setText("");
        txtCargoContacto.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtRegion.setText("");
        txtPais.setText("");
        txtTelefono.setText("");
        txtDocumento.requestFocus();
    }

    private void cargarTabla2() {
        modeloTabla2.setRowCount(0);
        for (LOGICA.ClienteClass cli : clienteDAO.listarClientes()) {
            Object[] fila = new Object[6];
            fila[0] = cli.getIdCliente();
            fila[1] = cli.getDocumento();
            fila[2] = cli.getNombreCliente();
            fila[3] = cli.getTelefono();
            fila[4] = cli.getDireccion();
            fila[5] = "";
            modeloTabla2.addRow(fila);
        }
    }

    private void limpiarCampos2() {
        txtNombre3.setText("");
        txtNombre4.setText("");
        txtNombre5.setText("");
        txtNombre6.setText("");
        txtPrecio3.setText("");
        txtStock3.setText("");
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
        txtNombre3.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtBuscar2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaMedicamentos2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnEliminar1 = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        btnActualizarStock1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        btnEliminar3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtPrecio3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtNombre3 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtStock3 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        txtNombre4 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtNombre5 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        txtNombre6 = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 255));
        jLabel11.setText("Mantenimiento de Cliente ");

        jPanel4.setBackground(new java.awt.Color(232, 232, 255));

        jLabel20.setText("Buscar Provedor (DNI/PAS o Nombre):");

        txtBuscar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscar2KeyReleased(evt);
            }
        });

        tablaMedicamentos2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaMedicamentos2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(232, 232, 255));

        btnEliminar1.setText("Buacar");

        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(this::btnGuardar1ActionPerformed);

        btnActualizarStock1.setText("Actualizar");

        jButton1.setText("Nuevo ");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        btnEliminar2.setText("Eliminar");

        btnEliminar3.setText("limpiar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActualizarStock1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarStock1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel3.setBackground(new java.awt.Color(232, 232, 255));

        jLabel26.setText(" ");

        jLabel27.setText("Tipo Documento:");

        jLabel28.setText("Dcumento:");

        jLabel31.setText("Correo:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 255));
        jLabel12.setText("Datos del Cliente");

        jLabel32.setText("Direccion");

        jLabel33.setText("Estado:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Pendiente", "Inactivo" }));
        jComboBox3.addActionListener(this::jComboBox3ActionPerformed);

        jLabel30.setText("Apellidos:");

        jLabel34.setText("Teléfono:");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI:", "PASAPORTE:", " " }));
        jComboBox4.addActionListener(this::jComboBox4ActionPerformed);

        jLabel29.setText("Nombre.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30)
                            .addComponent(jLabel29))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                            .addComponent(txtNombre4)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombre6, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel31))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecio3)
                                    .addComponent(txtStock3)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(jLabel33))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox3, 0, 191, Short.MAX_VALUE)
                                    .addComponent(txtNombre5))))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel31)
                    .addComponent(txtPrecio3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(txtNombre3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(txtStock3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel34)
                        .addComponent(txtNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel29)
                        .addComponent(txtNombre6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscar2KeyReleased(java.awt.event.KeyEvent evt) {
        String criterio = txtBuscar2.getText().trim();
        if (criterio.isEmpty()) {
            cargarTabla2();
        } else {
            modeloTabla2.setRowCount(0);
            for (LOGICA.ClienteClass cli : clienteDAO.buscarClientes(criterio)) {
                Object[] fila = new Object[6];
                fila[0] = cli.getIdCliente();
                fila[1] = cli.getDocumento();
                fila[2] = cli.getNombreCliente();
                fila[3] = cli.getTelefono();
                fila[4] = cli.getDireccion();
                fila[5] = "";
                modeloTabla2.addRow(fila);
            }
        }
    }

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {
        String nombres = txtNombre6.getText().trim();
        String apellidos = txtNombre4.getText().trim();
        String doc = txtNombre3.getText().trim();
        if (nombres.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "El nombre del cliente es obligatorio.");
            return;
        }
        LOGICA.ClienteClass cli = new LOGICA.ClienteClass();
        cli.setDocumento(doc);
        cli.setNombreCliente(nombres + " " + apellidos);
        cli.setTelefono(txtNombre5.getText().trim());
        cli.setDireccion(txtStock3.getText().trim());
        if (clienteDAO.registrarCliente(cli)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cliente guardado correctamente.");
            limpiarCampos2();
            cargarTabla2();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al registrar cliente.");
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos2();
    }

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {
        String criterio = javax.swing.JOptionPane.showInputDialog(this, "Ingrese documento o nombre a buscar:");
        if (criterio != null && !criterio.trim().isEmpty()) {
            txtBuscar2.setText(criterio);
            modeloTabla2.setRowCount(0);
            for (LOGICA.ClienteClass cli : clienteDAO.buscarClientes(criterio.trim())) {
                Object[] fila = new Object[6];
                fila[0] = cli.getIdCliente();
                fila[1] = cli.getDocumento();
                fila[2] = cli.getNombreCliente();
                fila[3] = cli.getTelefono();
                fila[4] = cli.getDireccion();
                fila[5] = "";
                modeloTabla2.addRow(fila);
            }
        }
    }

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, "Función Eliminar no implementada.");
    }

    private void btnEliminar3ActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos2();
    }

    private void btnActualizarStock1ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this, "Función Actualizar no implementada.");
    }

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarStock1;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnEliminar3;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaMedicamentos2;
    private javax.swing.JTextField txtBuscar2;
    private javax.swing.JTextField txtNombre3;
    private javax.swing.JTextField txtNombre4;
    private javax.swing.JTextField txtNombre5;
    private javax.swing.JTextField txtNombre6;
    private javax.swing.JTextField txtPrecio3;
    private javax.swing.JTextField txtStock3;
    // End of variables declaration//GEN-END:variables
}
