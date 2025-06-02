package com.dsi.cu23.models;

public class OrigenDeGeneracion {
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

    public static OrigenDeGeneracion mockOrigenDeGeneracion() {
        // sismo interplaca, sismo volcánico, sismo provocado por explosiones de minas, etc
        String[] origenes = {"Sismo Interplaca", "Sismo Volcánico", "Sismo provocado por explosiones de minas"};
        String origen = origenes[(int) (Math.random() * origenes.length)];
        return new OrigenDeGeneracion(origen);
    }

    public String getOrigenGeneracion() {
        return this.origen;
    }

    public static String[] mockOrigenesDeGeneracion() {
        return new String[] {
            "Sismo Interplaca",
            "Sismo Volcánico",
            "Sismo provocado por explosiones de minas"
        };
    }
}
