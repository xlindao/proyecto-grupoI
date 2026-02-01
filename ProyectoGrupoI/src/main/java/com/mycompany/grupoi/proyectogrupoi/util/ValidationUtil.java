package com.mycompany.grupoi.proyectogrupoi.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class ValidationUtil {

    public static void requireNotBlank(String value, String fieldName) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(fieldName + " no puede estar vacío.");
        }
    }

    public static LocalDate parseDate(String input) {
        requireNotBlank(input, "Fecha");
        return LocalDate.parse(input); // formato: YYYY-MM-DD
    }
}
