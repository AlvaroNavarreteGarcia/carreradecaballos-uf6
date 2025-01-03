package com.baraja_cartas_gui.Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {

    private final List<Carta> baraja;
    private int indiceCartaActual;

    public Baraja() {
        baraja = new ArrayList<>();
        Palo[] palos = {Palo.OROS, Palo.BASTOS, Palo.COPAS, Palo.ESPADAS};
        int[] numeros = {1, 2, 3, 4, 5, 6, 7};
        Figura[] figuras = {Figura.SOTA, Figura.REY}; // Excluimos "Caballo"

        // Crear las cartas numeradas
        for (int numero : numeros) {
            for (Palo palo : palos) {
                baraja.add(new NumeroCarta(numero, palo));
            }
        }

        // Crear las cartas de figura (excluyendo Caballos)
        for (Figura figura : figuras) {
            for (Palo palo : palos) {
                baraja.add(new PaloCarta(figura, palo));
            }
        }

        // Mezclar la baraja
        Collections.shuffle(baraja);
        indiceCartaActual = 0;
    }

    /**
     * Obtiene una carta de la baraja. Si no quedan cartas, lanza una excepción personalizada.
     * También registra la carta sacada en un archivo.
     *
     * @return la próxima carta de la baraja.
     * @throws BarajaVaciaException si no quedan cartas en la baraja.
     */
    public Carta obtenerCartaDeLaBaraja() throws BarajaVaciaException {
        if (indiceCartaActual >= baraja.size()) {
            throw new BarajaVaciaException("No quedan más cartas en la baraja.");
        }

        Carta carta = baraja.get(indiceCartaActual);
        indiceCartaActual++;

        registrarCartaEnArchivo(carta);
        return carta;
    }

    /**
     * Registra la carta sacada en un archivo de texto.
     *
     * @param carta la carta a registrar.
     */
    private void registrarCartaEnArchivo(Carta carta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cartas_sacadas.txt", true))) {
            writer.write(carta.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al registrar la carta en el archivo: " + e.getMessage());
        }
    }

    /**
     * Metodo para reiniciar la baraja (volver a mezclar y reiniciar índice).
     */
    public void reiniciarBaraja() {
        Collections.shuffle(baraja);
        indiceCartaActual = 0;
    }

    /**
     * Obtiene el número de cartas restantes en la baraja.
     *
     * @return el número de cartas restantes.
     */
    public int cartasRestantes() {
        return baraja.size() - indiceCartaActual;
    }
}

