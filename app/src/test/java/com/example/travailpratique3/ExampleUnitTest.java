package com.example.travailpratique3;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleUnitTest {

    // Partie 1 : Tester la création d’un utilisateur avec la classe Utilisateur
    @Test
    public void createUser_isCorrect() {
        Utilisateur user = new Utilisateur("Florentin Toupet", "ft123456@example.com", "1234567");
        assertNotNull(user);
        assertEquals("Florentin Toupet", user.getUsername());
        assertEquals("ft123456@example.com", user.getEmail());
        assertEquals("1234567", user.getPassword());
    }

}
