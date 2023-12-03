package juego;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class Controller {

    private static Partida partida = new Partida();
    @FXML
    private TextField jugador1TextField;
    private String nombreJugador1;
    @FXML
    private TextField jugador2TextField;
    private String nombreJugador2;


    @FXML
    Button buttonempezarpartida,buttonabandonarpartida;

    @FXML
    TextArea vjugador1,vjugador2,djugador1,djugador2;

    @FXML
    Text tj1,tj2;

    @FXML
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,bc;

    @FXML
    Button[] listabotones = new Button[8];


    @FXML
    public void EmpezarPartida(ActionEvent event) {
        nombreJugador1 = jugador1TextField.getText();
        tj1.setText("Turno de " + nombreJugador1);
        nombreJugador2 = jugador2TextField.getText();
        tj2.setText("Turno de " + nombreJugador2);



        buttonempezarpartida = (Button) event.getSource();
        listabotones = new Button[]{b0, b1, b2, b3, b4, b5, b6, b7, b8};

        partida.setModo(Alerts.EligeModo());


        if(partida.getModo() != null) {
            if(partida.getModo().equals("VS Player")) {
                partida.EmpezarPartida();
                if(partida.getTurno()==0) {
                    MostrarTurno(tj1);
                }else {
                    MostrarTurno(tj2);
                }
                OcultarBoton(buttonempezarpartida);
                MostrarBoton(buttonabandonarpartida);
            }else {
                String dificultad = "normal";
                partida.setIa(dificultad);
                partida.EmpezarPartida();
                OcultarBoton(buttonempezarpartida);
                MostrarBoton(buttonabandonarpartida);
                if (partida.getTurno() == 0) {
                    MostrarTurno(tj1);
                    if (partida.getModo().equals("Cpu VS Cpu")) {
                        TurnoCOM(partida.getCuadricula());
                    }
                } else {
                    MostrarTurno(tj2);
                    TurnoCOM(partida.getCuadricula());
                }
                }
            }
        }

    @FXML
    public void AbandonarPartida(ActionEvent event) {

        buttonabandonarpartida = (Button) event.getSource();
        Boolean respuesta = Alerts.AbandonarPartida();

        if(respuesta) {
            Partida.AbandonarPartida();
            Restart();
        }
    }

    @FXML
    public void Marcar(ActionEvent event) throws InterruptedException {
        if (!partida.getEstado()) {
            Alerts.DebesEmpezarPartida();
            return;
        }

        Button clickedButton = (Button) event.getSource();
        int id = Integer.parseInt(clickedButton.getId().replaceAll("[b]", ""));
        char[] cuadricula = partida.getCuadricula();
        partida.PropiedadesTurno();

        if (id >= 0 && id < cuadricula.length && partida.EstadoCuadricula(cuadricula[id])) {
            marcarCasilla(id);
            if (partida.ComprobarVictoria(partida.getValue().charAt(0))) {
                Partida.Ganador(partida.getValue().charAt(0), nombreJugador1, nombreJugador2);
                handleGameEnd();
            } else if (partida.CuadriculaLLena()) {
                Alerts.Empate();
                handleGameEnd();
            } else {
                handleNextTurn();
            }

            if (!partida.getModo().equals("VS Player") && partida.getEstado()) {
                TurnoCOM(cuadricula);
            }
        }
    }

    private void marcarCasilla(int id) {
        listabotones[id].styleProperty().setValue("-fx-text-fill: " + partida.getColor() + ";");
        listabotones[id].setText(partida.getValue());
        partida.setPosCuadricula(id, partida.getValue().charAt(0));
    }

    private void handleGameEnd() {
        Restart();
        partida.FinalizarPartida();
    }

    private void handleNextTurn() {
        if (partida.getTurno() == 0) {
            SetTurno(1, tj1, tj2);
        } else {
            SetTurno(0, tj2, tj1);
        }
    }

    private void TurnoCOM(char[] cuadricula) {
        partida.PropiedadesTurno();
        if (partida.getIa().equalsIgnoreCase("normal")) {
            IA(cuadricula);
        } else {
            IA(cuadricula);
        }


        if(partida.getEstado()) {
            if(partida.getModo().equals("Cpu VS Cpu")) {
                if(partida.getTurno()==0) {
                    SetTurno(1,tj1,tj2);
                }else {
                    SetTurno(0,tj2,tj1);
                }
                TurnoCOM(cuadricula);
            }else {
                SetTurno(0,tj2,tj1);
            }


        }
    }

    private void IA(char[] cuadricula) {
        int random;
        do {
            random = (int) (Math.random() * 9); // Cambié de 10 a 9 para asegurar un índice válido
        } while (!partida.EstadoCuadricula(cuadricula[random]));

        listabotones[random].styleProperty().setValue("-fx-text-fill: " + partida.getColor() + ";");
        listabotones[random].setText(partida.getValue());
        partida.setPosCuadricula(random, partida.getValue().charAt(0));

        if (partida.ComprobarVictoria(partida.getValue().charAt(0))) {
            Partida.Ganador(partida.getValue().charAt(0), nombreJugador1, nombreJugador2);
            handleGameEnd();
        } else {
            if (partida.CuadriculaLLena()) {
                Alerts.Empate();
                handleGameEnd();
            }
        }
    }


    private void OcultarBoton(Button button) {
        button.styleProperty().setValue("Visibility: false");
    }

    private void MostrarBoton(Button button) {
        button.styleProperty().setValue("Visibility: true");
    }

    private void MostrarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility:true");
    }

    private void OcultarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility: false");
    }

    private void RefrescarMarcador(Partida partida,TextArea vj1,TextArea vj2,TextArea dj1,TextArea dj2) {
        vj1.setText(String.valueOf(partida.getVjugador1()));
        vj2.setText(String.valueOf(partida.getVjugador2()));
        dj1.setText(String.valueOf(partida.getDjugador1()));
        dj2.setText(String.valueOf(partida.getDjugador2()));
    }

    private void VaciarCuadriculaFX() {
        for (Button button : listabotones) {
            button.textProperty().setValue("");
        }
    }


    private void Restart() {
        RefrescarMarcador(partida,vjugador1,vjugador2,djugador1,djugador2);
        OcultarTurno(tj1);
        OcultarTurno(tj2);
        OcultarBoton(buttonabandonarpartida);
        MostrarBoton(buttonempezarpartida);
        VaciarCuadriculaFX();
    }

    private void SetTurno(int turno,Text turnoOcultar, Text turnoMostrar) {
        partida.setTurno(turno);
        OcultarTurno(turnoOcultar);
        MostrarTurno(turnoMostrar);
    }
}
