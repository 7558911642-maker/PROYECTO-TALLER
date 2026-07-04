package FORMS;

import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoVentas extends javax.swing.JInternalFrame {

    DefaultTableModel modeloCarrito;
    
    DAO.ClienteDAO clienteDAO = new DAO.ClienteDAO();
    DAO.ProductoDAO productoDAO = new DAO.ProductoDAO();
    DAO.PedidoDAO pedidoDAO = new DAO.PedidoDAO();
    
    private int idClienteSeleccionado = 0;
    private int idEmpleadoActivo = 1;
    private BigDecimal totalVenta = BigDecimal.ZERO;
    
    public MantenimientoVentas() {
        initComponents();
        this.setTitle("Módulo de Ventas - Facturación de Mostrador");
        configurarTablaCarrito();
        btnActualizarStock2.addActionListener(this::btnActualizarStock2ActionPerformed);
        txtNombre9.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                calcularVuelto();
            }
        });
        txtCodigo3.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String codigo = txtCodigo3.getText().trim();
                if (codigo.length() >= 2) {
                    List<Object[]> productos = productoDAO.buscarMedicamentos(codigo);
                    if (!productos.isEmpty()) {
                        Object[] prod = productos.get(0);
                        txtNombre5.setText((String) prod[2]);
                        txtNombre4.setText(prod[5].toString());
                        txtNombre7.setText(prod[5].toString());
                    }
                }
            }
        });
    }

    private void configurarTablaCarrito() {
        String[] cabecera = {"ID Producto", "Código", "Medicamento", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloCarrito = new DefaultTableModel(null, cabecera) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMedicamentos2.setModel(modeloCarrito);
    }
    
    private void calcularTotalVenta() {
        totalVenta = BigDecimal.ZERO;
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            totalVenta = totalVenta.add((BigDecimal) modeloCarrito.getValueAt(i, 5));
        }
        txtNombre8.setText(totalVenta.toString());
        calcularVuelto();
    }

    private void calcularVuelto() {
        String efectivoStr = txtNombre9.getText().trim();
        if (!efectivoStr.isEmpty()) {
            try {
                BigDecimal efectivo = new BigDecimal(efectivoStr);
                BigDecimal vuelto = efectivo.subtract(totalVenta);
                txtNombre10.setText(vuelto.compareTo(BigDecimal.ZERO) >= 0 ? vuelto.toString() : "0");
            } catch (NumberFormatException e) {
            }
        }
    }
    
    private void limpiarFormularioVenta() {
        modeloCarrito.setRowCount(0);
        idClienteSeleccionado = 0;
        txtNombreCliente1.setText("");
        txtDocCliente1.setText("");
        txtCantidad2.setText("");
        txtCodigo3.setText("");
        txtNombre4.setText("");
        txtNombre5.setText("");
        txtNombre6.setText("");
        txtNombre7.setText("");
        txtNombre8.setText("");
        txtNombre9.setText("");
        txtNombre10.setText("");
        totalVenta = BigDecimal.ZERO;
        jComboBox1.setSelectedIndex(0);
        dcFechaFin2.setDate(new java.util.Date());
        txtCodigo3.requestFocus();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaMedicamentos2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNombre8 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtNombre9 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtNombre10 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNombreCliente1 = new javax.swing.JTextField();
        txtDocCliente1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        dcFechaFin2 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtCantidad2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        btnGuardar2 = new javax.swing.JButton();
        btnActualizarStock2 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtNombre4 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtNombre5 = new javax.swing.JTextField();
        txtCodigo3 = new javax.swing.JTextField();
        txtNombre6 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtNombre7 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        btnGuardar3 = new javax.swing.JButton();
        btnGuardar4 = new javax.swing.JButton();
        btnGuardar5 = new javax.swing.JButton();
        btnGuardar6 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 255));
        jLabel9.setText("Mantenimiento de Ventas");

        jPanel4.setBackground(new java.awt.Color(232, 232, 255));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 255));
        jLabel15.setText("3. Producto Agregados:");

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
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel2.setBackground(new java.awt.Color(232, 232, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 255));
        jLabel12.setText("4. Resumend e Venta:");

        jLabel31.setText("Total(S/):");

        jLabel33.setText("Efectivo (S/):");

        txtNombre10.addActionListener(this::txtNombre10ActionPerformed);

        jLabel34.setText("Vuelto:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel31)
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNombre9, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addComponent(txtNombre8))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txtNombre8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(txtNombre9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(txtNombre10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(232, 232, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 255));
        jLabel11.setText("1. Datos de la venta:");

        jLabel8.setText("N° Venta:");

        jLabel10.setText("Nombre Cliente");

        jLabel16.setText("Tipo Documento:");

        jLabel17.setText("N° Documento:");

        jButton2.setText("Buscar");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        dcFechaFin2.setDateFormatString("yyyy-MM-dd");

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        jLabel7.setText("Fecha:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI:", "PAS:", "RUC:" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtNombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDocCliente1)
                            .addComponent(jComboBox1, 0, 221, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(dcFechaFin2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCantidad2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)))))
                .addGap(0, 87, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel11)
                .addGap(67, 67, 67))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcFechaFin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(txtCantidad2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtNombreCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtDocCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(232, 232, 255));

        btnGuardar2.setText("Agregar");
        btnGuardar2.addActionListener(this::btnGuardar2ActionPerformed);

        btnActualizarStock2.setText("Actualizar");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 255));
        jLabel13.setText("2. Detalle de la Venta");

        jLabel32.setText("Producto:");

        jLabel28.setText("Cantidad:");

        jLabel27.setText("Código de producto:");

        jLabel30.setText("Precio (S/):");

        jLabel26.setText(" ");

        jLabel29.setText("subtotal:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo3, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(txtNombre4))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombre6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombre7, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnActualizarStock2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtCodigo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(txtNombre5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnGuardar2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jLabel28)
                            .addComponent(txtNombre6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(txtNombre7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizarStock2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Nuevo ");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        btnGuardar1.setText("Guardar Venta");
        btnGuardar1.addActionListener(this::btnGuardar1ActionPerformed);

        btnGuardar3.setText("Agregar");
        btnGuardar3.addActionListener(this::btnGuardar3ActionPerformed);

        btnGuardar4.setText("Quitar");
        btnGuardar4.addActionListener(this::btnGuardar4ActionPerformed);

        btnGuardar5.setText("limpiar");
        btnGuardar5.addActionListener(this::btnGuardar5ActionPerformed);

        btnGuardar6.setText("Facturar");
        btnGuardar6.addActionListener(this::btnGuardar6ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnGuardar5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarFormularioVenta();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String documento = txtCantidad2.getText().trim();
        if (documento.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese un número de documento para buscar.");
            return;
        }
        List<LOGICA.ClienteClass> lista = clienteDAO.buscarClientes(documento);
        if (lista.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
            txtNombreCliente1.setText("");
            idClienteSeleccionado = 0;
        } else {
            LOGICA.ClienteClass cli = lista.get(0);
            txtNombreCliente1.setText(cli.getNombreCliente());
            idClienteSeleccionado = cli.getIdCliente();
        }
    }

    private void btnGuardar2ActionPerformed(java.awt.event.ActionEvent evt) {
        agregarProductoAlCarrito();
    }

    private void btnGuardar3ActionPerformed(java.awt.event.ActionEvent evt) {
        agregarProductoAlCarrito();
    }

    private void agregarProductoAlCarrito() {
        String codigo = txtCodigo3.getText().trim();
        if (codigo.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el código del producto.");
            return;
        }
        List<Object[]> productos = productoDAO.buscarMedicamentos(codigo);
        if (productos.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }
        Object[] prod = productos.get(0);
        BigDecimal precio = (BigDecimal) prod[5];
        int idProducto = (int) prod[0];
        String nombreProd = (String) prod[2];
        String codigoProd = (String) prod[1];

        String cantStr = txtNombre6.getText().trim();
        if (cantStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese la cantidad.");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Cantidad inválida.");
            return;
        }
        if (cantidad <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a cero.");
            return;
        }
        int stockDisponible = (int) prod[6];
        if (cantidad > stockDisponible) {
            javax.swing.JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock actual: " + stockDisponible);
            return;
        }

        BigDecimal subtotal = precio.multiply(new BigDecimal(cantidad));
        Object[] fila = new Object[6];
        fila[0] = idProducto;
        fila[1] = codigoProd;
        fila[2] = nombreProd;
        fila[3] = precio;
        fila[4] = cantidad;
        fila[5] = subtotal;
        modeloCarrito.addRow(fila);
        calcularTotalVenta();
        txtCodigo3.setText("");
        txtNombre4.setText("");
        txtNombre5.setText("");
        txtNombre6.setText("");
        txtNombre7.setText("");
        txtCodigo3.requestFocus();
    }

    private void btnGuardar4ActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tablaMedicamentos2.getSelectedRow();
        if (fila < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto del carrito para quitar.");
            return;
        }
        modeloCarrito.removeRow(fila);
        calcularTotalVenta();
    }

    private void btnGuardar5ActionPerformed(java.awt.event.ActionEvent evt) {
        modeloCarrito.setRowCount(0);
        calcularTotalVenta();
    }

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {
        facturarVenta();
    }

    private void btnGuardar6ActionPerformed(java.awt.event.ActionEvent evt) {
        facturarVenta();
    }

    private void facturarVenta() {
        if (idClienteSeleccionado == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
            return;
        }
        if (modeloCarrito.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Agregue al menos un producto al carrito.");
            return;
        }
        String efectivoStr = txtNombre9.getText().trim();
        if (efectivoStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el monto con el que paga el cliente.");
            return;
        }
        BigDecimal efectivo;
        try {
            efectivo = new BigDecimal(efectivoStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Monto de efectivo inválido.");
            return;
        }
        if (efectivo.compareTo(totalVenta) < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "El efectivo no cubre el total de la venta.");
            return;
        }

        LOGICA.PedidoClass pedido = new LOGICA.PedidoClass();
        pedido.setIdCliente(idClienteSeleccionado);
        pedido.setIdEmpleado(idEmpleadoActivo);
        pedido.setFechaPedido(new java.util.Date());
        pedido.setMontoPagado(totalVenta);
        pedido.setEstado("Pagada");

        List<LOGICA.DetallePedidoClass> detalles = new ArrayList<>();
        for (int i = 0; i < modeloCarrito.getRowCount(); i++) {
            LOGICA.DetallePedidoClass det = new LOGICA.DetallePedidoClass();
            det.setIdProducto((int) modeloCarrito.getValueAt(i, 0));
            det.setPrecioUnidad((BigDecimal) modeloCarrito.getValueAt(i, 3));
            det.setCantidad((int) modeloCarrito.getValueAt(i, 4));
            detalles.add(det);
        }

        boolean exito = pedidoDAO.registrarVenta(pedido, detalles);
        if (exito) {
            javax.swing.JOptionPane.showMessageDialog(this, "Venta registrada con éxito.");
            limpiarFormularioVenta();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al registrar la venta.");
        }
    }

    private void txtNombre10ActionPerformed(java.awt.event.ActionEvent evt) {
        calcularVuelto();
    }

    private void btnActualizarStock2ActionPerformed(java.awt.event.ActionEvent evt) {
        String codigo = txtCodigo3.getText().trim();
        if (codigo.isEmpty()) return;
        List<Object[]> productos = productoDAO.buscarMedicamentos(codigo);
        if (!productos.isEmpty()) {
            Object[] prod = productos.get(0);
            txtNombre5.setText((String) prod[2]);
            txtNombre4.setText(prod[5].toString());
            txtNombre7.setText(prod[5].toString());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarStock2;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnGuardar2;
    private javax.swing.JButton btnGuardar3;
    private javax.swing.JButton btnGuardar4;
    private javax.swing.JButton btnGuardar5;
    private javax.swing.JButton btnGuardar6;
    private com.toedter.calendar.JDateChooser dcFechaFin2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaMedicamentos2;
    private javax.swing.JTextField txtCantidad2;
    private javax.swing.JTextField txtCodigo3;
    private javax.swing.JTextField txtDocCliente1;
    private javax.swing.JTextField txtNombre10;
    private javax.swing.JTextField txtNombre4;
    private javax.swing.JTextField txtNombre5;
    private javax.swing.JTextField txtNombre6;
    private javax.swing.JTextField txtNombre7;
    private javax.swing.JTextField txtNombre8;
    private javax.swing.JTextField txtNombre9;
    private javax.swing.JTextField txtNombreCliente1;
    // End of variables declaration//GEN-END:variables
}
