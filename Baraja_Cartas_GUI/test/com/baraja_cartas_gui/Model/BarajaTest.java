package com.baraja_cartas_gui.Model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BarajaTest {

    @Test
    void obtenerCartaDeLaBaraja() {
        // Suponiendo que Baraja tiene 36 cartas inicialmente 40 menos los caballos
        Baraja baraja = new Baraja();
        int totalCartas = 3; // Número total de cartas en la baraja (ajusta según el tipo de baraja)

        Set<Carta> cartasObtenidas = new HashSet<>();

        // Obtener todas las cartas de la baraja
        for (int i = 0; i < totalCartas; i++) {
            Carta carta = baraja.obtenerCartaDeLaBaraja();
            assertNotNull(carta, "La carta no debería ser null"); // Verificar que no sea null
            assertFalse(cartasObtenidas.contains(carta), "Se ha obtenido una carta repetida: " + carta);
            cartasObtenidas.add(carta); // Añadir la carta al Set
        }

        // Verificar que el total de cartas obtenidas coincide con el total esperado
        assertEquals(totalCartas, cartasObtenidas.size(),
                "El número de cartas únicas no coincide con el total esperado");
    }
}