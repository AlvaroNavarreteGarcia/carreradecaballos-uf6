package com.baraja_cartas_gui.Model;


public abstract class Carta {
    protected Palo palo;
//    protected float valor;
//    public float getValor() {
//        return valor;
//    }

    protected String toString(String numeroOFigura) {

        String articulo = "la";
        if (numeroOFigura == "SOTA") {
            articulo = "la";
        } else {
            articulo = "el";
        }
       return String.format("El crupier ha sacado " + articulo + " " + numeroOFigura + " de " + palo);
    }

    public abstract String obtenerCodigoCarta();

    public Palo getPalo() {
        return palo;
    }
}

