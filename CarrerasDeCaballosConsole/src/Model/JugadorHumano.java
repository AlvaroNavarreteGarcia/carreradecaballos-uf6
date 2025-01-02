package Model;

public class JugadorHumano extends JugadorBase {
    public JugadorHumano(String nombre, int fichasIniciales) {
        super(nombre, fichasIniciales); // Hereda atributos de JugadorBase
    }

    @Override
    public void hacerApuesta(Caballo caballo, int fichas) {
        super.hacerApuesta(caballo, fichas); // Usa la lógica común
        System.out.println(nombre + " ha hecho una apuesta como humano.");
    }

    @Override
    public void anunciarGanador(Caballo caballo) {
        // Lógica para anunciar al ganador para un jugador humano
        System.out.println("El humano " + nombre + " dice: ¡El caballo " + caballo.getNombre() + " ha ganado!");
    }

    @Override
    public void elegirCaballo(Caballo caballo) {

    }
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

