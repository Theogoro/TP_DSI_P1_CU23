package com.dsi.cu23.models;

import java.util.List;

public class EstacionSismologica {
    private String codigoEstacion;
    private double latitud;
    private double longitud;
    private String nombre;

    public EstacionSismologica(String codigoEstacion, double latitud, double longitud, String nombre) {
        this.codigoEstacion = codigoEstacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public String getCodigoEstacion() {
        return codigoEstacion;
    }

    public void setCodigoEstacion(String codigoEstacion) {
        this.codigoEstacion = codigoEstacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstacionSismologica getEstacion() {
        return this;
    }

    public static List<EstacionSismologica> mockEstacionesSismologicas() {
        return List.of(
            new EstacionSismologica("EST001", -34.61, -58.38, "Estación Buenos Aires"),
            new EstacionSismologica("EST002", -33.46, -70.65, "Estación Santiago"),
            new EstacionSismologica("EST003", -12.04, -77.03, "Estación Lima")
        );
    }
}
