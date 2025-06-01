package com.dsi.cu23.models;

public class ClasificacionSismo {
    private String clasificacion;

    public ClasificacionSismo(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
