package Model;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.util.Arrays;


public class ManejoBaseDatos {

    private static final String URL = "jdbc:mysql://localhost:3306/carrera_de_caballos"; // Cambiar la URL según tu configuración
    private static final String USUARIO = "root"; // Tu usuario de la base de datos
    private static final String CONTRASENA = ""; // Tu contraseña de la base de datos

    public static Connection obtenerConexion() throws SQLException {
        try {
            // Aquí pon tus datos de conexión (URL, USER y PASSWORD)
            String url = "jdbc:mysql://localhost:3306/carrera_de_caballos"; // Ejemplo URL
            String usuario = "root"; // Reemplaza por tu usuario
            String contraseña = ""; // Reemplaza por tu contraseña

            // Establecemos la conexión y la retornamos
            return DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener la conexión a la base de datos");
        }
    }

    // Método para guardar el estado de la partida
    public static void guardarEstado(EstadoPartida estado) {
        try (Connection conn = obtenerConexion()) {
            String sql = "INSERT INTO estado_carrera (jugadores, turno) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, convertirListaAJugadores(estado.getJugadores()));  // Convierte la lista de jugadores a un String, si es necesario
            pstmt.setInt(2, estado.getTurno());
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Estado guardado correctamente.");
            } else {
                System.out.println("No se pudo guardar el estado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String convertirListaAJugadores(List<Jugador> jugadores) {
        Gson gson = new Gson();
        return gson.toJson(jugadores); // Convierte la lista a JSON
    }

    // Método para cargar el estado de la carrera
    public static EstadoPartida cargarEstado() {
        EstadoPartida estado = null;
        try (Connection conn = obtenerConexion()) {
            String sql = "SELECT * FROM estado_carrera ORDER BY id DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String jugadoresJson = rs.getString("jugadores");  // Este es un String en formato JSON

                // Convertir el JSON a lista de Jugadores
                Gson gson = new Gson();
                // Convertimos el JSON (que está en formato de arreglo) a una lista de objetos Jugador
                Jugador[] jugadoresArray = gson.fromJson(jugadoresJson, Jugador[].class);
                List<Jugador> jugadores = Arrays.asList(jugadoresArray);  // Convertimos el arreglo a lista

                int turno = rs.getInt("turno");

                // Crear el estado correctamente
                estado = new EstadoPartida(jugadores, turno, "EN CURSO");  // Creamos el estado con la lista de jugadores, turno y estado

                System.out.println("Partida cargada, jugadores: " + jugadores + ", turno: " + turno);
            } else {
                System.out.println("No hay estado guardado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estado;
    }
    /*public static void verEstadoGuardado() {
        EstadoPartida estado = cargarEstado();  // Método que recupera el último estado de la partida guardado

        if (estado != null) {
            System.out.println("Estado cargado correctamente:");
            System.out.println("Jugadores: " + estado.getJugadores());
            System.out.println("Turno: " + estado.getTurno());
            System.out.println("Estado: " + estado.getEstado());  // Mostrará el estado ("EN CURSO", por ejemplo)
        } else {
            System.out.println("No se pudo cargar el estado guardado.");
        }
    }*/


    // Convierte la lista de jugadores a un formato String (por ejemplo, separado por comas o JSON)
    private static String convertJugadoresAString(List<Jugador> jugadores) {
        StringBuilder sb = new StringBuilder();
        for (Jugador jugador : jugadores) {
            sb.append(jugador.getNombre()).append(",");
        }
        return sb.toString(); // Como ejemplo, usaremos una lista separada por comas
    }

    // Convierte el formato String de jugadores en una lista de objetos Player
    private static List<Jugador> convertStringAListJugadores(String jugadores) {
        List<Jugador> listaJugadores = new ArrayList<>();
        String[] nombresJugadores = jugadores.split(",");
        for (String nombre : nombresJugadores) {
            listaJugadores.add(new JugadorHumano(nombre, 50));  // Aquí se asume que `Player` tiene un constructor que solo recibe nombre
        }
        return listaJugadores;
    }
}

