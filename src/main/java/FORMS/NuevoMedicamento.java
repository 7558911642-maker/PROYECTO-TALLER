/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package FORMS;

import DAO.CategoriaDAO;
import DAO.LaboratorioDAO;
import DAO.ProductoDAO;
import DAO.ProveedorDAO;
import LOGICA.CategoriaClass;
import LOGICA.LaboratorioClass;
import LOGICA.ProductoClass;
import LOGICA.ProveedorClass;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class NuevoMedicamento extends javax.swing.JInternalFrame {
    
    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;
    private ProveedorDAO proveedorDAO;
    private LaboratorioDAO laboratorioDAO;

    public NuevoMedicamento() {
        initComponents();
        productoDAO = new ProductoDAO();
        categoriaDAO = new CategoriaDAO();
        proveedorDAO = new ProveedorDAO();
        laboratorioDAO = new LaboratorioDAO();
        cargarCombosMedicamento();
    }

    private void cargarCombosMedicamento() {
        cargarCategorias();
        cargarProveedores();
        cargarLaboratorios();
        jComboBox12.setModel(new DefaultComboBoxModel<>(new String[]{"Seleccionar", "Tableta", "Cápsula", "Jarabe", "Frasco", "Crema", "Ampolla", "Caja", "Unidad"}));
        jComboBox15.setModel(new DefaultComboBoxModel<>(new String[]{"Seleccionar", "Activo", "Inactivo"}));
    }

    private void cargarCategorias() {
        jComboBox7.removeAllItems();
        jComboBox7.addItem("Seleccionar");
        List<CategoriaClass> categorias = categoriaDAO.listar();
        for (CategoriaClass c : categorias) {
            if ("Activo".equalsIgnoreCase(c.getEstado())) {
                jComboBox7.addItem(c.getIdCategoria() + " - " + c.getNombreCategoria());
            }
        }
    }

    private void cargarProveedores() {
        jComboBox11.removeAllItems();
        jComboBox11.addItem("Seleccionar");
        List<ProveedorClass> proveedores = proveedorDAO.listar();
        for (ProveedorClass p : proveedores) {
            if ("Activo".equalsIgnoreCase(p.getEstado())) {
                jComboBox11.addItem(p.getIdProveedor() + " - " + p.getRazonSocial());
            }
        }
    }

    private void cargarLaboratorios() {
        jComboBox8.removeAllItems();
        jComboBox8.addItem("Seleccionar");
        List<LaboratorioClass> laboratorios = laboratorioDAO.listar();
        for (LaboratorioClass l : laboratorios) {
            if ("Activo".equalsIgnoreCase(l.getEstado())) {
                jComboBox8.addItem(l.getIdLaboratorio() + " - " + l.getNombre());
            }
        }
    }

    private void guardarMedicamento() {
        String codigo = txtCodigo5.getText().trim();
        String nombre = txtNombre5.getText().trim();
        String precioCompraTexto = txtPrecio6.getText().trim();
        String precioVentaTexto = txtPrecio7.getText().trim();
        String stockTexto = txtStock6.getText().trim();
        String presentacion = jComboBox12.getSelectedItem() == null ? "" : jComboBox12.getSelectedItem().toString();
        String estado = jComboBox15.getSelectedItem() == null ? "Activo" : jComboBox15.getSelectedItem().toString();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código del medicamento es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCodigo5.requestFocus();
            return;
        }
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre comercial es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre5.requestFocus();
            return;
        }
        if (jComboBox7.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría.", "Validación", JOptionPane.WARNING_MESSAGE);
            jComboBox7.requestFocus();
            return;
        }
        if (presentacion.equals("Seleccionar") || presentacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una presentación.", "Validación", JOptionPane.WARNING_MESSAGE);
            jComboBox12.requestFocus();
            return;
        }
        if (precioCompraTexto.isEmpty() || precioVentaTexto.isEmpty() || stockTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Precio de compra, precio de venta y stock son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            BigDecimal precioCompra = new BigDecimal(precioCompraTexto);
            BigDecimal precioVenta = new BigDecimal(precioVentaTexto);
            int stock = Integer.parseInt(stockTexto);

            if (precioCompra.compareTo(BigDecimal.ZERO) < 0 || precioVenta.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Los precios deben ser mayores o iguales a cero. El precio de venta debe ser mayor que cero.", "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.", "Validación", JOptionPane.WARNING_MESSAGE);
                txtStock6.requestFocus();
                return;
            }

            ProductoClass producto = new ProductoClass();
            producto.setCodigo(codigo);
            producto.setNombreComercial(nombre);
            producto.setPrincipioActivo(nombre);
            producto.setConcentracion("");
            producto.setPresentacion(presentacion);
            producto.setUnidadMedida("Unidad");
            producto.setRegistroSanitario("");
            producto.setRequiereReceta(false);
            producto.setIdCategoria(obtenerIdSeleccionado(jComboBox7.getSelectedItem().toString()));
            producto.setIdProveedor(jComboBox11.getSelectedIndex() > 0 ? obtenerIdSeleccionado(jComboBox11.getSelectedItem().toString()) : null);
            producto.setIdLaboratorio(jComboBox8.getSelectedIndex() > 0 ? obtenerIdSeleccionado(jComboBox8.getSelectedItem().toString()) : null);
            producto.setPrecioCompra(precioCompra);
            producto.setPrecio(precioVenta);
            producto.setPrecioUnidad(precioVenta);
            producto.setStock(stock);
            producto.setStockMinimo(10);
            producto.setStockMaximo(null);
            producto.setEstado(estado.equals("Seleccionar") ? "Activo" : estado);

            if (productoDAO.registrarMedicamento(producto)) {
                JOptionPane.showMessageDialog(this, "Medicamento registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarMedicamento();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y stock deben ser valores numéricos válidos.", "Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private int obtenerIdSeleccionado(String texto) {
        return Integer.parseInt(texto.split(" - ")[0].trim());
    }

    private void limpiarMedicamento() {
        txtCodigo5.setText("");
        txtNombre5.setText("");
        txtPrecio6.setText("");
        txtPrecio7.setText("");
        txtStock6.setText("");
        jComboBox7.setSelectedIndex(0);
        jComboBox8.setSelectedIndex(0);
        jComboBox11.setSelectedIndex(0);
        jComboBox12.setSelectedIndex(0);
        jComboBox15.setSelectedIndex(0);
        if (jDateChooser5 != null) {
            jDateChooser5.setDate(null);
        }
        txtCodigo5.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        txtCodigo5 = new javax.swing.JTextField();
        txtNombre5 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel51 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel52 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser5 = new com.toedter.calendar.JDateChooser();
        jComboBox15 = new javax.swing.JComboBox<>();
        jLabel61 = new javax.swing.JLabel();
        txtStock6 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtPrecio6 = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        txtPrecio7 = new javax.swing.JTextField();
        btnEliminar9 = new javax.swing.JButton();
        btnGuardar5 = new javax.swing.JButton();
        btnEliminar10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jPanel6.setBackground(new java.awt.Color(237, 246, 254));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 255));
        jLabel13.setText("<html>\n<table cellpadding=\"0\" cellspacing=\"0\">\n    <tr>\n        <td style=\"padding-left:18px;\">\n            <span style=\"font-family:Segoe UI; font-size:28px; color:#075DEB;\">\n                <b>Nuevo Medicamentos</b>\n            </span>\n            <br>\n            <span style=\"font-family:Segoe UI; font-size:13px; color:#5A6E8C;\">\n                Registre un nuevo Medicamento\n            </span>\n        </td>\n    </tr>\n    <tr>\n        <td>\n            <hr color=\"#0066FF\" size=\"1\">\n        </td>\n    </tr>\n</table>\n</html>");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel43.setText("Código");

        jLabel44.setText("Nombre Comercial:");

        jLabel45.setText("Categoria:");

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 100, 248));
        jLabel14.setText("Datos del Medicamento");

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "da", "ssa" }));

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "da", "ssa" }));

        jLabel50.setText("Laboratorio:");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "da", "ssa" }));

        jLabel51.setText("Provedor:");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "da", "ssa" }));

        jLabel52.setText("Presentacion:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel52))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox12, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox11, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNombre5))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                                    .addGap(108, 108, 108)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtCodigo5)
                                                .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel14))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel45)
                                    .addGap(24, 24, 24))
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addComponent(jLabel43)))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel50)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel45)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel50))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodigo5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 21, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txtNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51))
                .addGap(21, 21, 21)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel52))
                .addGap(42, 42, 42))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 100, 248));
        jLabel16.setText("Inventario");

        jLabel5.setText("fecha de vencimiento:");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo", " " }));

        jLabel61.setText("Estado:");

        jLabel57.setText("Stock:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel16))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel61)
                                    .addComponent(jLabel57))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox15, 0, 113, Short.MAX_VALUE)
                                    .addComponent(txtStock6))))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jDateChooser5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61)))
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStock6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("*Llene todos los campos son obligatorios ");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel56.setText("Precio Compra:");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 100, 248));
        jLabel15.setText("Informacion Comercial");

        jLabel59.setText("Precio Venta:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel15))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel56)
                            .addComponent(jLabel59))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecio6, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(txtPrecio7))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrecio6)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(txtPrecio7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnEliminar9.setBackground(new java.awt.Color(0, 100, 248));
        btnEliminar9.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar9.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/disk.png"))); // NOI18N
        btnEliminar9.setText("Guardar");
        btnEliminar9.addActionListener(this::btnEliminar9ActionPerformed);

        btnGuardar5.setBackground(new java.awt.Color(237, 246, 254));
        btnGuardar5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnGuardar5.setForeground(new java.awt.Color(0, 100, 248));
        btnGuardar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close-24.png"))); // NOI18N
        btnGuardar5.setText("Cancelar");
        btnGuardar5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 100, 248), 2, true));
        btnGuardar5.addActionListener(this::btnGuardar5ActionPerformed);

        btnEliminar10.setBackground(new java.awt.Color(248, 251, 254));
        btnEliminar10.setFont(new java.awt.Font("Segoe UI Semibold", 1, 13)); // NOI18N
        btnEliminar10.setForeground(new java.awt.Color(255, 102, 102));
        btnEliminar10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/broom.png"))); // NOI18N
        btnEliminar10.setText("limpiar");
        btnEliminar10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 2, true));
        btnEliminar10.addActionListener(this::btnEliminar10ActionPerformed);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XJ96v-logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(btnGuardar5, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnEliminar10))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar9)
                        .addComponent(btnGuardar5)
                        .addComponent(btnEliminar10))
                    .addComponent(jLabel3))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminar9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar9ActionPerformed
        guardarMedicamento();
    }//GEN-LAST:event_btnEliminar9ActionPerformed

    private void btnGuardar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar5ActionPerformed
        dispose();
    }//GEN-LAST:event_btnGuardar5ActionPerformed

    private void btnEliminar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar10ActionPerformed
        limpiarMedicamento();
    }//GEN-LAST:event_btnEliminar10ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar10;
    private javax.swing.JButton btnEliminar9;
    private javax.swing.JButton btnGuardar5;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private com.toedter.calendar.JDateChooser jDateChooser5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField txtCodigo5;
    private javax.swing.JTextField txtNombre5;
    private javax.swing.JTextField txtPrecio6;
    private javax.swing.JTextField txtPrecio7;
    private javax.swing.JTextField txtStock6;
    // End of variables declaration//GEN-END:variables
}
