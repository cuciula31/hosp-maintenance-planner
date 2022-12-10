package ui.cell;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ui.Transitions.FadeTransition;

public class VisualiserCell {

    //Misc
    private final FadeTransition fadeTransitions = new FadeTransition();
    //UI Elements
    @FXML
    private Pane cellPane;
    @FXML
    private Text cellText;

    @FXML
    private void initialize() {
        fadeTransitions.fade(cellPane, 0, 1, 150);
    }

    public void setText(String text) {

        cellText.setText(text);

    }

}
