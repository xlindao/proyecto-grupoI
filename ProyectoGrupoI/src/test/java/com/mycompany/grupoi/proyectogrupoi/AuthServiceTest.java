package com.mycompany.grupoi.proyectogrupoi;

import com.mycompany.grupoi.proyectogrupoi.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    void sha256GeneraHashConsistente() {
        String h1 = PasswordUtil.sha256("1234");
        String h2 = PasswordUtil.sha256("1234");
        assertEquals(h1, h2);
        assertNotEquals(h1, PasswordUtil.sha256("12345"));
    }

    @Test
    void hashNoEsTextoPlano() {
        String h = PasswordUtil.sha256("password");
        assertFalse(h.contains("password"));
        assertEquals(64, h.length());
    }
}
