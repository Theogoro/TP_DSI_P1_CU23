package com.dsi.cu23.interfaces;

import java.util.List;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dsi.cu23.models.EstacionSismologica;
import com.dsi.cu23.models.SerieTemporal;

public class InterfazGenerarSismograma {
  public BufferedImage generarSismogramaXEstacion(EstacionSismologica estacion, List<SerieTemporal> series) {
    int width = 800;
    int height = 200;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = image.createGraphics();

    // Dibujar fondo cuadriculado
    g2d.setColor(new Color(240, 220, 180)); // fondo estilo papel
    g2d.fillRect(0, 0, width, height);

    g2d.setColor(new Color(200, 170, 130)); // líneas finas
    for (int x = 0; x < width; x += 20) {
      g2d.drawLine(x, 0, x, height);
    }
    for (int y = 0; y < height; y += 20) {
      g2d.drawLine(0, y, width, y);
    }

    // Línea base
    int baseY = height / 2;
    g2d.setColor(Color.BLACK);
    g2d.drawLine(0, baseY, width, baseY);

    // Dibujar "sismo" con patrón aleatorio
    g2d.setColor(Color.BLACK);
    Random rand = new Random();
    int[] signal = new int[width];

    for (int x = 0; x < width; x++) {
      double factor = 0.0;

      // Zona de mayor actividad en el centro
      if (x > width * 0.3 && x < width * 0.7) {
        factor = 1.0;
      } else if (x > width * 0.2 && x < width * 0.8) {
        factor = 0.5;
      } else {
        factor = 0.1;
      }

      // Simular valores con picos, senoidal + ruido
      double noise = rand.nextGaussian() * 20 * factor;
      double wave = Math.sin(x * 0.05) * 15 * factor;
      signal[x] = (int) (wave + noise);
    }

    for (int x = 0; x < width - 1; x++) {
      int y1 = baseY + signal[x];
      int y2 = baseY + signal[x + 1];
      g2d.drawLine(x, y1, x + 1, y2);
    }

    g2d.dispose();
    return image;
  }

}
