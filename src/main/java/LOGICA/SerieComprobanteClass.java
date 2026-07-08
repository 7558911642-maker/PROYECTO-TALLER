package LOGICA;

import java.sql.Timestamp;

public class SerieComprobanteClass {
    private int idSerie;
    private String tipoComprobante;
    private String serie;
    private int correlativoActual;
    private String estado;
    private Timestamp creadoEn;

    public SerieComprobanteClass() {
        this.tipoComprobante = "BOLETA";
        this.estado = "Activo";
    }

    public int getIdSerie() { return idSerie; }
    public void setIdSerie(int idSerie) { this.idSerie = idSerie; }
    public String getTipoComprobante() { return tipoComprobante; }
    public void setTipoComprobante(String tipoComprobante) { this.tipoComprobante = tipoComprobante; }
    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }
    public int getCorrelativoActual() { return correlativoActual; }
    public void setCorrelativoActual(int correlativoActual) { this.correlativoActual = correlativoActual; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    @Override
    public String toString() {
        return tipoComprobante + " - " + serie;
    }
}
