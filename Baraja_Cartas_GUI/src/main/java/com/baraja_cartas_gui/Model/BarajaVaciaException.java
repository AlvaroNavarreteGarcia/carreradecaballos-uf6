package com.baraja_cartas_gui.Model;

/**
 * Excepción personalizada para indicar que la baraja está vacía.
 */
public class BarajaVaciaException extends RuntimeException {
    public BarajaVaciaException(String mensaje) {
        super(mensaje);
    }
}
