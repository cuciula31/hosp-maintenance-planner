package ui.Transitions;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FadeTransition {

    public void fade(Node node, double fromOpacity, double toOpacity, int milis) {
        javafx.animation.FadeTransition fadeTransition = new javafx.animation.FadeTransition();
        fadeTransition.setFromValue(fromOpacity);

        if (toOpacity == 0) {
            node.setDisable(true);
        } else if (toOpacity != 0) {

            node.setDisable(false);
        }

        fadeTransition.setToValue(toOpacity);
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(milis));
        fadeTransition.play();
    }

    public void fade(Pane pane, double fromOpacity, double toOpacity, int milisDuration, int milisDelay) {
        javafx.animation.FadeTransition fadeTransition = new javafx.animation.FadeTransition();
        fadeTransition.setDelay(Duration.millis(milisDelay));
        fadeTransition.setFromValue(fromOpacity);
        fadeTransition.setOnFinished(actionEvent -> {
            if (toOpacity == 0) {
                pane.setDisable(true);
            } else if (toOpacity != 0) {

                pane.setDisable(false);
            }
        });
        fadeTransition.setToValue(toOpacity);
        fadeTransition.setNode(pane);
        fadeTransition.setDuration(Duration.millis(milisDuration));
        fadeTransition.play();


    }

    public void fade(Node node, double fromOpacity, double toOpacity, int milisDuration, int milisDelay) {
        javafx.animation.FadeTransition fadeTransition = new javafx.animation.FadeTransition();
        fadeTransition.setDelay(Duration.millis(milisDelay));
        fadeTransition.setFromValue(fromOpacity);

        fadeTransition.setOnFinished(actionEvent -> {
            if (toOpacity == 0) {
                node.setDisable(true);
                //node.setVisible(false);
            } else if (toOpacity != 0) {
                node.setDisable(false);
               // node.setVisible(true);
            }
        });

        fadeTransition.setToValue(toOpacity);
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(milisDuration));
        fadeTransition.play();


    }

    public void progressiveFadeIn(Pane pane, int milisDelay){
        int initialDelay = milisDelay;
        for (Node node : pane.getChildren()){
            fade(node,0,1,100,initialDelay);
            initialDelay+=100;
        }
    }

    public void progressiveFadeOut(Pane pane, int milisDelay){
        int initialDelay = milisDelay;
        for (Node node : pane.getChildren()){
            fade(node,1,0,100,initialDelay);
            initialDelay+=100;
        }
    }

}
