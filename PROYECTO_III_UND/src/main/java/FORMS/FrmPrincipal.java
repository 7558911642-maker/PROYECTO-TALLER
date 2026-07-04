package FORMS;

import javax.swing.JInternalFrame;

public class FrmPrincipal extends javax.swing.JFrame {

    
    public FrmPrincipal() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jButton5.addActionListener(this::jButton5ActionPerformed);       
        jMenuItem8.addActionListener(this::jMenuItem8ActionPerformed);
        jMenuItem9.addActionListener(this::jMenuItem9ActionPerformed);
        jMenuItem11.addActionListener(this::jMenuItem11ActionPerformed);
        jMenuItem4.addActionListener(this::jMenuItem4ActionPerformed);
        jMenuItem5.addActionListener(this::jMenuItem5ActionPerformed);
        jMenuItem6.addActionListener(this::jMenuItem6ActionPerformed);
        jMenuItem7.addActionListener(this::jMenuItem7ActionPerformed);
        jMenuItem2.addActionListener(this::jMenuItem2ActionPerformed);
        jMenuItem3.addActionListener(this::jMenuItem3ActionPerformed);
    }

    
    private void abrirVentanaInterna(JInternalFrame ventana) {
        // Recorremos las ventanas abiertas en el panel para ver si ya existe
        boolean encontrado = false;
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame.getClass().equals(ventana.getClass())) {
                encontrado = true;
                frame.toFront(); // Si ya existe, la trae al frente
                try {
                    frame.setSelected(true); // La selecciona
                } catch (java.beans.PropertyVetoException e) {
                    System.out.println("Error al enfocar la ventana: " + e.getMessage());
                }
                break;
            }
        }
        
        if (!encontrado) {
            desktopPane.add(ventana);
            ventana.setVisible(true);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton18 = new javax.swing.JButton();
        desktopPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jButton18.setText("jButton18");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel2.setBackground(new java.awt.Color(232, 232, 255));

        jLabel1.setBackground(new java.awt.Color(102, 102, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("Accesos Rapidos ");

        jButton5.setText("Nueva Venta\n");
        jButton5.setToolTipText("");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton14.setText("Consulta stock");

        jButton15.setText("Nuevo cliente");

        jButton16.setText("Alertas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton14)
                .addGap(18, 18, 18)
                .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton14)
                    .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16)
                    .addComponent(jButton5))
                .addGap(17, 17, 17))
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));

        jButton8.setBackground(new java.awt.Color(102, 102, 255));
        jButton8.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Inicio");
        jButton8.setBorder(null);

        jButton9.setBackground(new java.awt.Color(102, 102, 255));
        jButton9.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Ventas");
        jButton9.setBorder(null);

        jButton2.setBackground(new java.awt.Color(102, 102, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Clientes");
        jButton2.setBorder(null);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton10.setBackground(new java.awt.Color(102, 102, 255));
        jButton10.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Inventario");
        jButton10.setBorder(null);

        jButton3.setBackground(new java.awt.Color(102, 102, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Provedores");
        jButton3.setBorder(null);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jButton11.setBackground(new java.awt.Color(102, 102, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Reportes");
        jButton11.setBorder(null);

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Medicamentos ");
        jButton1.setBorder(null);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton12.setBackground(new java.awt.Color(102, 102, 255));
        jButton12.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Productos por vencer");
        jButton12.setBorder(null);

        jButton13.setBackground(new java.awt.Color(102, 102, 255));
        jButton13.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("configuraciones");
        jButton13.setBorder(null);

        jComboBox1.setBackground(new java.awt.Color(102, 102, 255));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Mi perfil" }));
        jComboBox1.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13)
                    .addComponent(jButton12)
                    .addComponent(jButton1)
                    .addComponent(jButton10)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addGap(18, 18, 18)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("<html>\n<center>\n<b><font size=\"4\" color=\"#10213D\">Sistema de Gestión</font></b>\n<br><br>\n<font size=\"3\" color=\"#53627A\">Control de ventas, inventarios y medicamentos</font>\n</center>\n</html>");

        jLabel3.setText("Fecha:");

        jLabel4.setText("Hora:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jLabel3)
                .addGap(113, 113, 113)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        desktopPane.add(jPanel1, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Mantenimiento");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Medicamentos");
        openMenuItem.addActionListener(this::openMenuItemActionPerformed);
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Clientes");
        saveMenuItem.addActionListener(this::saveMenuItemActionPerformed);
        fileMenu.add(saveMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Categoria ");
        exitMenuItem.addActionListener(this::exitMenuItemActionPerformed);
        fileMenu.add(exitMenuItem);

        jMenuItem8.setText("Provedores");
        fileMenu.add(jMenuItem8);

        jMenuItem9.setText("Ventas");
        fileMenu.add(jMenuItem9);

        jMenuItem11.setText("Usuarios");
        fileMenu.add(jMenuItem11);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Operaciones");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("NuevaVenta");
        cutMenuItem.addActionListener(this::cutMenuItemActionPerformed);
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("AnularVenta");
        copyMenuItem.addActionListener(this::copyMenuItemActionPerformed);
        editMenu.add(copyMenuItem);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Reportes");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Reportes Categoria");
        contentMenuItem.addActionListener(this::contentMenuItemActionPerformed);
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("ReporteDiario");
        aboutMenuItem.addActionListener(this::aboutMenuItemActionPerformed);
        helpMenu.add(aboutMenuItem);

        jMenuItem1.setText("Estadisticas");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        helpMenu.add(jMenuItem1);

        jMenuItem4.setText("Reporte Cientes");
        helpMenu.add(jMenuItem4);

        jMenuItem5.setText("Reporte Provedores");
        helpMenu.add(jMenuItem5);

        jMenuItem6.setText("Reporte Medicamentos");
        helpMenu.add(jMenuItem6);

        jMenuItem7.setText("Reporte ventas");
        helpMenu.add(jMenuItem7);

        menuBar.add(helpMenu);

        jMenu3.setText("Ayuda");
        menuBar.add(jMenu3);

        jMenu1.setText("Salir");

        jMenuItem2.setText("CerrarSesion");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("SalirSistema");
        jMenu1.add(jMenuItem3);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoCategorias ventana = new MantenimientoCategorias();
        abrirVentanaInterna(ventana);
    }

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimietnoMedicamentos ventanaMedicamentos = new MantenimietnoMedicamentos();
        abrirVentanaInterna(ventanaMedicamentos);
    }

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoClientes ventanaClientes = new MantenimientoClientes();
        abrirVentanaInterna(ventanaClientes);
    }

    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoVentas ventanaVentas = new MantenimientoVentas();
        abrirVentanaInterna(ventanaVentas);
    }

    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        AnularVentas ventanaAnulaciones = new AnularVentas();
        abrirVentanaInterna(ventanaAnulaciones);
    }

    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteCategorias ventana = new ReporteCategorias();
        abrirVentanaInterna(ventana);
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteDiarioVentas ventanaDiaria = new ReporteDiarioVentas();
        abrirVentanaInterna(ventanaDiaria);
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        Estadisticas_Ventas ventanaEstadisticas = new Estadisticas_Ventas();
        abrirVentanaInterna(ventanaEstadisticas);      
    }

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoProveedores ventana = new MantenimientoProveedores();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoVentas ventana = new MantenimientoVentas();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoUsuarios ventana = new MantenimientoUsuarios();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteClientes ventana = new ReporteClientes();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteProvedores ventana = new ReporteProvedores();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteMedicamentos ventana = new ReporteMedicamentos();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteVentas ventana = new ReporteVentas();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new LoginForm().setVisible(true);
    }

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        MantenimietnoMedicamentos ventana = new MantenimietnoMedicamentos();
        abrirVentanaInterna(ventana);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

   

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoProveedores ventana = new MantenimientoProveedores();
        abrirVentanaInterna(ventana);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoCategorias ventana = new MantenimientoCategorias();
        abrirVentanaInterna(ventana);
    }

  

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        AnularVentas ventana = new AnularVentas();
        abrirVentanaInterna(ventana);
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        ReporteVentas ventana = new ReporteVentas();
        abrirVentanaInterna(ventana);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

}
