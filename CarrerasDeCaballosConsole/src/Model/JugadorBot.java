package Model;

import java.util.List;  // Para usar listas de caballos (si es necesario para tu proyecto)

public class JugadorBot extends JugadorBase {

    // Constructor para el Bot
    public JugadorBot(String nombre, int fichasIniciales) {
        super(nombre, fichasIniciales);  // Llama al constructor de la clase base JugadorBase
    }

    @Override
    public int getId() {
        // Puedes retornar un identificador específico para el bot
        return 0; // Ejemplo, define un ID o implementa una lógica personalizada
    }
    @Override
    public void hacerApuesta(Caballo caballo, int fichas) {
        if (fichas <= getFichas()) {
            super.hacerApuesta(caballo, fichas);  // Usa la implementación de la clase base
            System.out.println(nombre + " ha apostado " + fichas + " fichas al caballo " + caballo.getNombre());
        } else {
            System.out.println(nombre + " no tiene suficientes fichas para realizar la apuesta.");
        }
    }

    @Override
    public void anunciarGanador(Caballo caballo) {
        System.out.println("El bot " + nombre + " anuncia que el ganador es el caballo " + caballo.getNombre() + ".");
    }

    @Override
    public void decrementarFichas(int cantidad) {
        super.decrementarFichas(cantidad);  // Llama a la implementación de la clase base
        System.out.println(nombre + " ha perdido " + cantidad + " fichas.");
    }

    @Override
    public void incrementarFichas(int cantidad) {
        super.incrementarFichas(cantidad);  // Llama a la implementación de la clase base
        System.out.println(nombre + " ha ganado " + cantidad + " fichas.");
    }

    @Override
    public void elegirCaballo(Caballo caballo) {
        // Logica del bot para elegir un caballo (por ejemplo, aleatoriamente o de alguna manera inteligente)
        System.out.println("El bot " + nombre + " ha elegido al caballo " + caballo.getNombre());
        this.caballoElegido = caballo;  // Guarda el caballo elegido (por ejemplo, para usar en la apuesta)
    }
}


