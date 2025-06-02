package com.dsi.cu23.models;

public class MagnitudRichter {
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

    public static MagnitudRichter mockMagnitudRichter() {
        String[] descripciones = MagnitudRichter.mockMagnitudesRichter();
        double magnitud = Math.random() * 10; // Random magnitude between 0 and 10
        String descripcion;
        if (magnitud < 2.0) {
            descripcion = descripciones[0];
        }
        else if (magnitud < 4.0) {
            descripcion = descripciones[1];
        }
        else if (magnitud < 6.0) {
            descripcion = descripciones[2];
        }
        else if (magnitud < 8.0) {
            descripcion = descripciones[3];
        }
        else {
            descripcion = descripciones[4];
        }
        return new MagnitudRichter(magnitud, descripcion);
    }

    public static String[] mockMagnitudesRichter() {
        return new String[] {
            "Micro (0.0 - 1.9)",
            "Leve (2.0 - 3.9)",
            "Moderado (4.0 - 5.9)",
            "Fuerte (6.0 - 7.9)",
            "Extremo (8.0 y más)"
        };
    }
}
