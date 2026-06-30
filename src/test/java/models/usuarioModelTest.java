package models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class usuarioModelTest {

    @Test
    public void testSetAndGetEmail() {
        usuarioModel usuario = new usuarioModel();
        usuario.setEmail("test@example.com");
        assertEquals("test@example.com", usuario.getEmail());
    }

    @Test
    public void testSetAndGetRol() {
        usuarioModel usuario = new usuarioModel();
        usuario.setRol("ADMIN");
        assertEquals("ADMIN", usuario.getRol());
    }
}
