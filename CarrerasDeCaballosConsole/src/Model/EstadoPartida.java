package Model;

import java.io.Serializable;
import java.util.List;

public class EstadoPartida {
    private List<Jugador> jugadores;
    private int turno;
    private String estado; // Puede ser "EN CURSO", "PAUSADO", etc.

    public EstadoPartida(List<Jugador> jugadores, int turno, String estado) {
        this.jugadores = jugadores;
        this.turno = turno;
        this.estado = estado;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getTurno() {
        return turno;
    }

    public String getEstado() {
        return estado;
    }
}

