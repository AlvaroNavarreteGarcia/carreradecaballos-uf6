package Model;

// Asegurándote de que los métodos en la interfaz 'Jugador' estén implementados.
public abstract class JugadorBase implements Jugador {

    protected String nombre;
    protected int fichas;
    protected Caballo caballoElegido;

    public JugadorBase(String nombre, int fichasIniciales) {
        this.nombre = nombre;
        this.fichas = fichasIniciales;
    }

    @Override
    public void hacerApuesta(Caballo caballo, int fichas) {
        this.fichas -= fichas;  // Reduces las fichas por la apuesta realizada
    }

    @Override
    public void anunciarGanador(Caballo caballo) {
        System.out.println("El jugador " + nombre + " dice que el ganador es el caballo " + caballo.getNombre());
    }

    @Override
    public void incrementarFichas(int cantidad) {
        this.fichas += cantidad;  // Aumenta las fichas
    }

    @Override
    public void decrementarFichas(int cantidad) {
        this.fichas -= cantidad;  // Decrementa las fichas
    }

    @Override
    public int getFichas() {
        return this.fichas;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }


    @Override
    public Caballo getCaballoElegido() {
        // Esto se puede personalizar según tu lógica
        return null; // Aquí asumo que no necesitas este comportamiento por el momento
    }
}


