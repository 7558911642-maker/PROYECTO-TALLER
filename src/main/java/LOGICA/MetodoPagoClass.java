package LOGICA;

public class MetodoPagoClass {
    private int idMetodoPago;
    private String nombre;
    private boolean requiereReferencia;
    private String estado;

    public MetodoPagoClass() {
        this.estado = "Activo";
    }

    public int getIdMetodoPago() { return idMetodoPago; }
    public void setIdMetodoPago(int idMetodoPago) { this.idMetodoPago = idMetodoPago; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public boolean isRequiereReferencia() { return requiereReferencia; }
    public boolean getRequiereReferencia() { return requiereReferencia; }
    public void setRequiereReferencia(boolean requiereReferencia) { this.requiereReferencia = requiereReferencia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombre != null ? nombre : "";
    }
}
