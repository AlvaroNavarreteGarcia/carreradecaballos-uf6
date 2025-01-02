package Model;

public interface Jugador {
    void hacerApuesta(Caballo caballo, int fichas);
    void anunciarGanador(Caballo caballo);
    void incrementarFichas(int cantidad);
    void elegirCaballo(Caballo caballo);
    void decrementarFichas(int cantidad);
    int getFichas();
    String getNombre();
    Caballo getCaballoElegido();
    int getId();

}

