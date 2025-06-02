package com.dsi.cu23.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dsi.cu23.records.RecordMuestra;

public class SerieTemporal {
  private LocalDateTime fechaHoraInicio;
  private List<MuestraSismica> muestraSismicas;

  public SerieTemporal(LocalDateTime fechaHoraInicio) {
    this.fechaHoraInicio = fechaHoraInicio;
    this.muestraSismicas = MuestraSismica.mockMuestrasSismicas();
  }

  public LocalDateTime getFechaHoraInicio() {
    return fechaHoraInicio;
  }

  public List<MuestraSismica> getMuestraSismicas() {
    return muestraSismicas;
  }

  public void setMuestraSismicas(List<MuestraSismica> muestraSismicas) {
    this.muestraSismicas = muestraSismicas;
  }

  public RecordMuestra[] buscarVelocidadLongitudFrecuencia() {
    RecordMuestra[] resultados = new RecordMuestra[muestraSismicas.size()];
    for (int i = 0; i < muestraSismicas.size(); i++) {
      MuestraSismica muestra = muestraSismicas.get(i);
      resultados[i] = muestra.buscarVelocidadLongitudFrecuencia();
    }
    return resultados;
  }

  public SerieTemporal getSerieTemporal() {
    return this;
  }

  public static List<SerieTemporal> mockSeriesTemporales() {
    List<SerieTemporal> series = new ArrayList<>();
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 0)));
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 1)));
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 2)));
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 3)));
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 4)));
    series.add(new SerieTemporal(LocalDateTime.of(2023, 10, 1, 12, 5)));
    return series;
  }

}
