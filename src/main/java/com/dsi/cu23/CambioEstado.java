package com.dsi.cu23;

import java.time.LocalDateTime;
import java.util.List;

public class CambioEstado {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Estado estado;

    public CambioEstado(LocalDateTime fechaInicio, LocalDateTime fechaFin, Estado estado) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public boolean esActual() {
        return this.fechaFin == null;
    }

    public boolean sosPteRevision() { return this.estado.sosPteRevision(); }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    // Getters
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public Estado getEstado() { return estado; }

    public static List<CambioEstado> mockCambios() {
        List<CambioEstado> cambios = new java.util.ArrayList<>();
        cambios.add(
            new CambioEstado(
            LocalDateTime.of(2023, 10, 1, 12, 0),
            null,
            new Estado(Estado.PTE_REVISION, Estado.AMBITO_EVENTO_SISMICO)
            )
        );
        return cambios;
    }
}