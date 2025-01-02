package Model;


public class JugadorCarreraCaballos implements Jugador {

    private String nombre; // Nombre del jugador
    private int fichas; // Fichas disponibles del jugador
    private Caballo caballoElegido; // Caballo elegido para la apuesta

    @Override
    public int getId() {
        // Devuelve un valor único para este jugador
        return 0; // Cambia este valor si necesitas algo más dinámico
    }

    // Constructor
    public JugadorCarreraCaballos(String nombre, int fichasIniciales) {
        this.nombre = nombre;
        this.fichas = fichasIniciales;
    }
    // Constructor con un solo parámetro (nombre), asigna un valor predeterminado para las fichas
    public JugadorCarreraCaballos(String nombre) {
        this(nombre, 100); // Valor predeterminado: 100 fichas
    }

    // Implementación de hacerApuesta
    @Override
    public void hacerApuesta(Caballo caballo, int fichas) {
        if (fichas > 0 && fichas <= this.fichas) {
            decrementarFichas(fichas);
            this.caballoElegido = caballo;
            System.out.println(nombre + " ha apostado " + fichas + " fichas por el caballo: " + caballo.getNombre());
        } else {
            System.out.println("Apuesta inválida. Verifica tus fichas disponibles.");
        }
    }

    // Implementación de anunciarGanador
    @Override
    public void anunciarGanador(Caballo caballo) {
        if (caballo.equals(caballoElegido)) {
            System.out.println("¡Felicidades, " + nombre + "! Tu caballo " + caballo.getNombre() + " ha ganado.");
        } else {
            System.out.println("Lo siento, " + nombre + ". Tu caballo " + caballoElegido.getNombre() + " no ganó.");
        }
    }

    // Implementación de incrementarFichas
    @Override
    public void incrementarFichas(int cantidad) {
        if (cantidad > 0) {
            fichas += cantidad;
            System.out.println(nombre + " ganó " + cantidad + " fichas. Ahora tiene: " + fichas);
        }
    }

    // Implementación de decrementarFichas
    @Override
    public void decrementarFichas(int cantidad) {
        if (cantidad > 0 && cantidad <= fichas) {
            fichas -= cantidad;
            System.out.println(nombre + " pierde " + cantidad + " fichas. Le quedan: " + fichas);
        } else {
            System.out.println("Operación inválida. Fichas insuficientes.");
        }
    }

    // Implementación de elegirCaballo
    @Override
    public void elegirCaballo(Caballo caballo) {
        this.caballoElegido = caballo;
        System.out.println(nombre + " ha elegido al caballo: " + caballo.getNombre());
    }

    // Implementación de getFichas
    @Override
    public int getFichas() {
        return fichas;
    }

    // Implementación de getNombre
    @Override
    public String getNombre() {
        return nombre;
    }

    // Implementación de getCaballoElegido
    @Override
    public Caballo getCaballoElegido() {
        return caballoElegido;
    }
}
