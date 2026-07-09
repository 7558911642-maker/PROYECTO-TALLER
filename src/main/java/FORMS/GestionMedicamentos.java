/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.ProductoDAO;
import LOGICA.ProductoClass;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestionMedicamentos extends javax.swing.JInternalFrame {

   private ProductoDAO productoDAO;
    private int filaSeleccionada = -1;
    private DefaultTableModel modeloTabla;

    public GestionMedicamentos() {
        initComponents();
         productoDAO = new ProductoDAO();
        configurarTabla();
        cargarDatos();
    }



// 4) MÉTODOS: pegar antes de // Variables declaration
private void configurarTabla() {
    String[] columnas = {"ID", "Código", "Medicamento", "Principio Activo", "Concentración", "Presentación", "Categoría", "Proveedor", "Laboratorio", "P. Compra", "P. Venta", "Stock", "Stock Mín.", "Stock Máx.", "Estado", "Modificar", "Eliminar"};
    modeloTabla = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tblGestionMedicamentos.setModel(modeloTabla);
    tblGestionMedicamentos.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            tblGestionMedicamentosMouseClicked(e);
        }
    });
}

private void mostrarMedicamentos(List<Object[]> medicamentos) {
    modeloTabla.setRowCount(0);
    int total = 0, activos = 0, inactivos = 0;

    for (Object[] m : medicamentos) {
        modeloTabla.addRow(new Object[]{
            m[0], m[1], m[2], m[3], m[4], m[5], m[6], m[7], m[8], m[9], m[10], m[11], m[12], m[13], m[14],
            "<html><font color='blue'>Modificar</font></html>",
            "<html><font color='red'>Eliminar</font></html>"
        });
        total++;
        if ("Activo".equalsIgnoreCase(valor(m[14]))) activos++; else inactivos++;
    }

    lblTotal.setText(String.valueOf(total));
    lblActivos.setText(String.valueOf(activos));
    lblInactivos.setText(String.valueOf(inactivos));
    filaSeleccionada = -1;
}

private void cargarDatos() {
    try {
        List<Object[]> medicamentos = productoDAO.listarMedicamentos();
        mostrarMedicamentos(medicamentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar medicamentos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void buscarMedicamento() {
    String textoBusqueda = txtBuscar.getText().trim();
    try {
        List<Object[]> medicamentos = textoBusqueda.isEmpty() ? productoDAO.listarMedicamentos() : productoDAO.buscarMedicamentos(textoBusqueda);
        if (medicamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron medicamentos.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        mostrarMedicamentos(medicamentos);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al buscar medicamento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void modificarMedicamento() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un medicamento para modificar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    ProductoClass producto = productoDAO.buscarPorId(id);
    if (producto == null) {
        JOptionPane.showMessageDialog(this, "No se pudo cargar la información del medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JTextField txtCodigoDlg = new JTextField(valor(producto.getCodigo()));
    JTextField txtNombreDlg = new JTextField(valor(producto.getNombreComercial()));
    JTextField txtPrincipioDlg = new JTextField(valor(producto.getPrincipioActivo()));
    JTextField txtConcentracionDlg = new JTextField(valor(producto.getConcentracion()));
    JTextField txtPresentacionDlg = new JTextField(valor(producto.getPresentacion()));
    JTextField txtUnidadDlg = new JTextField(valor(producto.getUnidadMedida()).isEmpty() ? "Unidad" : valor(producto.getUnidadMedida()));
    JTextField txtPrecioCompraDlg = new JTextField(valor(producto.getPrecioCompra()));
    JTextField txtPrecioVentaDlg = new JTextField(valor(producto.getPrecio()));
    JTextField txtStockDlg = new JTextField(String.valueOf(producto.getStock()));
    JTextField txtStockMinDlg = new JTextField(String.valueOf(producto.getStockMinimo()));
    JTextField txtStockMaxDlg = new JTextField(producto.getStockMaximo() == null ? "" : String.valueOf(producto.getStockMaximo()));
    JComboBox<String> cbxEstadoDlg = new JComboBox<>(new String[]{"Activo", "Inactivo"});
    cbxEstadoDlg.setSelectedItem(valor(producto.getEstado()).isEmpty() ? "Activo" : producto.getEstado());

    JPanel panel = new JPanel(new GridLayout(12, 2, 10, 10));
    panel.add(new JLabel("Código:")); panel.add(txtCodigoDlg);
    panel.add(new JLabel("Nombre comercial:")); panel.add(txtNombreDlg);
    panel.add(new JLabel("Principio activo:")); panel.add(txtPrincipioDlg);
    panel.add(new JLabel("Concentración:")); panel.add(txtConcentracionDlg);
    panel.add(new JLabel("Presentación:")); panel.add(txtPresentacionDlg);
    panel.add(new JLabel("Unidad medida:")); panel.add(txtUnidadDlg);
    panel.add(new JLabel("Precio compra:")); panel.add(txtPrecioCompraDlg);
    panel.add(new JLabel("Precio venta:")); panel.add(txtPrecioVentaDlg);
    panel.add(new JLabel("Stock:")); panel.add(txtStockDlg);
    panel.add(new JLabel("Stock mínimo:")); panel.add(txtStockMinDlg);
    panel.add(new JLabel("Stock máximo:")); panel.add(txtStockMaxDlg);
    panel.add(new JLabel("Estado:")); panel.add(cbxEstadoDlg);

    int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar medicamento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (opcion == JOptionPane.OK_OPTION) {
        try {
            if (txtCodigoDlg.getText().trim().isEmpty() || txtNombreDlg.getText().trim().isEmpty() || txtPresentacionDlg.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código, nombre y presentación son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            BigDecimal precioCompra = new BigDecimal(txtPrecioCompraDlg.getText().trim());
            BigDecimal precioVenta = new BigDecimal(txtPrecioVentaDlg.getText().trim());
            int stock = Integer.parseInt(txtStockDlg.getText().trim());
            int stockMin = Integer.parseInt(txtStockMinDlg.getText().trim());
            Integer stockMax = txtStockMaxDlg.getText().trim().isEmpty() ? null : Integer.valueOf(txtStockMaxDlg.getText().trim());

            if (precioCompra.compareTo(BigDecimal.ZERO) < 0 || precioVenta.compareTo(BigDecimal.ZERO) < 0 || stock < 0 || stockMin < 0) {
                JOptionPane.showMessageDialog(this, "Precios y stock no pueden ser negativos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            producto.setCodigo(txtCodigoDlg.getText().trim());
            producto.setNombreComercial(txtNombreDlg.getText().trim());
            producto.setPrincipioActivo(txtPrincipioDlg.getText().trim());
            producto.setConcentracion(txtConcentracionDlg.getText().trim());
            producto.setPresentacion(txtPresentacionDlg.getText().trim());
            producto.setUnidadMedida(txtUnidadDlg.getText().trim());
            producto.setPrecioCompra(precioCompra);
            producto.setPrecio(precioVenta);
            producto.setPrecioUnidad(precioVenta);
            producto.setStock(stock);
            producto.setStockMinimo(stockMin);
            producto.setStockMaximo(stockMax);
            producto.setEstado(cbxEstadoDlg.getSelectedItem().toString());

            if (productoDAO.actualizar(producto)) {
                JOptionPane.showMessageDialog(this, "Medicamento actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                txtBuscar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void eliminarMedicamento() {
    if (filaSeleccionada < 0) {
        JOptionPane.showMessageDialog(this, "Seleccione un medicamento para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int id = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
    String nombre = valor(modeloTabla.getValueAt(filaSeleccionada, 2));
    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar/inactivar el medicamento: " + nombre + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

    if (confirmacion == JOptionPane.YES_OPTION) {
        if (productoDAO.eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Medicamento eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatos();
            txtBuscar.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void tblGestionMedicamentosMouseClicked(MouseEvent evt) {
    filaSeleccionada = tblGestionMedicamentos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        int columna = tblGestionMedicamentos.getSelectedColumn();
        if (columna == 15) modificarMedicamento();
        else if (columna == 16) eliminarMedicamento();
        else if (evt.getClickCount() == 2) modificarMedicamento();
    }
}

private void imprimirTabla() {
    try {
        tblGestionMedicamentos.print();
    } catch (PrinterException e) {
        JOptionPane.showMessageDialog(this, "Error al imprimir: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void exportarTablaCSV() {
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Guardar reporte de medicamentos");
    chooser.setSelectedFile(new File("reporte_medicamentos.csv"));
    int opcion = chooser.showSaveDialog(this);
    if (opcion == JFileChooser.APPROVE_OPTION) {
        try (PrintWriter pw = new PrintWriter(chooser.getSelectedFile())) {
            for (int i = 0; i < modeloTabla.getColumnCount() - 2; i++) {
                pw.print(modeloTabla.getColumnName(i));
                if (i < modeloTabla.getColumnCount() - 3) pw.print(";");
            }
            pw.println();
            for (int f = 0; f < modeloTabla.getRowCount(); f++) {
                for (int c = 0; c < modeloTabla.getColumnCount() - 2; c++) {
                    pw.print(valor(modeloTabla.getValueAt(f, c)));
                    if (c < modeloTabla.getColumnCount() - 3) pw.print(";");
                }
                pw.println();
            }
            JOptionPane.showMessageDialog(this, "Reporte exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private String valor(Object dato) {
    return dato == null ? "" : dato.toString();
}

// 5) EVENTOS EXISTENTES: 
private void btnBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {
    buscarMedicamento();
}

private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
    dispose();
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGestionMedicamentos = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblInactivos = new javax.swing.JLabel();
        lblActivos = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();
        btnExportarExel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel5.setBackground(new java.awt.Color(237, 246, 254));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblGestionMedicamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblGestionMedicamentos);

        btnActualizar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/refresh.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Inventario General" }));

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel7.setText("Tipo de inventario:");

        btnBuscar1.setBackground(new java.awt.Color(0, 100, 248));
        btnBuscar1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnBuscar1.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar1.setText("Buscar");
        btnBuscar1.addActionListener(this::btnBuscar1ActionPerformed);

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("Total Medicamentos:");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel9.setText("Medicamentos activos:");

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("Medicamentos inactivos:");

        lblTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblTotal.setText("0");

        lblInactivos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblInactivos.setText("0");

        lblActivos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        lblActivos.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 50, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(btnBuscar1)
                                .addGap(18, 18, 18)))))
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar1))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(lblTotal)
                    .addComponent(lblInactivos)
                    .addComponent(lblActivos))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnImprimir.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnImprimir.setText("Imprimr");
        btnImprimir.addActionListener(this::btnImprimirActionPerformed);

        btnExportarExel.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btnExportarExel.setText("Exportar");
        btnExportarExel.addActionListener(this::btnExportarExelActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 255));
        jLabel4.setText("<html>\n<table width=\"260\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#0645A3\" size=\"5\"><b>Gestion Medicamentos</b></font></td>\n  </tr>\n  <tr>\n    <td><font face=\"Segoe UI\" color=\"#707782\" size=\"2\">Administra el catálago de Medicamentos</font></td>\n  </tr>\n  <!-- Se eliminó la fila intermedia que daba el espacio de altura 5 -->\n  <tr>\n    <td bgcolor=\"#62A9F5\" height=\"3\"></td>\n  </tr>\n</table>\n</html>");

        btnVolver.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        btnVolver.setText(" VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(btnImprimir)
                                .addGap(52, 52, 52)
                                .addComponent(btnExportarExel))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVolver)
                        .addGap(98, 98, 98))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir)
                    .addComponent(btnExportarExel))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatos();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnExportarExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarExelActionPerformed
        exportarTablaCSV();
        
    }//GEN-LAST:event_btnExportarExelActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       
        imprimirTabla();
    }//GEN-LAST:event_btnImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBuscar1;
    private javax.swing.JButton btnExportarExel;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblActivos;
    private javax.swing.JLabel lblInactivos;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblGestionMedicamentos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
