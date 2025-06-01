package com.dsi.cu23;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventoSismico {
    private LocalDateTime fechaHoraOcurrencia;
    private Coordenadas coordenadas;
    private MagnitudRichter valorMagnitud;
    private List<CambioEstado> cambiosEstado = CambioEstado.mockCambios();

    public EventoSismico(LocalDateTime fechaHoraOcurrencia, Coordenadas coordenadas) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.coordenadas = coordenadas;
    }

    public static List<EventoSismico> buscarEventosSinRevision() {
        List<EventoSismico> eventos = EventoSismico.generarEventosMock()
                                                    .stream()
                                                    .filter(evento -> {
                                                        CambioEstado cambioActual = evento.cambiosEstado.stream()
                                                                .filter(CambioEstado::esActual)
                                                                .filter(CambioEstado::sosPteRevision)
                                                                .findFirst()
                                                                .orElse(null);
                                                        return cambioActual != null;
                                                    })
                                                    .collect(Collectors.toList());

        return eventos;
    }

    private static List<EventoSismico> generarEventosMock() {
        List<EventoSismico> eventos = new ArrayList<>();
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 1, 12, 0), new Coordenadas(37.77, -122.42)));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 2, 14, 30), new Coordenadas(34.05, -118.25)));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 3, 9, 45), new Coordenadas(36.16, -115.15)));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 4, 11, 0), new Coordenadas(40.71, -74.01)));
        return eventos;
    }

    private LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }

    // Getters
    public LocalDateTime getFechaOcurrencia() {
        return fechaHoraOcurrencia;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }

    public MagnitudRichter getMagnitud() {
        return valorMagnitud;
    }

    // Setters
    public void setFechaOcurrencia(LocalDateTime fechaHoraOcurrencia) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
    }

    public void setCoordenadas(Coordenadas coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public String toString() {
        return "Evento Sismico a las " + fechaHoraOcurrencia ;
    }

    public String toLabel() {
        return String.format("Evento: %s, Ubicación: %s",
                fechaHoraOcurrencia, coordenadas);
    }

    public void bloquear(Estado estadoBloqueadoEnRevision) {
        CambioEstado cambioActual = this.cambiosEstado.stream()
                .filter(CambioEstado::esActual)
                .filter(CambioEstado::sosPteRevision)
                .findFirst()
                .orElse(null);

        if (cambioActual == null) {
            throw new IllegalStateException("No hay un cambio de estado pendiente de revisión actual.");
        }

        LocalDateTime fechaHoraActual = obtenerFechaActual();
        cambioActual.setFechaFin(fechaHoraActual);
        CambioEstado nuevoCambioEstado = new CambioEstado(
                fechaHoraActual,
                null,
                estadoBloqueadoEnRevision
        );
        this.cambiosEstado.add(nuevoCambioEstado);
    }

    public void obtenerDatosRegistrados() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerDatosRegistrados'");
    }

}

class Coordenadas {
    private double latitud;
    private double longitud;

    public Coordenadas(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    // Setters
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", latitud, longitud);
    }
}

class AlcanceSismo {
    private String alcance;

    public AlcanceSismo(String alcance) {
        this.alcance = alcance;
    }

    public String getAlcance() {
        return this.alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }
}

class MagnitudRichter {
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

class ClasificacionSismo {
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

class OrigenDeGeneracion {
    private String origen;

    public OrigenDeGeneracion(String origen) {
        this.origen = origen;
    }

    public String getOrigen() {
        return this.origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
