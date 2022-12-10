package ui.error;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ui.Transitions.FadeTransition;
import ui.Transitions.MixedTransition;

public class LocalError {

    //UI
    //Pane
    @FXML
    private Pane mainPane;
    @FXML
    private Pane blurPane;
    @FXML
    private Pane errorPane;

    //Text
    @FXML
    private Text text;

    //JFXButton
    @FXML
    private JFXButton okButton;

    //Transition
    private final FadeTransition fadeTransition = new FadeTransition();
    private final MixedTransition mixedTransition = new MixedTransition();

    @FXML
    private void initialize(){
        blurPane.setOpacity(0);
        errorPane.setOpacity(0);

    }

    public void spawn(){
        mainPane.toFront();
        mixedTransition.entireFadeIn(errorPane);
        fadeTransition.fade(blurPane,0,0.3,1000,250);
    }

    public void suicide(){
        mixedTransition.entireFadeOut(errorPane);
        fadeTransition.fade(blurPane,0.3,0,100,100);
        mainPane.toBack();
    }

    public JFXButton getOkButton() {
        return okButton;
    }
}
