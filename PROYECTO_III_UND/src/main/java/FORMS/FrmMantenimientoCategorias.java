package FORMS;

import DAO.CategoriaDAO;
import LOGICA.CategoriaClass;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmMantenimientoCategorias extends javax.swing.JInternalFrame {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private DefaultTableModel modeloTabla;
    private int idSeleccionado = -1;

    public FrmMantenimientoCategorias() {
        initComponents();
        initListeners();
        configurarTabla();
    }

    private void initListeners() {
        btnBuscar.addActionListener(this::btnBuscarActionPerformed);
        jButton1.addActionListener(this::btnActualizarActionPerformed);
        jButton2.addActionListener(this::btnCancelarActionPerformed);
        jButton3.addActionListener(this::btnLimpiarActionPerformed);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        tblListClientes2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCamposDesdeTabla();
            }
        });
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Descripción", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblListClientes2.setModel(modeloTabla);
        listarCategorias();
    }

    private void listarCategorias() {
        modeloTabla.setRowCount(0);
        List<CategoriaClass> lista = categoriaDAO.listar();
        for (CategoriaClass c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getIdCategoria(), c.getNombreCategoria(), c.getDescripcion(), c.getEstado()
            });
        }
    }

    private void buscarCategoria() {
        String criterio = txtBuscarCodigo.getText().trim();
        if (criterio.isEmpty()) {
            listarCategorias();
            return;
        }
        modeloTabla.setRowCount(0);
        for (CategoriaClass c : categoriaDAO.buscar(criterio)) {
            modeloTabla.addRow(new Object[]{
                c.getIdCategoria(), c.getNombreCategoria(), c.getDescripcion(), c.getEstado()
            });
        }
        if (modeloTabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron categorías");
        }
    }

    private void cargarCamposDesdeTabla() {
        int fila = tblListClientes2.getSelectedRow();
        if (fila < 0) return;
        idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
        cbxEstado.setText(modeloTabla.getValueAt(fila, 3).toString());
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        buscarCategoria();
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría de la tabla");
            return;
        }
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String estado = cbxEstado.getText();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return;
        }
        CategoriaClass cat = new CategoriaClass();
        cat.setIdCategoria(idSeleccionado);
        cat.setNombreCategoria(nombre);
        cat.setDescripcion(descripcion);
        cat.setEstado(estado);
        if (categoriaDAO.actualizar(cat)) {
            JOptionPane.showMessageDialog(this, "Categoría actualizada correctamente");
            limpiarCampos();
            listarCategorias();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la categoría");
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
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtBuscarCodigo.setText("");
        cbxEstado.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscarCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListClientes2 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(101, 65, 136));
        jLabel1.setText("MANTENIEMIENTO DE CATEGORÍAS");

        jButton1.setText("ACTUALIZAR");

        jPanel2.setBackground(new java.awt.Color(226, 226, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(200, 96, 222));
        jLabel2.setText("Buscar Categoría (codigo único):");

        jLabel3.setText("Código Único:");

        btnBuscar.setText("BUSCAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel3)
                        .addGap(51, 51, 51)
                        .addComponent(txtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77)
                        .addComponent(btnBuscar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel2)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(226, 226, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(200, 96, 222));
        jLabel4.setText("Datos del cliente");

        jLabel5.setText("Código:");

        jLabel6.setText("Nombres:");

        jLabel9.setText("Descripción:");

        jLabel11.setText("Estado:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(txtNombre))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDescripcion)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jButton2.setText("CANCELAR");

        btnVolver.setText("VOLVER");

        jButton3.setText("LIMPIAR");

        jPanel6.setBackground(new java.awt.Color(226, 226, 255));

        tblListClientes2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblListClientes2);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(200, 96, 222));
        jLabel14.setText("Lista de Clientes");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel14))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(91, 91, 91)
                            .addComponent(jButton1)
                            .addGap(117, 117, 117)
                            .addComponent(jButton2)
                            .addGap(126, 126, 126)
                            .addComponent(jButton3)
                            .addContainerGap(107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addComponent(jLabel1)
                            .addGap(41, 41, 41)
                            .addComponent(btnVolver)
                            .addGap(29, 29, 29)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVolver)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JTextField cbxEstado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblListClientes2;
    private javax.swing.JTextField txtBuscarCodigo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
