package com.baraja_cartas_gui.Controller;

import com.baraja_cartas_gui.Model.Caballo;
import com.baraja_cartas_gui.Model.Carta;
import com.baraja_cartas_gui.Model.Baraja;
import com.baraja_cartas_gui.Model.Palo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarreraController {

    @FXML
    private Text cartaMostrada;
    @FXML
    private Text mensajeCrupier;
    @FXML
    private ImageView imagenCarta;
    @FXML
    private ImageView caballoBastos;
    @FXML
    private ImageView caballoEspadas;
    @FXML
    private ImageView caballoOros;
    @FXML
    private ImageView caballoCopas;

    private final Baraja baraja = new Baraja();
    private final Map<Palo, Caballo> caballos = new HashMap<>();
    private final Map<Palo, ImageView> imagenesCaballos = new HashMap<>();
    private int contadorRondas = 0;

    private static final double ULTIMA_POSICION = 370.0;

    @FXML
    public void initialize() {
        caballos.put(Palo.BASTOS, new Caballo(Palo.BASTOS));
        caballos.put(Palo.ESPADAS, new Caballo(Palo.ESPADAS));
        caballos.put(Palo.OROS, new Caballo(Palo.OROS));
        caballos.put(Palo.COPAS, new Caballo(Palo.COPAS));

        imagenesCaballos.put(Palo.BASTOS, caballoBastos);
        imagenesCaballos.put(Palo.ESPADAS, caballoEspadas);
        imagenesCaballos.put(Palo.OROS, caballoOros);
        imagenesCaballos.put(Palo.COPAS, caballoCopas);
    }

    @FXML
    public void onJugarRondaClick() throws IOException {
        Carta carta = baraja.obtenerCartaDeLaBaraja();

        imagenCarta.setImage(new Image(new File("src/main/resources/com/baraja_cartas_gui/images/" + carta.obtenerCodigoCarta() + ".png").toURI().toString()));
        cartaMostrada.setText(carta.toString());

        contadorRondas++;

        Palo palo = carta.getPalo();
        if (caballos.containsKey(palo)) {
            Caballo caballo = caballos.get(palo);

            if (contadorRondas % 4 == 0) {
                caballo.retroceder();
                mensajeCrupier.setText("El caballo de " + palo + " retrocede una posición");
            } else {
                caballo.avanzar();
                mensajeCrupier.setText("El caballo de " + palo + " avanza una posición");
            }

            // Actualizar la posición gráfica
            ImageView caballoImagen = imagenesCaballos.get(palo);
            caballoImagen.setTranslateX(caballo.getPosicion());

            verificarGanador();

        }
    }

    private void mostrarEscenaGanador(Caballo caballoGanador, Stage escenaAnterior) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/baraja_cartas_gui/Views/caballoGanador-view.fxml"));
            VBox root = loader.load();

            // Obtener el controlador asociado y configurar los datos del ganador
            GanadorController controlador = loader.getController();
            String mensaje = "¡El caballo de " + caballoGanador.getPalo() + " ha ganado!";
            String rutaImagen = new File("src/main/resources/com/baraja_cartas_gui/images/CABALLO_" + caballoGanador.getPalo() + ".png").toURI().toString();
            controlador.setDatosGanador(mensaje, rutaImagen);

            // Crear la escena y mostrarla
            Stage stage = new Stage();
            stage.setTitle("¡Tenemos un ganador!");
            stage.setScene(new Scene(root, 500, 400));
            stage.show();

            escenaAnterior.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Verificar si algún caballo ha llegado a la última posición
    @FXML
    public void verificarGanador() {
        for (Caballo caballo : caballos.values()) {
            if (caballo.getPosicion() >= ULTIMA_POSICION) {
                Stage stageActual = (Stage) cartaMostrada.getScene().getWindow();
                mostrarEscenaGanador(caballo, stageActual);
                break;
            }
        }
    }



    @FXML
    public void onSalirClick() throws IOException {
        Platform.exit();
    }
}
