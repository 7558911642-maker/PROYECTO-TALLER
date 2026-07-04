package LOGICA;

import java.util.Date;

public class EmpleadoClass {
    private int idEmpleado;
    private String apellidos;
    private String nombre;
    private String cargo;
    private Date fechaNacimiento;
    private Date fechaContratacion;
    private String direccion;

    public EmpleadoClass() {
    }

    public EmpleadoClass(int idEmpleado, String apellidos, String nombre, String cargo, Date fechaNacimiento, Date fechaContratacion, String direccion) {
        this.idEmpleado = idEmpleado;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.cargo = cargo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaContratacion = fechaContratacion;
        this.direccion = direccion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}
