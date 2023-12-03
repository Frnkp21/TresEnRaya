module juego {
    requires javafx.controls;
    requires javafx.fxml;

    opens juego to javafx.fxml;
    exports juego;
}