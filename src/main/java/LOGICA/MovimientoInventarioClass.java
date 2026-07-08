package LOGICA;

import java.sql.Timestamp;

public class MovimientoInventarioClass {
    private long idMovimiento;
    private int idMedicamento;
    private Long idLote;
    private String tipoMovimiento;
    private int cantidad;
    private int stockAnterior;
    private int stockNuevo;
    private String tablaReferencia;
    private Long idReferencia;
    private int idUsuario;
    private String observacion;
    private Timestamp fecha;
    private String nombreMedicamento;
    private String numeroLote;

    public MovimientoInventarioClass() {
        this.tablaReferencia = "NINGUNA";
        this.fecha = new Timestamp(System.currentTimeMillis());
    }

    public long getIdMovimiento() { return idMovimiento; }
    public void setIdMovimiento(long idMovimiento) { this.idMovimiento = idMovimiento; }
    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }
    public Long getIdLote() { return idLote; }
    public void setIdLote(Long idLote) { this.idLote = idLote; }
    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public int getStockAnterior() { return stockAnterior; }
    public void setStockAnterior(int stockAnterior) { this.stockAnterior = stockAnterior; }
    public int getStockNuevo() { return stockNuevo; }
    public void setStockNuevo(int stockNuevo) { this.stockNuevo = stockNuevo; }
    public String getTablaReferencia() { return tablaReferencia; }
    public void setTablaReferencia(String tablaReferencia) { this.tablaReferencia = tablaReferencia; }
    public Long getIdReferencia() { return idReferencia; }
    public void setIdReferencia(Long idReferencia) { this.idReferencia = idReferencia; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }
    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }
}
