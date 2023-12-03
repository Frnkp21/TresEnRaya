package juego;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {


    public static String EligeModo() {
        String modo = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modos de juego");
        alert.setHeaderText("Selecciona un modo de juego.");

        ButtonType btPlayerVsPlayer = new ButtonType("Player VS Player");
        ButtonType btPlayerVsCpu = new ButtonType("Player VS Cpu");
        ButtonType btCpuVsCpu = new ButtonType("Cpu VS Cpu");

        alert.getButtonTypes().setAll(btPlayerVsPlayer,btPlayerVsCpu,btCpuVsCpu,ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == btPlayerVsPlayer) {
            modo = "VS Player";
        }else if(result.get() == btPlayerVsCpu) {
            modo = "VS Cpu";
        }else if(result.get() == btCpuVsCpu) {
            modo = "Cpu VS Cpu";
        }else {
            alert.close();
            modo = null;
        }
        return modo;
    }


    public static Boolean AbandonarPartida() {
        Boolean respuesta = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("abandonar?");
        alert.setHeaderText("Si abandonas la partida, obtendrás una derrota.");
        alert.getButtonTypes().setAll(ButtonType.CLOSE,ButtonType.YES,ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES) {
            respuesta = true;
        }else{
            respuesta=false;
            alert.close();
        }
        return respuesta;
    }

    public static void DebesEmpezarPartida() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("CUAC");
        alert.setHeaderText("dale a EMPEZAR PARTIDA");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }

    public static void Ganador(int ganador, String nombreJugador1, String nombreJugador2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (ganador == 0) {
            alert.setTitle("RESULTADO");
            alert.setHeaderText(nombreJugador1+" ha ganado");
        } else {
            alert.setTitle("RESULTADO");
            alert.setHeaderText(nombreJugador2+" ha ganado");
        }
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }
    public  static void Empate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EMPATE");
        alert.setHeaderText("EMPATE");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }
}
