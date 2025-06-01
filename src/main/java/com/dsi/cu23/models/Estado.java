package com.dsi.cu23.models;

import java.util.ArrayList;
import java.util.List;

public class Estado {
    public static final String PTE_REVISION = "PTE_REVISION";
    public static final String RECHAZADO = "RECHAZADO";
    public static final String BLOQUEADO_EN_REVISION = "BLOQUEADO_EN_REVISION";

    public static final String AMBITO_EVENTO_SISMICO = "EVENTO_SISMICO";
    public static final String AMBITO_OTRO = "OTRO";

    private String estado;
    private String ambito;

    public Estado(String estado, String ambito) {
        this.estado = estado;
        this.ambito = ambito;
    }

    public boolean sosPteRevision() {
        return PTE_REVISION.equals(this.estado);
    }

    public boolean sosEstadoBloqueadoEnRevision() {
        return BLOQUEADO_EN_REVISION.equals(this.estado);
    }

    public boolean sosRechazado() {
        return RECHAZADO.equals(this.estado);
    }

    public boolean sosAmbitoEventoSismico() {
        return AMBITO_EVENTO_SISMICO.equals(this.ambito);
    }



    // Getters
    public String getEstado() { return estado; }
    public String getAmbito() { return ambito; }

    // Setters
    public void setEstado(String estado) { this.estado = estado; }
    public void setAmbito(String ambito) { this.ambito = ambito; }

    public static List<Estado> mockEstados() {
        List<Estado> estados = new ArrayList<>();
        estados.add(new Estado(PTE_REVISION, AMBITO_EVENTO_SISMICO));
        estados.add(new Estado(PTE_REVISION, AMBITO_OTRO));
        estados.add(new Estado(RECHAZADO, AMBITO_OTRO));
        estados.add(new Estado(RECHAZADO, AMBITO_EVENTO_SISMICO));
        estados.add(new Estado(BLOQUEADO_EN_REVISION, AMBITO_EVENTO_SISMICO));
        return estados;
    }
}