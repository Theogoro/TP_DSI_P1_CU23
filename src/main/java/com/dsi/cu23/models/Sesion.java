package com.dsi.cu23.models;

public class Sesion {
    private Usuario usuario;
    private String fechaInicio;
    private String fechaFin;

    public Sesion(Usuario usuario, String fechaInicio, String fechaFin) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Sesion(Usuario usuario, String fechaInicio) {
        this(usuario, fechaInicio, null);
    }

    public Empleado conocerUsuario() {
        return this.usuario.getEmpleado();
    }

    public static Sesion mockSesion() {
        Empleado empleado = new Empleado("Juan", "Pérez", "juan.perez@gmail.com");
        Usuario usuario = new Usuario("juanperez", "contrasenia123", "2023-10-01", empleado);
        return new Sesion(usuario, "2023-10-01 10:00:00");
    }
}