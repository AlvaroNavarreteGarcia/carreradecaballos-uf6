package Model;

public interface Jugador {
    // Método para que el jugador realice una apuesta por un caballo específico
    void hacerApuesta(Caballo caballo, int fichas);
    // Método para anunciar al jugador que un caballo ha ganado la carrera
    void anunciarGanador(Caballo caballo);
    // Método para que el jugador reciba el bote
    void recibirBote(int fichas);
    // Método para obtener el nombre del jugador
    String getNombre();
}

