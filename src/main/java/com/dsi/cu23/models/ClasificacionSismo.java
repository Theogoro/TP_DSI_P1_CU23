package com.dsi.cu23.models;

public class ClasificacionSismo {
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

    public static ClasificacionSismo mockClasificacionSismo() {
        // La clasificación: por la profundidad a la que se originan, los sismos se clasifican como sismos superficiales
        // (0 a 60 km), intermedios (61 a 300 km) o profundos (301 a 650 km).
        double profundidad = Math.random() * 650;
        String clasificacion;
        if (profundidad <= 60) {
            clasificacion = "Superficial";
        }
        else if (profundidad <= 300) {
            clasificacion = "Intermedio";
        }
        else {
            clasificacion = "Profundo";
        }
        return new ClasificacionSismo(clasificacion);
    }

    public static String[] mockClasificacionSismos() {
        return new String[] {
            "Superficial",
            "Intermedio",
            "Profundo"
        };
    }
}
