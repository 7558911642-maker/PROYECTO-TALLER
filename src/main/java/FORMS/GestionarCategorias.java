package FORMS;



import DAO.CategoriaDAO;
import LOGICA.CategoriaClass;
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



public class GestionarCategorias extends javax.swing.JInternalFrame {

    private CategoriaDAO categoriaDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;
    
    
    public GestionarCategorias() {
        initComponents();
        categoriaDAO = new CategoriaDAO();
        configurarTabla();
        cargarDatos();
        
    }
    
    // ===============================
// GESTIONARCATEGORIAS.java
// ===============================



// 4) MÉTODOS: pegar antes de // Variables declaration
private void configurarTabla() {
    String[] columnas = {"ID", "Código", "Nombre", "Descripción", "Estado", "Modificar", "Eliminar"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblGestionCategorias.setModel(modeloTabla);
    tblGestionCategorias.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            tblGestionCategoriasMouseClicked(e);
        }
    });
}

private void mostrarCategorias(List<CategoriaClass> categorias) {
    modeloTabla.setRowCount(0);
    int total = 0, activos = 0, inactivos = 0;

    for (CategoriaClass cat : categorias) {
        modeloTabla.addRow(new Object[]{
            cat.getIdCategoria(),
            cat.getCodigo(),
            cat.getNombreCategoria(),
            cat.getDescripcion(),
            cat.getEstado(),
            "<html><font color='blue'>Modificar</font></html>",
            "<html><font color='red'>Eliminar</font></html>"
        });
        total++;
        if ("Activo".equalsIgnoreCase(cat.getEstado())) activos++; else inactivos++;
    }

    lblTotalCat.setText(String.valueOf(total));
    lblCatActivas.setText(String.valueOf(activos));
    lblCatInactiva.setText(String.valueOf(inactivos));
    filaSeleccionada = -1;
}

private void cargarDatos() {
    try {
        List<CategoriaClass> categorias = categoriaDAO.listar();
        mostrarCategorias(categorias);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void buscarCategoria() {
    String textoBusqueda = txtBuscar.getText().trim();
    try {
        List<CategoriaClass> categorias = textoBusqueda.isEmpty() ? categoriaDAO.listar() : categoriaDAO.buscar(textoBusqueda);
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron categorías.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        mostrarCategorias(categorias);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al buscar categoría: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void modificarCategoria() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione una categoría para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    String codigoActual = valor(modeloTabla.getValueAt(filaSeleccionada, 1));
    String nombreActual = valor(modeloTabla.getValueAt(filaSeleccionada, 2));
    String descripcionActual = valor(modeloTabla.getValueAt(filaSeleccionada, 3));
    String estadoActual = valor(modeloTabla.getValueAt(filaSeleccionada, 4));

    JTextField txtCodigoDlg = new JTextField(codigoActual);
    JTextField txtNombreDlg = new JTextField(nombreActual);
    JTextField txtDescripcionDlg = new JTextField(descripcionActual);
    JComboBox<String> cbxEstadoDlg = new JComboBox<>(new String[]{"Activo", "Inactivo"});
    cbxEstadoDlg.setSelectedItem(estadoActual);

    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    panel.add(new JLabel("Código:")); panel.add(txtCodigoDlg);
    panel.add(new JLabel("Nombre:")); panel.add(txtNombreDlg);
    panel.add(new JLabel("Descripción:")); panel.add(txtDescripcionDlg);
    panel.add(new JLabel("Estado:")); panel.add(cbxEstadoDlg);

    int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar categoría", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (opcion == JOptionPane.OK_OPTION) {
        String codigo = txtCodigoDlg.getText().trim();
        String nombre = txtNombreDlg.getText().trim();
        String descripcion = txtDescripcionDlg.getText().trim();
        String estado = cbxEstadoDlg.getSelectedItem().toString();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código y el nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CategoriaClass categoria = new CategoriaClass();
        categoria.setIdCategoria(id);
        categoria.setCodigo(codigo);
        categoria.setNombreCategoria(nombre);
        categoria.setDescripcion(descripcion);
        categoria.setEstado(estado);

        if (categoriaDAO.actualizar(categoria)) {
            JOptionPane.showMessageDialog(this, "Categoría actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
            txtBuscar.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void eliminarCategoria() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione una categoría para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    String nombre = valor(modeloTabla.getValueAt(filaSeleccionada, 2));
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar/inactivar la categoría: " + nombre + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        if (categoriaDAO.eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
            txtBuscar.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void tblGestionCategoriasMouseClicked(MouseEvent evt) {
    filaSeleccionada = tblGestionCategorias.getSelectedRow();
    if (filaSeleccionada >= 0) {
        int columna = tblGestionCategorias.getSelectedColumn();
        if (columna == 5) modificarCategoria();
        else if (columna == 6) eliminarCategoria();
        else if (evt.getClickCount() == 2) modificarCategoria();
    }
}

private String valor(Object dato) {
    return dato == null ? "" : dato.toString();
}

// 5) EVENTOS EXISTENTES: reemplazar el contenido
private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {
    NuevaCategoria formulario = new NuevaCategoria();
    if (getDesktopPane() != null) {
        getDesktopPane().add(formulario);
        formulario.setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Abra este formulario desde la ventana principal.");
    }
}



// Si tienes botón Buscar, usa esto:
private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
    buscarCategoria();
}

// Si no tienes botón Buscar, puedes llamar buscarCategoria() al presionar Enter en txtBuscar.

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGestionCategorias = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblTotalCat = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCatActivas = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblCatInactiva = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(237, 246, 254));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionCategorias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionCategorias);

        btnBuscar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 696, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addGap(41, 41, 41))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText(" Total Categorias");

        lblTotalCat.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblTotalCat.setForeground(new java.awt.Color(0, 100, 248));
        lblTotalCat.setText("0");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setText(" Categorias activas  ");

        lblCatActivas.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblCatActivas.setForeground(new java.awt.Color(34, 165, 90));
        lblCatActivas.setText("0");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel6.setText("Categorias Inactivas");

        lblCatInactiva.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        lblCatInactiva.setForeground(new java.awt.Color(255, 0, 0));
        lblCatInactiva.setText("0");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Categorías</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Administración de Categorías</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");
        jLabel4.setToolTipText("");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/check_grenn32.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-32.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVolver)
                .addGap(64, 64, 64))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3)
                        .addGap(138, 138, 138)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalCat)
                        .addGap(193, 193, 193)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCatActivas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblCatInactiva))
                    .addComponent(jLabel6))
                .addGap(125, 125, 125))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTotalCat)
                                .addComponent(lblCatActivas)
                                .addComponent(lblCatInactiva))
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        dispose();
    }//GEN-LAST:event_btnVolverActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCatActivas;
    private javax.swing.JLabel lblCatInactiva;
    private javax.swing.JLabel lblTotalCat;
    private javax.swing.JTable tblGestionCategorias;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
