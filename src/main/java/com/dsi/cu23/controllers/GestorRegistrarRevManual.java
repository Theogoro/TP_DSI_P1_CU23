package com.dsi.cu23.controllers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dsi.cu23.interfaces.InterfazGenerarSismograma;
import com.dsi.cu23.interfaces.InterfazRegistrarRevManual;
import com.dsi.cu23.models.*;

/**
 * Gestor para el manejo de la lógica de registro manual de revisiones
 */
public class GestorRegistrarRevManual {
    private InterfazRegistrarRevManual interfaz;
    private List<EventoSismico> listaDeEventos;
    private EventoSismico eventoSeleccionado;
    private Estado estadoBloqueadoEnRevision;
    private HashMap<EstacionSismologica, List<SerieTemporal>> estacionesConSeries;
    
    public GestorRegistrarRevManual(InterfazRegistrarRevManual interfaz) {
        this.interfaz = interfaz;
        this.buscarEventosSinRevision();
    }
    
    public void buscarEventosSinRevision() {
        System.out.println("Buscando eventos sin revisión...");
        this.listaDeEventos = EventoSismico.buscarEventosSinRevision();
        if (this.hayEventosSinRevision()) {
            this.ordenarEventosPorFechaOcurrencia();
            this.interfaz.mostrarEventos(this.listaDeEventos);
            this.interfaz.solicitarSeleccionEvento();
        }
    }

    private void ordenarEventosPorFechaOcurrencia() {
        this.listaDeEventos.sort((e1, e2) -> e1.getFechaOcurrencia().compareTo(e2.getFechaOcurrencia()));
    }

    private boolean hayEventosSinRevision() {
        return (long) listaDeEventos.size() > 0;
    }

    public void finCu() {
        this.interfaz.cerrarVentana();
    }
    
    // Getters
    public List<EventoSismico> getEventos() { return listaDeEventos; }
    public InterfazRegistrarRevManual getInterfaz() { return interfaz; }

    public void solicitarSeleccionEvento(Integer filaSeleccionada) {
        if (filaSeleccionada == null || filaSeleccionada < 0 || filaSeleccionada >= listaDeEventos.size()) {
            throw new IllegalArgumentException("Fila seleccionada inválida: " + filaSeleccionada);
        }
        this.eventoSeleccionado = listaDeEventos.get(filaSeleccionada);
        this.bloquearEvento();
        this.buscarDatosRegistrados();
    }

    private void buscarDatosRegistrados() {
        HashMap<String, Object> datos = this.eventoSeleccionado.obtenerDatosRegistrados();
        this.interfaz.mostrarClasificacionOrigenAlcance(datos);
        // HashMap<SerieTemporal, RecordMuestra[]> series = (HashMap<SerieTemporal, RecordMuestra[]>) datos.get("Series temporales");
        this.clasificarDatosPorEstacion();
        List<BufferedImage> sismogramas = this.generarSismogramaPorEstacion();
        this.interfaz.mostrarSismogramas(new ArrayList<>(estacionesConSeries.keySet()), sismogramas);
        this.habilitarOpcionVisualizarMapa();
    }

    private void habilitarOpcionVisualizarMapa() {
        this.interfaz.habilitarOpcionVerMapa();
    }

    private void clasificarDatosPorEstacion() {
        SerieTemporal[] series = this.eventoSeleccionado.getSerieTemporal().toArray(new SerieTemporal[0]);
        List<Sismografo> sismografos = Sismografo.mockSismografos();
        this.estacionesConSeries = new HashMap<>();

        for (Sismografo sismografo : sismografos) {
            for (int i = 0; i < series.length; i++) {
                if (series[i] == null) continue; // Evitar procesar series nulas

                SerieTemporal serie = series[i];
                if (sismografo.sosDeSerieTemporal(serie)) {
                    EstacionSismologica estacion = sismografo.buscarEstacionSismologica();
                    if (!estacionesConSeries.containsKey(estacion)) {
                        estacionesConSeries.put(estacion, new ArrayList<SerieTemporal>());
                    }
                    estacionesConSeries.get(estacion).add(serie);
                    series[i] = null; // Evitar duplicados
                }
            }
        }
    }

    
    private List<BufferedImage> generarSismogramaPorEstacion() {
        List<BufferedImage> sismogramas = new ArrayList<>();
        for (EstacionSismologica estacion : estacionesConSeries.keySet()) {
            List<SerieTemporal> series = estacionesConSeries.get(estacion);
            BufferedImage sismograma = new InterfazGenerarSismograma().generarSismogramaXEstacion(estacion, series);
            sismogramas.add(sismograma);
        }
        return sismogramas;
    }

    private void bloquearEvento() {
        this.buscarEstadoBloqueadoEnRevision();
        this.eventoSeleccionado.bloquear(this.estadoBloqueadoEnRevision);
    }

    private void buscarEstadoBloqueadoEnRevision() {
        List<Estado> estados = Estado.mockEstados();
        for (Estado estado : estados) {
            if (estado.sosAmbitoEventoSismico() && estado.sosEstadoBloqueadoEnRevision()) {
                this.estadoBloqueadoEnRevision = estado;
                break;
            }
        }
    }

    public void tomarRechazo() {
      
    }


    // Metodos para tener soporte de opciones en los combos de la interfaz
    public String[] getClasificacionesOpciones() {
        return ClasificacionSismo.mockClasificacionSismos();
    }

    public String[] getOrigenesOpciones() {
        return OrigenDeGeneracion.mockOrigenesDeGeneracion();
    }

    public String[] getAlcancesOpciones() {
        return AlcanceSismo.mockAlcancesSismos();
    }

    public String[] getMagnitudesOpciones() {
        return MagnitudRichter.mockMagnitudesRichter();
    }
}
