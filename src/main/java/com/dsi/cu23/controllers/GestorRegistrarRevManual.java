package com.dsi.cu23.controllers;

import java.util.List;

import com.dsi.cu23.interfaces.InterfazRegistrarRevManual;
import com.dsi.cu23.models.Estado;
import com.dsi.cu23.models.EventoSismico;

/**
 * Gestor para el manejo de la lógica de registro manual de revisiones
 */
public class GestorRegistrarRevManual {
    private InterfazRegistrarRevManual interfaz;
    private List<EventoSismico> listaDeEventos;
    private EventoSismico eventoSeleccionado;
    private Estado estadoBloqueadoEnRevision;
    
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
        this.eventoSeleccionado.obtenerDatosRegistrados();
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
}
