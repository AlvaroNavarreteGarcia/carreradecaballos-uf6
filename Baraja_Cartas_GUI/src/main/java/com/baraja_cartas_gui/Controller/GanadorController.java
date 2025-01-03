package com.baraja_cartas_gui.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.application.Platform;

public class GanadorController {

    @FXML
    private ImageView imagenCartaGanador;

    @FXML
    private Text mensajeGanador;

    @FXML
    private Button botonSalir;

    @FXML
    public void initialize() {
        // Configurar el botón de salida
        botonSalir.setOnAction(event -> Platform.exit());
    }

    // Metodo para inicializar los datos del ganador
    public void setDatosGanador(String mensaje, String rutaImagen) {
        mensajeGanador.setText(mensaje);
        imagenCartaGanador.setImage(new javafx.scene.image.Image(rutaImagen));
    }
}
