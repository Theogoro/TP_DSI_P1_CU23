package com.dsi.cu23;

public class Empleado {
    private String nombre;
    private String apellido;
    private String mail;

    public Empleado(String nombre, String apellido, String mail) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getMail() { return mail; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setMail(String mail) { this.mail = mail; }
}