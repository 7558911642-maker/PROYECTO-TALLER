package LOGICA;

import java.math.BigDecimal;

public class ProductoClass {
    
    private int idProducto;
    private String codigo;
    private String nombreComercial;
    private String principioActivo;
    private String laboratorio;
    private BigDecimal precioUnidad;
    private int stock;

    public ProductoClass() {
    }

    public ProductoClass(int idProducto, String codigo, String nombreComercial, String principioActivo, String laboratorio, BigDecimal precioUnidad, int stock) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombreComercial = nombreComercial;
        this.principioActivo = principioActivo;
        this.laboratorio = laboratorio;
        this.precioUnidad = precioUnidad;
        this.stock = stock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
}
