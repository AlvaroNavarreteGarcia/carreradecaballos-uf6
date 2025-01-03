package com.baraja_cartas_gui.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class HelloController {

    @FXML
    private TextField nombreJugadorTextField; // Vincula el TextField del FXML

    private Stage stage;  // Variable para almacenar el Stage

    // Metodo para establecer el Stage desde HelloApplication
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Metodo que se llama cuando se hace clic en "Empezar Partida"
    @FXML
    private void empezarPartidaBotonClick() throws IOException {

        String nombreJugador = nombreJugadorTextField.getText().trim();

        if (nombreJugador.isEmpty()) {
            // Si está vacío, resaltar el TextField en rojo
            nombreJugadorTextField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        } else {
            // Restablecer el estilo si no está vacío
            nombreJugadorTextField.setStyle("");

            // Cargar la segunda escena
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/baraja_cartas_gui/Views/nuevaPartida-view.fxml"));

            Scene segundaEscena = new Scene(fxmlLoader.load());

            // Cambiar la escena en el Stage
            stage.setScene(segundaEscena);
            stage.show();
        }


    }

    @FXML
    private void salirBotonClick() throws IOException {
        // Cerrar la aplicación
        Platform.exit();
    }

}
