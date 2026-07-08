package LOGICA;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ProductoClass {
    private int idMedicamento;
    private String codigo;
    private String nombreComercial;
    private String principioActivo;
    private String concentracion;
    private String presentacion;
    private String unidadMedida;
    private String registroSanitario;
    private boolean requiereReceta;
    private int idCategoria;
    private Integer idProveedor;
    private Integer idLaboratorio;
    private String nombreCategoria;
    private String nombreProveedor;
    private String laboratorio;
    private BigDecimal precioCompra;
    private BigDecimal precio;
    private int stock;
    private int stockMinimo;
    private Integer stockMaximo;
    private String estado;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    public ProductoClass() {
        this.unidadMedida = "Unidad";
        this.precioCompra = BigDecimal.ZERO;
        this.precio = BigDecimal.ZERO;
        this.stockMinimo = 10;
        this.estado = "Activo";
    }

    public ProductoClass(int idProducto, String codigo, String nombreComercial, String principioActivo, String laboratorio, BigDecimal precioUnidad, int stock) {
        this();
        this.idMedicamento = idProducto;
        this.codigo = codigo;
        this.nombreComercial = nombreComercial;
        this.principioActivo = principioActivo;
        this.laboratorio = laboratorio;
        this.precio = precioUnidad;
        this.stock = stock;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdProducto() {
        return idMedicamento;
    }

    public void setIdProducto(int idProducto) {
        this.idMedicamento = idProducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public boolean getRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdLaboratorio() {
        return idLaboratorio;
    }

    public void setIdLaboratorio(Integer idLaboratorio) {
        this.idLaboratorio = idLaboratorio;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getLaboratorioNombre() {
        return laboratorio;
    }

    public void setLaboratorioNombre(String laboratorioNombre) {
        this.laboratorio = laboratorioNombre;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioUnidad() {
        return precio;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precio = precioUnidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(Integer stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public boolean tieneStockSuficiente(int cantidad) {
        return stock >= cantidad;
    }

    public boolean requiereAlertaStock() {
        return stock <= stockMinimo;
    }

    @Override
    public String toString() {
        return nombreComercial != null ? nombreComercial : "";
    }
}
