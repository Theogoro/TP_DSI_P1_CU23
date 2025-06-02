package com.dsi.cu23.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dsi.cu23.records.RecordMuestra;
import com.dsi.cu23.utils.LocalDateTimeFormat;

public class MuestraSismica {
  private LocalDateTime fechaHoraInicio;
  private LocalDateTime fechaHoraFin;
  private List<DetalleMuestraSismica> detalleMuestra;

  public MuestraSismica(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
    this.fechaHoraInicio = fechaHoraInicio;
    this.fechaHoraFin = fechaHoraFin;
    this.detalleMuestra = DetalleMuestraSismica.mockDetalleMuestraSismica();
  }

  public LocalDateTime getFechaHoraInicio() {
    return fechaHoraInicio;
  }

  public LocalDateTime getFechaHoraFin() {
    return fechaHoraFin;
  }

  public List<DetalleMuestraSismica> getDetalleMuestra() {
    return detalleMuestra;
  }

  public void addDetalleMuestra(DetalleMuestraSismica detalle) {
    this.detalleMuestra.add(detalle);
  }

  public RecordMuestra buscarVelocidadLongitudFrecuencia() {
    double velocidad = 0.0;
    double frecuencia = 0.0;
    double longitud = 0.0;

    for (DetalleMuestraSismica detalle : detalleMuestra) {
      if (detalle.sosVelocidadFrecuenciaLogitud()) {
        if (detalle.getTipoDato().getNombre().equals("Velocidad de onda")) {
          velocidad += detalle.getVelocidadOnda();
        }
        else if (detalle.getTipoDato().getNombre().equals("Frecuencia de onda")) {
          frecuencia += detalle.getFrecuenciaOnda();
        } else if (detalle.getTipoDato().getNombre().equals("Longitud")) {
          longitud += detalle.getLongitud();
        }
      }
    }
    return new RecordMuestra(velocidad, frecuencia, longitud);
  }

  // public String buscarVelocidadLongitudFrecuencia() {
  //   String resultados = " - Muestra Sismica:\n";
  //   String fechaHoraInicio = LocalDateTimeFormat.format(this.fechaHoraInicio);
  //   String fechaHoraFin = LocalDateTimeFormat.format(this.fechaHoraFin);
  //   resultados += "   (" + fechaHoraInicio + " - " + fechaHoraFin + ")\n";
  //   for (DetalleMuestraSismica detalle : detalleMuestra) {
  //     resultados += "\t";
  //     if (detalle.sosVelocidadFrecuenciaLogitud()) {
  //       resultados += detalle.getTipoDato().getNombre() + ": ";
  //       if (detalle.getTipoDato().getNombre().equals("Velocidad de onda")) {
  //         resultados += String.format("%.5f", detalle.getVelocidadOnda()) + " m/s, ";
  //       } else if (detalle.getTipoDato().getNombre().equals("Frecuencia de onda")) {
  //         resultados += String.format("%.5f", detalle.getFrecuenciaOnda()) + " Hz, ";
  //       } else if (detalle.getTipoDato().getNombre().equals("Longitud")) {
  //         resultados += String.format("%.5f", detalle.getLongitud()) + " m, ";
  //       }
  //       resultados = resultados.substring(0, resultados.length() - 2); // Eliminar la última coma y espacio
  //       resultados += "\n";
  //     }
  //   }
  //   return resultados;
  // }

  public static List<MuestraSismica> mockMuestrasSismicas() {
    List<MuestraSismica> muestras = new ArrayList<>();
    muestras.add(new MuestraSismica(LocalDateTime.now(), LocalDateTime.now().plusHours(1)));
    muestras.add(new MuestraSismica(LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1)));
    return muestras;
  }
}

class DetalleMuestraSismica {
  private double velocidadOnda;
  private double frecuenciaOnda;
  private double longitud;
  private TipoDato tipoDato;

  public DetalleMuestraSismica(double magnitud, TipoDato tipoDato) {
    this.tipoDato = tipoDato;
    
    // Asignar el valor según el tipo de dato
    if (tipoDato.getNombre().equals("Velocidad de onda")) {
      this.velocidadOnda = magnitud;
    } else if (tipoDato.getNombre().equals("Frecuencia de onda")) {
      this.frecuenciaOnda = magnitud;
    } else if (tipoDato.getNombre().equals("Longitud")) {
      this.longitud = magnitud;
    }
  }

  public boolean sosVelocidadLongitudFrecuencia() {
    return this.tipoDato.sosVelocidadFrecuenciaLogitud();
  }

  public double getVelocidadOnda() {
    return velocidadOnda;
  }

  public double getFrecuenciaOnda() {
    return frecuenciaOnda;
  }

  public double getLongitud() {
    return longitud;
  }

  public TipoDato getTipoDato() {
    return tipoDato;
  }

  public boolean sosVelocidadFrecuenciaLogitud() {
    return this.tipoDato.sosVelocidadFrecuenciaLogitud();
  }

  // Método mock para crear datos de prueba
  public static List<DetalleMuestraSismica> mockDetalleMuestraSismica() {
    List<DetalleMuestraSismica> muestras = new ArrayList<>();
    java.util.Random rand = new java.util.Random();
    muestras.add(new DetalleMuestraSismica(5.0 + rand.nextDouble() * 5.0, new TipoDato("Velocidad de onda")));
    muestras.add(new DetalleMuestraSismica(8.0 + rand.nextDouble() * 4.0, new TipoDato("Frecuencia de onda")));
    muestras.add(new DetalleMuestraSismica(0.5 + rand.nextDouble() * 1.0, new TipoDato("Longitud")));
    return muestras;
  }

  public HashMap<String, String> buscarVelocidadLongitudFrecuencia() {
    HashMap<String, String> datos = new HashMap<>();
    if (sosVelocidadFrecuenciaLogitud()) {
      datos.put("Velocidad de onda", String.valueOf(velocidadOnda));
      datos.put("Frecuencia de onda", String.valueOf(frecuenciaOnda));
      datos.put("Longitud", String.valueOf(longitud));
    }
    return datos;
  }
}

class TipoDato {
  private static final String VELOCIDAD = "Velocidad de onda";
  private static final String FRECUENCIA = "Frecuencia de onda";
  private static final String LONGITUD = "Longitud";
  private String nombre;

  public TipoDato(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

  public boolean sosVelocidadFrecuenciaLogitud() {
    return VELOCIDAD.equals(nombre) || FRECUENCIA.equals(nombre) || LONGITUD.equals(nombre);
  }
}