package FORMS;

import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;

public class FrmPrincipal_1 extends javax.swing.JFrame {

    
   

    
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
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        exitMenuItem1 = new javax.swing.JMenuItem();
        saveMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        saveMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jButton18.setText("jButton18");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 100, 248));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 100, 248));
        jLabel1.setText("Accesos Rapidos ");

        jButton5.setText("<html><center><b>Nueva Venta</b><br><font size=\"2\">Registrar una nueva<br>venta rápidamente.</font></center></html>\n");
        jButton5.setToolTipText("");
        jButton5.addActionListener(this::jButton5ActionPerformed);

        jButton14.setText("<html><center><b>Consulta Stock</b><br><font size=\"2\">Consultar disponibilidad<br>de medicamentos.</font></center></html>");

        jButton15.setText("<html><center><b>Nuevo Cliente</b><br><font size=\"2\">Registrar un nuevo<br>cliente en el sistema.</font></center></html>");

        jButton16.setText("<html><center><b>Alertas</b><br><font size=\"2\">Ver alertas de stock bajo<br>y vencimientos.</font></center></html>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );

        jPanel3.setBackground(new java.awt.Color(0, 100, 248));

        jButton8.setBackground(new java.awt.Color(0, 100, 248));
        jButton8.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Inicio");
        jButton8.setBorder(null);

        jButton2.setBackground(new java.awt.Color(0, 100, 248));
        jButton2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Clientes");
        jButton2.setBorder(null);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton10.setBackground(new java.awt.Color(0, 100, 248));
        jButton10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Inventario");
        jButton10.setBorder(null);

        jButton3.setBackground(new java.awt.Color(0, 100, 248));
        jButton3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Provedores");
        jButton3.setBorder(null);
        jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jButton11.setBackground(new java.awt.Color(0, 100, 248));
        jButton11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Reportes");
        jButton11.setBorder(null);

        jButton1.setBackground(new java.awt.Color(0, 100, 248));
        jButton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Medicamentos ");
        jButton1.setBorder(null);
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton13.setBackground(new java.awt.Color(0, 100, 248));
        jButton13.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("configuraciones");
        jButton13.setBorder(null);

        jLabel5.setText("<html>\n    <center>\n        <font face=\"Segoe UI\" color=\"#DCE7F3\" size=\"3\">\n            Tu salud, nuestro compromiso\n        </font>\n    </center>\n</html>");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/login.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton13)
                    .addComponent(jButton10)
                    .addComponent(jButton8)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton10)
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addGap(18, 18, 18)
                .addComponent(jButton13)
                .addGap(129, 129, 129))
        );

        jPanel4.setBackground(new java.awt.Color(240, 246, 254));

        jLabel3.setText("Fecha:");

        jLabel4.setText("Hora:");

        jComboBox1.setBackground(new java.awt.Color(0, 100, 248));
        jComboBox1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Mi perfil" }));
        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(jLabel3)
                .addGap(298, 298, 298)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html>\n<font color=\"#1451C9\" size=\"10\"><b>Bienvenido</b></font>\n<br><br>\n\n<font color=\"#09256D\" size=\"7\">\n<b>Sistema de Gestión NOVA</b>\n</font>\n\n<br><br>\n\n<font color=\"#52617A\" size=\"4\">\nControl de ventas, inventario y medicamentos\n</font>\n\n<br>\n<hr width=\"290\" align=\"left\">\n\n<font color=\"#65738A\" size=\"3\">\nAdministra tu farmacia de forma eficiente y segura.\n<br><br>\nGestiona ventas, controla inventario, atiende a tus clientes\n<br>\ny toma decisiones con información confiable.\n</font>\n</html>");
        jLabel2.setOpaque(true);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/baner principal.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        desktopPane.add(jPanel1, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Mantenimiento");

        jMenu7.setText("Categoria");

        jMenuItem1.setText("Registrar categoría");
        jMenu7.add(jMenuItem1);

        jMenuItem4.setText("Gestionar categorías");
        jMenu7.add(jMenuItem4);

        fileMenu.add(jMenu7);

        jMenu9.setText("Clientes");

        jMenuItem5.setText("Registrar cliente");
        jMenu9.add(jMenuItem5);

        jMenuItem22.setText("Gestionar clientes");
        jMenu9.add(jMenuItem22);

        fileMenu.add(jMenu9);

        jMenu17.setText("Provedores");

        jMenuItem31.setText("Registrar proveedor");
        jMenu17.add(jMenuItem31);

        jMenuItem32.setText("Gestionar proveedores");
        jMenu17.add(jMenuItem32);

        fileMenu.add(jMenu17);

        jMenu11.setText("Medicamentos");

        jMenuItem27.setText("Registrar medicamento");
        jMenu11.add(jMenuItem27);

        jMenuItem28.setText("Gestionar medicamentos");
        jMenu11.add(jMenuItem28);

        fileMenu.add(jMenu11);

        jMenu15.setText("Usuarios");

        jMenuItem35.setText("Registrar usuario");
        jMenu15.add(jMenuItem35);

        jMenuItem36.setText("Gestionar usuarios");
        jMenu15.add(jMenuItem36);

        fileMenu.add(jMenu15);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Operaciones");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Nueva Venta");
        cutMenuItem.addActionListener(this::cutMenuItemActionPerformed);
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Gestionar ventas");
        copyMenuItem.addActionListener(this::copyMenuItemActionPerformed);
        editMenu.add(copyMenuItem);

        jMenuItem10.setText(" Ajustar stock");
        editMenu.add(jMenuItem10);

        jMenuItem12.setText("Consultar inventario");
        editMenu.add(jMenuItem12);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Reportes");

        jMenu2.setText(" Ventas");

        jMenuItem6.setText("Ventas por fecha");
        jMenu2.add(jMenuItem6);

        jMenuItem13.setText("Ventas por Cliente");
        jMenu2.add(jMenuItem13);

        jMenuItem14.setText("Ventas por Usuario");
        jMenu2.add(jMenuItem14);

        jMenuItem15.setText("Ventas anuladas");
        jMenu2.add(jMenuItem15);

        helpMenu.add(jMenu2);

        jMenu4.setText("Inventario");

        jMenuItem7.setText("Stock actual");
        jMenu4.add(jMenuItem7);

        jMenuItem16.setText("Bajo stock");
        jMenu4.add(jMenuItem16);

        jMenuItem17.setText("Medicamentos vencidos ");
        jMenu4.add(jMenuItem17);

        jMenuItem18.setText("Proximos a Vencer");
        jMenu4.add(jMenuItem18);

        helpMenu.add(jMenu4);

        jMenu5.setText("Maestros");

        exitMenuItem1.setMnemonic('x');
        exitMenuItem1.setText("Categoria ");
        exitMenuItem1.addActionListener(this::exitMenuItem1ActionPerformed);
        jMenu5.add(exitMenuItem1);

        saveMenuItem1.setMnemonic('s');
        saveMenuItem1.setText("Clientes");
        saveMenuItem1.addActionListener(this::saveMenuItem1ActionPerformed);
        jMenu5.add(saveMenuItem1);

        jMenuItem20.setText("Provedores");
        jMenu5.add(jMenuItem20);

        jMenuItem21.setText("Usuarios");
        jMenu5.add(jMenuItem21);

        helpMenu.add(jMenu5);

        jMenu6.setText("Estadisticas");

        jMenuItem19.setText("Estadísticas de ventas");
        jMenu6.add(jMenuItem19);

        jMenuItem23.setText("Productos más vendidos");
        jMenu6.add(jMenuItem23);

        jMenuItem24.setText("Clientes frecuentes");
        jMenu6.add(jMenuItem24);

        saveMenuItem2.setMnemonic('s');
        saveMenuItem2.setText("Ganancias");
        saveMenuItem2.addActionListener(this::saveMenuItem2ActionPerformed);
        jMenu6.add(saveMenuItem2);

        helpMenu.add(jMenu6);

        menuBar.add(helpMenu);

        jMenu3.setText("Ayuda");

        jMenuItem33.setText("Manual de usuario");
        jMenu3.add(jMenuItem33);

        jMenuItem34.setText("Informacion del sistema");
        jMenu3.add(jMenuItem34);

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
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
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
        NuevoClientes ventanaClientes = new NuevoClientes();
        abrirVentanaInterna(ventanaClientes);
    }

    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoVentas ventanaVentas = new MantenimientoVentas();
        abrirVentanaInterna(ventanaVentas);
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
        frmReporteProvedores ventana = new frmReporteProvedores();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
        frmReporteMedicamentos ventana = new frmReporteMedicamentos();
        abrirVentanaInterna(ventana);
    }

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {
        frmReporteVentas ventana = new frmReporteVentas();
        abrirVentanaInterna(ventana);
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

    private void exitMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitMenuItem1ActionPerformed

    private void saveMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveMenuItem1ActionPerformed

    private void saveMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveMenuItem2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

   

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoProveedores ventana = new MantenimientoProveedores();
        abrirVentanaInterna(ventana);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        MantenimientoCategorias ventana = new MantenimientoCategorias();
        abrirVentanaInterna(ventana);
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        frmReporteVentas ventana = new frmReporteVentas();
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
            java.util.logging.Logger.getLogger(FrmPrincipal_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal_1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPrincipal_1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem1;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem saveMenuItem1;
    private javax.swing.JMenuItem saveMenuItem2;
    // End of variables declaration//GEN-END:variables

    private void copyMenuItemActionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
