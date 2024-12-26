import Model.*;

import java.util.*;

public class CarreraCaballos {
    private Crupier crupier;
    private Tablero tablero;
    private Caballo[] caballos;
    private Jugador[] jugadores;
    private Scanner scanner;
    private int bote;
    private Set<Caballo> caballosEscogidos; // Controlar los caballos ya escogidos
    private int contadorRondas;
    private boolean retrocederProximo;
    private int retrocesosPermitidos;

    public CarreraCaballos() {
        this.scanner = new Scanner(System.in);
        System.out.println("Hola, introduce tu nombre:");
        inicializarJuego(scanner.nextLine());
    }

    private void inicializarJuego(String nombreJugador) {


        this.caballos = new Caballo[]{
                new Caballo("Caballo de Bastos", TipoCarta.BASTOS),
                new Caballo("Caballo de Oros", TipoCarta.OROS),
                new Caballo("Caballo de Espadas", TipoCarta.ESPADAS),
                new Caballo("Caballo de Copas", TipoCarta.COPAS)
        };

        this.jugadores = new Jugador[]{
                new JugadorCarreraCaballos(nombreJugador),
                new JugadorCarreraCaballos("Bot1"),
                new JugadorCarreraCaballos("Bot2"),
                new JugadorCarreraCaballos("Bot3")
        };

        this.crupier = new Crupier();
        this.tablero = new Tablero(caballos);
        this.caballosEscogidos = new HashSet<>();
        this.contadorRondas = 0;
        this.retrocederProximo = false;
        this.retrocesosPermitidos = 6;
    }

    public void iniciarJuego() {
        boolean jugar = true;

        while (jugar) {
            mostrarMenu();

            try {
                String input = scanner.nextLine();
                int opcion = Integer.parseInt(input.trim());

                switch (opcion) {
                    case 1:
                        jugarPartida();
                        break;
                    case 2:
                        System.out.println("Adios...");
                        jugar = false;
                        break;
                    default:
                        System.out.println("Error, debe escoger una opción");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, debe escoger una opción");
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Jugar una partida");
        System.out.println("2. Salir del juego");
        System.out.print("Seleccione una opción: ");
    }

    private void jugarPartida() {
        System.out.println("\n--- Iniciando nueva partida ---");


        this.bote = 0; // Inicia el bote desde 0
        this.caballosEscogidos.clear(); // Limpiar el conjunto de caballos escogidos
        Jugador[] jugadoresApostadores = new Jugador[caballos.length]; // Array para guardar el jugador que apostó por cada caballo
        Jugador jugadorPrincipal = jugadores[0];
        Caballo caballoElegido = elegirCaballo();
        int fichas = realizarApuesta(jugadorPrincipal);
        jugadorPrincipal.hacerApuesta(caballoElegido, fichas);
        hacerBote(fichas);
        int indiceCaballo = getCaballoIndex(caballoElegido);
        if (indiceCaballo != -1) {
            jugadoresApostadores[indiceCaballo] = jugadorPrincipal;
        }
        realizarRepartoCaballosBots(indiceCaballo,jugadoresApostadores);

        while (!hayGanador()) {
            jugarRonda();
            tablero.actualizarTablero();
        }

        Caballo caballoGanador = getCaballoGanador();
        int indiceCaballoGanador = getCaballoIndex(caballoGanador);
        if (indiceCaballoGanador != -1 && jugadoresApostadores[indiceCaballoGanador] != null) {
            Jugador ganador = jugadoresApostadores[indiceCaballoGanador];
            ganador.anunciarGanador(caballoGanador);
            repartirBote(ganador, bote); // Dar el bote al ganador de la carrera
            reiniciarJuego();
        }
    }

    private void realizarRepartoCaballosBots(int indiceCaballoJugdorPrincipal, Jugador[] jugadoresApostadores) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < caballos.length; i++) {
            indices.add(i);
        }
        indices.remove(Integer.valueOf(indiceCaballoJugdorPrincipal));
        Random random = new Random();
        for (int i = 1; i < jugadores.length; i++) {
            int indice = indices.get(i-1);
            Caballo caballoElegido = caballos[indice];
            int fichas = random.nextInt(25) + 1;
            jugadores[i].hacerApuesta(caballoElegido, fichas);
            hacerBote(fichas);
            int indiceCaballo = getCaballoIndex(caballoElegido);
            if (indiceCaballo != -1) {
                jugadoresApostadores[indiceCaballo] = jugadores[i];
            }
        }

    }

    // El jugador elige el caballo disponible
    private Caballo elegirCaballo() {
        System.out.println("\n--- Escoge un caballo ---");

        for (int i = 0; i < caballos.length; i++) {
            System.out.println((i + 1) + ". " + caballos[i].getNombre());
        }

        while (true) {
            System.out.print("Ingrese el número situado a la izquierda del caballo que quiera: ");
            String input = scanner.nextLine();
            int opcion = Integer.parseInt(input.trim());

            if (opcion < 1 || opcion > caballos.length) {
                System.out.println("Opción no válida. Inténtelo de nuevo.");
                continue;
            }

            Caballo caballoSeleccionado = caballos[opcion - 1];
            caballosEscogidos.add(caballoSeleccionado);
            return caballoSeleccionado;

        }
    }

    private int realizarApuesta(Jugador jugador) {
        System.out.println(jugador.getNombre() + ", ¿cuantas fichas quieres apostar?:");
        return scanner.nextInt();
    }
    // Incrementar el bote con las fichas apostadas
    private void hacerBote(int fichas) {
        this.bote += fichas;
    }
    // Método que simula la ronda
    private void jugarRonda() {
        Crupier crupier = this.crupier;

        scanner.nextLine();
        Carta carta = crupier.sacarCarta();

        if (carta == null) {
            System.out.println("Se ha acabado la baraja. Barajando de nuevo...");
            crupier.getBaraja().barajar();
            carta = crupier.sacarCarta();
        }

        if (carta != null) {
            System.out.println("El crupier ha sacado la carta: " + carta);
            if (retrocederProximo && retrocesosPermitidos > 0) {
                for (Caballo caballo : caballos) {
                    if (caballo.getTipoCarta() == carta.getTipoCarta()) {
                        caballo.retroceder();
                        System.out.println(caballo.getNombre() + " retrocede una posicion");
                        retrocesosPermitidos--; // Disminuir el contador de retrocesos permitidos
                        break; // Solo retrocede un caballo por carta
                    }
                }
                retrocederProximo = false; // Restablecer para la próxima ronda
            } else {
                // Avanzar los caballos que coinciden con la carta
                for (Caballo caballo : caballos) {
                    if (caballo.getTipoCarta() == carta.getTipoCarta()) {
                        caballo.avanzar();
                        System.out.println(caballo.getNombre() + " avanza una posicion");
                    }
                }
                // Incrementar el contador de rondas
                contadorRondas++;
                // Si es la cuarta ronda, preparar para retroceder en la próxima ronda
                if (contadorRondas % 4 == 0) {
                    retrocederProximo = true;
                }
            }
        }
    }
    // Método para verificar si algún caballo a llegado a la meta
    private boolean hayGanador() {
        for (Caballo caballo : caballos) {
            if (caballo.getPosicion() >= 9) {
                return true; // Hay ganador
            }
        }
        return false; // No hay ganador
    }

    // Obtener el índice del caballo en el array de caballos
    private int getCaballoIndex(Caballo caballo) {
        for (int i = 0; i < caballos.length; i++) {
            if (caballos[i] == caballo) {
                return i;
            }
        }
        return -1;
    }

    //Obtener el caballo que ha llegado primero a la meta
    private Caballo getCaballoGanador() {
        for (Caballo caballo : caballos) {
            if (caballo.getPosicion() >= 9) {
                return caballo;
            }
        }
        return null;
    }
    // Entrega el bote al ganador
    private void repartirBote(Jugador ganador, int bote) {
        ganador.recibirBote(bote);
    }
    private void reiniciarJuego() {
        // Reiniciar posiciones de los caballos
        for (Caballo caballo : caballos) {
            caballo.setPosicion(0); // Reiniciar posición a 0
        }
        // reinicia el bote a 0
        bote = 0;
        // Limpia los caballos escogidos
        caballosEscogidos.clear();
        // Reinicia el contador de rondas, retrocederProximo a false y retrocesosPermitidos a 6
        contadorRondas = 0;
        retrocederProximo = false;
        retrocesosPermitidos = 6;
    }
}