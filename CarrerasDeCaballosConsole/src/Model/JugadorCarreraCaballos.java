package Model;


public class JugadorCarreraCaballos extends JugadorBase {
    private Caballo caballoElegido;
    private String nombre;

    public JugadorCarreraCaballos(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void hacerApuesta(Caballo caballo, int fichas) {
        this.caballoElegido = caballo;
        System.out.println(nombre + " ha apostado en " + caballo.getNombre() + " con " + fichas + " fichas.");
    }

    @Override
    public void anunciarGanador(Caballo caballo) {
        System.out.println("El " + caballo.getNombre() + " ha ganado la carrera ");
    }

    @Override
    public void recibirBote(int bote) {
        System.out.println(nombre + " ha ganado el bote de " + bote + " fichas");
    }

    public String getNombre() {
        return nombre;
    }

}