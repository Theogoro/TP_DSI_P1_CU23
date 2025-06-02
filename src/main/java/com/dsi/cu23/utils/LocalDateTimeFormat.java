package com.dsi.cu23.utils;

public class LocalDateTimeFormat {
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static String format(java.time.LocalDateTime dateTime) {
    return dateTime.format(java.time.format.DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
  }
}
