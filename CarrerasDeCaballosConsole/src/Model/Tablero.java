package Model;

public class Tablero {
    private static final int MAX_POSICIONES = 10;

    private Caballo[] caballos;

    public Tablero(Caballo[] caballos) {
        this.caballos = caballos;
    }
    // Método que actualiza el tablero a medida que pasa cada ronda

    public void actualizarTablero() {
        System.out.println("\n--- Estado de la carrera ---");
        int longitudFila = MAX_POSICIONES * 2 + 2; // Longitud total de cada fila en el tablero (contando "|")
        int posicionUltimoCaballo = MAX_POSICIONES;
        for (Caballo caballo : caballos) {
            StringBuilder row = new StringBuilder(String.format("%-" + (longitudFila - 2) + "s", caballo.getNombre() + ": "));

            int posicionCaballo = caballo.getPosicion();
            // Bucle que indicará si poner "X" o "-" dependiendo de la posición del caballo
            for (int i = 0; i < MAX_POSICIONES; i++) {
                if (i == posicionCaballo) {
                    row.append("X ");
                } else {
                    row.append("- ");
                }
            }
            if (posicionUltimoCaballo > posicionCaballo)
                posicionUltimoCaballo = posicionCaballo;

            System.out.println(row.toString() + "|");
        }
    }
}