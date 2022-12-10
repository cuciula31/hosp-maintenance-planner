package ui.splash;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import ui.Transitions.FadeTransition;

public class Splash {

    @FXML
    private Pane splashPane;


    private final FadeTransition fadeTransitions = new FadeTransition();

    @FXML
    private void initialize(){

        for (Node n : splashPane.getChildren()){
            ProgressBar progressBar = (ProgressBar) n;
        }

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.millis(2000));
        pauseTransition.setOnFinished(actionEvent -> {
            Main main = new Main();

            splashPane.getScene().getWindow().hide();

            try{
                main.start(new Stage());
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        pauseTransition.play();
    }

}
