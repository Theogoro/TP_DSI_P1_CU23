package com.dsi.cu23.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.dsi.cu23.records.RecordMuestra;
import com.dsi.cu23.utils.LocalDateTimeFormat;

public class EventoSismico {
    private LocalDateTime fechaHoraOcurrencia;
    private Coordenadas[] coordenadas; // 1er epicentro y 2do hipoepicentro
    private MagnitudRichter valorMagnitud;
    private List<CambioEstado> cambiosEstado = CambioEstado.mockCambios();
    private ClasificacionSismo clasificacionSismo;
    private OrigenDeGeneracion origen;
    private AlcanceSismo alcance;
    private List<SerieTemporal> seriesTemporales;

    public EventoSismico(LocalDateTime fechaHoraOcurrencia, Coordenadas[] coordenadas) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.coordenadas = coordenadas;

        // Mock data for demo
        this.valorMagnitud = MagnitudRichter.mockMagnitudRichter();
        this.clasificacionSismo = ClasificacionSismo.mockClasificacionSismo();
        this.origen = OrigenDeGeneracion.mockOrigenDeGeneracion();
        this.alcance = AlcanceSismo.mockAlcanceSismo();
        this.seriesTemporales = SerieTemporal.mockSeriesTemporales();
    }

    public static List<EventoSismico> buscarEventosSinRevision() {
        List<EventoSismico> eventos = EventoSismico
                .generarEventosMock()
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
                LocalDateTime.of(2023, 10, 1, 12, 0),
                new Coordenadas[] {
                    new Coordenadas(37.77, -122.42), // epicentro
                    new Coordenadas(37.80, -122.45)  // hipoepicentro
                }));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 2, 14, 30),
                new Coordenadas[] {
                    new Coordenadas(34.05, -118.25),
                    new Coordenadas(34.08, -118.28)
                }));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 3, 9, 45),
                new Coordenadas[] {
                    new Coordenadas(36.16, -115.15),
                    new Coordenadas(36.18, -115.18)
                }));
        eventos.add(new EventoSismico(
                LocalDateTime.of(2023, 10, 4, 11, 0),
                new Coordenadas[] {
                    new Coordenadas(40.71, -74.01),
                    new Coordenadas(40.74, -74.04)
                }));
        return eventos;
    }

    private LocalDateTime obtenerFechaActual() {
        return LocalDateTime.now();
    }

    // Getters
    public LocalDateTime getFechaOcurrencia() {
        return fechaHoraOcurrencia;
    }

    public Coordenadas[] getCoordenadas() {
        return coordenadas;
    }

    public double getMagnitud() {
        return valorMagnitud.getMagnitud();
    }

    // Setters
    public void setFechaOcurrencia(LocalDateTime fechaHoraOcurrencia) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
    }

    public void setCoordenadas(Coordenadas[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    @Override
    public String toString() {
        return LocalDateTimeFormat.format(fechaHoraOcurrencia);
    }

    public String toLabel() {
        return String.format("Evento: %s, Ubicación: %s",
                LocalDateTimeFormat.format(fechaHoraOcurrencia), coordenadas);
    }

    public void bloquear(Estado estadoBloqueadoEnRevision) {
        System.out.println("Bloqueando evento sismico: " + this.toString());
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
                estadoBloqueadoEnRevision,
                null);
        this.cambiosEstado.add(nuevoCambioEstado);
    }

    public HashMap<String,Object> obtenerDatosRegistrados() {
        String clasificacion = this.clasificacionSismo.getClasificacion();
        String origenGeneracion = this.origen.getOrigenGeneracion();
        String alcance = this.alcance.getAlcance();

        HashMap<SerieTemporal, RecordMuestra[]> detalleSeries = new HashMap<>();
        for (SerieTemporal serie : this.seriesTemporales) {
            detalleSeries.put(serie, serie.buscarVelocidadLongitudFrecuencia());
        }
        return new HashMap<String, Object>() {{
            put("Fecha de ocurrencia", fechaHoraOcurrencia);
            // put("Coordenadas", coordenadas);
            // put("Magnitud", valorMagnitud.getMagnitud());
            put("Clasificación del sismo", clasificacion);
            put("Origen de generación", origenGeneracion);
            put("Alcance del sismo", alcance);
            put("Series temporales", detalleSeries);
        }};
    }

    public List<SerieTemporal> getSerieTemporal() {
        return seriesTemporales
            .stream()
            .map(serie -> {
                return serie.getSerieTemporal();
            })
            .collect(Collectors.toList());
    }

    public AlcanceSismo getAlcance() {
        return this.alcance;
    }

    public OrigenDeGeneracion getOrigen() {
        return this.origen;
    }

    public void registrarRechazo(Estado estadoRechazado, Empleado deLaSesion, LocalDateTime fechaHoraActual) {
        for (CambioEstado cambio : cambiosEstado) {
            if (cambio.esActual()) {
                cambio.setFechaFin(fechaHoraActual);
                cambio.setResponsable(deLaSesion.toString());
            }
        }

        CambioEstado nuevoCambioEstado = new CambioEstado(
                fechaHoraActual,
                null,
                estadoRechazado,
                deLaSesion.toString()
        );
        cambiosEstado.add(nuevoCambioEstado);

        System.out.println("Evento sismico rechazado: " + this.toString());
    }
}
