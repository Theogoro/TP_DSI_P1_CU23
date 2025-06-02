package com.dsi.cu23.models;

import java.util.Date;
import java.util.List;

public class Sismografo {
    private String nombre;
    private String idSismografo;
    private String nroSerie;
    private Date fechaAdquisicion;
    private String modelo;
    private String fabricante;
    private String caracteristicas;
    private EstacionSismologica estacionSismologica;
    private SerieTemporal serieTemporal; // Assuming sosDeSerieTemporal might relate to a SerieTemporal

    public Sismografo(String nombre, String idSismografo, String nroSerie, Date fechaAdquisicion, String modelo, String fabricante, String caracteristicas, EstacionSismologica estacionSismologica, SerieTemporal serieTemporal) {
        this.nombre = nombre;
        this.idSismografo = idSismografo;
        this.nroSerie = nroSerie;
        this.fechaAdquisicion = fechaAdquisicion;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.caracteristicas = caracteristicas;
        this.estacionSismologica = estacionSismologica;
        this.serieTemporal = serieTemporal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdSismografo() {
        return idSismografo;
    }

    public void setIdSismografo(String idSismografo) {
        this.idSismografo = idSismografo;
    }

    public String getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(String nroSerie) {
        this.nroSerie = nroSerie;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public EstacionSismologica getEstacionSismologica() {
        return estacionSismologica;
    }

    public void setEstacionSismologica(EstacionSismologica estacionSismologica) {
        this.estacionSismologica = estacionSismologica;
    }
    
    public SerieTemporal getSerieTemporal() {
        return serieTemporal;
    }

    public void setSerieTemporal(SerieTemporal serieTemporal) {
        this.serieTemporal = serieTemporal;
    }

    public boolean sosDeSerieTemporal(SerieTemporal serieTemporal) {
        // Aca hacemos un mock porque no tenemos la persistencia de datos para recuperar la serie temporal
        return true; // Assuming this always returns true for mock purposes
    }

    public EstacionSismologica buscarEstacionSismologica() {
        return this.estacionSismologica.getEstacion();
    }

    public static List<Sismografo> mockSismografos() {
        List<EstacionSismologica> estaciones = EstacionSismologica.mockEstacionesSismologicas();
        // This method should return a list of mock Sismografo objects for testing purposes
        return List.of(
            new Sismografo("Sismografo A", "SG001", "SN123456", new Date(), "Modelo X", "Fabricante Y", "Caracteristicas A", estaciones.get(0), SerieTemporal.mockSeriesTemporales().get(0)),
            new Sismografo("Sismografo B", "SG002", "SN654321", new Date(), "Modelo Z", "Fabricante W", "Caracteristicas B", estaciones.get(1), SerieTemporal.mockSeriesTemporales().get(0)),
            new Sismografo("Sismografo C", "SG003", "SN789012", new Date(), "Modelo Y", "Fabricante Z", "Caracteristicas C", estaciones.get(0), SerieTemporal.mockSeriesTemporales().get(0))
        );
    }
}
