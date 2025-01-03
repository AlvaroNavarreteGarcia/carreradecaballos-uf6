package com.baraja_cartas_gui;

import com.baraja_cartas_gui.Controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML de la primera escena
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/baraja_cartas_gui/Views/hello-view.fxml"));

        // Crear la primera escena
        Scene scene = new Scene(fxmlLoader.load(), 640, 320);

        // Obtener el controlador del FXML (HelloController) y pasar el Stage al controlador
        HelloController controller = fxmlLoader.getController();
        controller.setStage(stage);

        // Configurar y mostrar la escena
        stage.setTitle("Carrera de caballos");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
