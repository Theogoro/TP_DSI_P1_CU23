package com.dsi.cu23.models;

public class MagnitudRichter {
    private double magnitud;
    private String descripcion;

    public MagnitudRichter(double magnitud, String descripcion) {
        this.magnitud = magnitud;
        this.descripcion = descripcion;
    }

    public double getMagnitud() {
        return this.magnitud;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
