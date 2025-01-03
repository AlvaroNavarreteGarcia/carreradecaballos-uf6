module com.baraja_cartas_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.baraja_cartas_gui to javafx.fxml;
    exports com.baraja_cartas_gui;
    exports com.baraja_cartas_gui.Controller;
    opens com.baraja_cartas_gui.Controller to javafx.fxml;
}