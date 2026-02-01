package com.mycompany.grupoi.proyectogrupoi;

import com.mycompany.grupoi.proyectogrupoi.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @Test
    void parseDateValidaFormato() {
        LocalDate d = ValidationUtil.parseDate("2026-01-31");
        assertEquals(2026, d.getYear());
        assertEquals(1, d.getMonthValue());
        assertEquals(31, d.getDayOfMonth());
    }

    @Test
    void requireNotBlankLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> ValidationUtil.requireNotBlank("   ", "Campo"));
    }
}
