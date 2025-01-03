import Model.*;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;



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
    private Connection connection;

    private static final String url = "jdbc:mysql://localhost:3306/carrera_de_caballos";
    private static final String username = "root";
    private static final String password = "";


    public CarreraCaballos() {
        try {
            // Aquí debes realizar la conexión a la base de datos, usando JDBC
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrera_de_caballos", "root", "");
            System.out.println("Conexión exitosa a la base de datos");
            scanner = new Scanner(System.in);

            // Recoger valores dinámicos
            System.out.println("Introduce el nombre del jugador:");
            String nombreGanador = scanner.nextLine();

            System.out.println("Introduce el nombre del caballo:");
            String nombreCaballo = scanner.nextLine();

            System.out.println("Selecciona el palo del caballo (1. OROS, 2. COPAS, 3. ESPADAS, 4. BASTOS):");
            int opcionTipoCarta = Integer.parseInt(scanner.nextLine()); // Índice para el TipoCarta
            TipoCarta tipoCarta = obtenerTipoCartaPorIndice(opcionTipoCarta); // Convertir índice a TipoCarta

            System.out.println("Introduce las fichas iniciales del ganador:");
            int fichasIniciales = Integer.parseInt(scanner.nextLine());


            System.out.println("Introduce el valor del bote:");
            int bote = Integer.parseInt(scanner.nextLine());

            JugadorHumano ganador = new JugadorHumano(nombreGanador, fichasIniciales);
            Caballo caballoGanador = new Caballo(nombreCaballo, tipoCarta);

            insertarPartidaInicial(ganador, caballoGanador, bote);
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime cualquier error de conexion
        } catch (NumberFormatException e) {
            System.out.println("El valor del bote debe ser un número válido.");
        }

        this.scanner = new Scanner(System.in);
        System.out.println("Hola, introduce tu nombre:");
        inicializarJuego(scanner.nextLine());
    }

    public static void main(String[] args) {
        // Crear jugadores y el estado de la partida
        List<Jugador> jugadores = Arrays.asList(
                new JugadorHumano("Jugador 1", 50),
                new JugadorBot("Bot 1", 50),
                new JugadorBot("Bot 2", 50),
                new JugadorBot("Bot 3", 50)
        );
        EstadoPartida estado = new EstadoPartida(jugadores, 1, "EN CURSO");

        // Guardamos el estado al inicio
        ManejoBaseDatos.guardarEstado(estado);

        // Aquí podrías, por ejemplo, simular la partida (empezando turnos)

        // Al cargar el estado
        EstadoPartida estadoCargado = ManejoBaseDatos.cargarEstado();
        if (estadoCargado != null) {
            System.out.println("Partida cargada con " + estadoCargado.getJugadores().size() + " jugadores, turno: " + estadoCargado.getTurno());
        } else {
            System.out.println("No hay partida guardada.");
        }
    }
    private TipoCarta obtenerTipoCartaPorIndice(int indice) {
        switch (indice) {
            case 1:
                return TipoCarta.OROS;
            case 2:
                return TipoCarta.COPAS;
            case 3:
                return TipoCarta.ESPADAS;
            case 4:
                return TipoCarta.BASTOS;
            default:
                throw new IllegalArgumentException("Índice de tipo de carta inválido: " + indice);
        }
    }

    private void insertarPartidaInicial(Jugador ganador, Caballo caballoGanador, int bote) {
        String query = "INSERT INTO Partidas (ganador, caballo_ganador, bote) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrera_de_caballos", "root", "");
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, ganador.getNombre()); // Nombre del ganador
            stmt.setString(2, caballoGanador.getNombre()); // Nombre del caballo ganador
            stmt.setInt(3, bote); // Valor del bote

            stmt.executeUpdate(); // Ejecutar la inserción
            System.out.println("Partida inicial insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); // Si ocurre un error, mostrarlo
        }
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
            actualizarEstadoPartidaEnBD(ganador, caballoGanador);
            reiniciarJuego();
        }
    }
    // Método para actualizar el estado de la partida en la base de datos
    private void actualizarEstadoPartidaEnBD(Jugador ganador, Caballo caballoGanador) {
        try {
            // Aquí crearás el SQL para guardar el ganador, por ejemplo:
            String sql = "UPDATE partidas SET ganador = ?, caballo_ganador = ?, bote = ? WHERE id_partida = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            // Suponemos que tienes el id de la partida
            int idPartida = 1;  // Pueden usar algo que te indique la partida actual

            ps.setString(1, ganador.getNombre());
            ps.setString(2, caballoGanador.getNombre());
            ps.setInt(3, bote);
            ps.setInt(4, idPartida);

            ps.executeUpdate();
            System.out.println("Estado de la partida actualizado en la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
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
        while (true) {
            try {
                System.out.println(jugador.getNombre() + ", ¿cuántas fichas quieres apostar?");
                int apuesta = Integer.parseInt(scanner.nextLine().trim());
                if (apuesta > 0) {
                    return apuesta;
                } else {
                    System.out.println("La apuesta debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, introduce un número.");
            }
        }
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

        ganador.incrementarFichas(bote); // Suponiendo que los jugadores tienen un atributo para fichas
        System.out.println("¡El ganador es " + ganador.getNombre() + "! Se lleva un bote de " + bote + " fichas.");
    }
    /*private void reiniciarJuego() {
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
    }*/
    private void reiniciarJuego() {
        for (Caballo caballo : caballos) {
            caballo.setPosicion(0); // Reinicia las posiciones de los caballos
        }
        this.bote = 0;
        this.retrocederProximo = false;
        this.contadorRondas = 0;
        this.caballosEscogidos.clear();
        System.out.println("El juego ha sido reiniciado. ¡Listo para otra partida!");
    }

}