package Model;

public class Caballo {
    private String nombre;
    private TipoCarta tipoCarta;
    private int posicion;
    private int[] posiciones;
    public Caballo(String nombre, TipoCarta tipoCarta) {
        this.nombre = nombre;
        this.tipoCarta = tipoCarta;
        this.posiciones = new int[10]; // historial donde guarda las posiciones
        this.posicion = 0; // inicializa la posición del caballo a 0
    }
    // Obtener el nombre del caballo
    public String getNombre() {
        return nombre;
    }
    // Obtener el tipo de carta asociado al caballo
    public TipoCarta getTipoCarta() {
        return tipoCarta;
    }

    // Obtener la posición actual del caballo en el tablero
    public int getPosicion() {
        return posicion;
    }
    // Establece la posición del caballo en el tablero
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    // EL caballo avanza una posición
    public void avanzar() {
        if (posicion < 9) {
            posicion++;
        }
    }
    // El caballo retrocede una posición
    public void retroceder() {
        if (posicion > 0) {
            posicion--;
        }
    }
}