package com.dsi.cu23.models;

public class AlcanceSismo {
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

    public static AlcanceSismo mockAlcanceSismo() {
        // Alcance: la distancia epicentral (km) entre el epicentro de un sismo y el punto de observación (ES). Ej.
        // sismos locales (hasta 100 km), sismos regionales (hasta 1000 km), o tele sismos (más de 1000 km).
        String[] alcances = AlcanceSismo.mockAlcancesSismos();
        String alcance = alcances[(int) (Math.random() * alcances.length)];
        return new AlcanceSismo(alcance);
    }

    public static String[] mockAlcancesSismos() {
        return new String[] {
            "Local (0-100 km)",
            "Regional (101-1000 km)",
            "Tele (más de 1000 km)"
        };
    }
}
