package com.baraja_cartas_gui.Model;

public class Caballo {

    private Palo palo;
    private int posicion;
    private Double[] posiciones;

    public Caballo(Palo palo) {
        this.palo = palo;
        this.posicion = 0;
        this.posiciones = new Double[]{-302.0, -190.0, -78.0, 34.0, 146.0, 258.0, 370.0};
    }

    // Establece la posición del caballo en el tablero
    public void setPosicion(int posicion) {
        if (posicion >= 0 && posicion < posiciones.length) {
            this.posicion = posicion;
        }
    }

    // Avanza una posición si no está en el límite
    public void avanzar() {
        if (posicion < posiciones.length - 1) {
            posicion++;
        }
    }

    // Retrocede una posición si no está en el límite
    public void retroceder() {
        if (posicion > 0) {
            posicion--;
        }
    }

    // Devuelve la posición en píxeles para la interfaz gráfica
    public Double getPosicion() {
        return posiciones[posicion];
    }

    public Palo getPalo() {
        return palo;
    }
}
