package com.dsi.cu23;

public class Usuario {
    private String nombre;
    private String contrasenia;
    private String fechaAlta;
    private Empleado empleado;

    public Usuario(String nombre, String contrasenia, String fechaAlta, Empleado empleado) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.fechaAlta = fechaAlta;
        this.empleado = empleado;
    }

    public Empleado conocerEmpleado() {
        return this.empleado;
    }

    @Override
    public String toString() {
        return String.format("Usuario: %s, Fecha de alta: %s, Empleado: %s %s, Email: %s",
                this.nombre, this.fechaAlta, this.empleado.getNombre(),
                this.empleado.getApellido(), this.empleado.getMail());
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getContrasenia() { return contrasenia; }
    public String getFechaAlta() { return fechaAlta; }
    public Empleado getEmpleado() { return empleado; }
}