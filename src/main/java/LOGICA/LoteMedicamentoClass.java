package LOGICA;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class LoteMedicamentoClass {
    private long idLote;
    private int idMedicamento;
    private String numeroLote;
    private Date fechaFabricacion;
    private Date fechaVencimiento;
    private int stockLote;
    private BigDecimal costoUnitario;
    private String estado;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private String nombreMedicamento;

    public LoteMedicamentoClass() {
        this.stockLote = 0;
        this.costoUnitario = BigDecimal.ZERO;
        this.estado = "Disponible";
    }

    public long getIdLote() { return idLote; }
    public void setIdLote(long idLote) { this.idLote = idLote; }
    public int getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(int idMedicamento) { this.idMedicamento = idMedicamento; }
    public String getNumeroLote() { return numeroLote; }
    public void setNumeroLote(String numeroLote) { this.numeroLote = numeroLote; }
    public Date getFechaFabricacion() { return fechaFabricacion; }
    public void setFechaFabricacion(Date fechaFabricacion) { this.fechaFabricacion = fechaFabricacion; }
    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public int getStockLote() { return stockLote; }
    public void setStockLote(int stockLote) { this.stockLote = stockLote; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }
    public Timestamp getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }
    public String getNombreMedicamento() { return nombreMedicamento; }
    public void setNombreMedicamento(String nombreMedicamento) { this.nombreMedicamento = nombreMedicamento; }

    @Override
    public String toString() {
        return numeroLote != null ? numeroLote : "";
    }
}
