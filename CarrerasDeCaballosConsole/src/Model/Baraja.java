package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {
    private List<Carta> cartas;
    private int indice;

    public Baraja() {
        this.cartas = new ArrayList<>(); // Inicializa un ArrayList vació para guardar las cartas
        this.indice = 0; // Inicializa el índice en 0

        // Añade 10 cartas de cada tipo dentro de la baraja
        for (TipoCarta tipo : TipoCarta.values()) {
            for (int i = 1; i <= 10; i++) {
                cartas.add(new Carta(tipo, i)); // Crea una nueva carta con su tipo y numero, y lo añade a la lista
            }
        }

        barajar();
    }
    // Baraja las cartas de forma aleatoria con el Collections.shuffle
    public void barajar() {
        Collections.shuffle(cartas);
        indice = 0;
    }
    // Sacar la próxima carta de la baraja
    public Carta sacarCarta() {
        if (indice < cartas.size()) {
            return cartas.get(indice++);
        }
        return null;
    }
}