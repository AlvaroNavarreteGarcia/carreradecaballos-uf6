package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarreraService {
    // Parámetros de conexión
    private static final String DB_URL = "jdbc:mysql://localhost:3306/carrera_de_caballos";
    private static final String USER = "root"; // Usuario predeterminado en XAMPP
    private static final String PASS = ""; // Contraseña predeterminada (vacía en XAMPP)


    public void guardarEstadoCarrera(String estado, String informacionEstado) {
        String query = "INSERT INTO estado_carrera (estado, fecha_guardado, informacion_estado) VALUES (?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Registra en la consola para asegurarte de que esta parte del código se está ejecutando.
            System.out.println("Intentando guardar el estado: " + estado);

            // Inserta los valores en la consulta preparada
            pst.setString(1, estado);
            pst.setString(2, informacionEstado);

            // Ejecuta la consulta para insertar los datos
            pst.executeUpdate();

            // Log de éxito
            System.out.println("Estado guardado correctamente en la base de datos.");
        } catch (SQLException ex) {
            // Si ocurre algún error, lo registramos
            System.err.println("Error al guardar el estado.");
            ex.printStackTrace();
        }
    }
}
