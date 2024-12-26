package Model;

public abstract class JugadorBase implements Jugador {
    protected Caballo caballoElegido;  // Caballo escogido por el jugador

    @Override
    public void hacerApuesta(Caballo caballo, int fichas){
        this.caballoElegido = caballo; // Establece el caballo escogido por el jugador
    }
    @Override
    public abstract void anunciarGanador(Caballo caballo);

}
