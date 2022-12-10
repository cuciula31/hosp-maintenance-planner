package ui.Transitions;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class MixedTransition {

    private final FadeTransition fadeTransition = new FadeTransition();
    private final ScaleTransition scaleTransition = new ScaleTransition();

    public void scaleFadeTransitionIn(Pane n){
        int initialDelay = 150;
        for (Node node : n.getChildren()){
            scaleTransition.scale(node,0.5,1,100,initialDelay);
            fadeTransition.fade(node,0,1,100,initialDelay);
            initialDelay+=100;
        }
    }

    public void entireFadeIn(Pane n){
        scaleTransition.scale(n,0.5,1,100,0);
        fadeTransition.fade(n,0,1,100);
    }

    public void scaleFadeTransitionOut(Pane n){
        int initialDelay = 100;
        for (Node node : n.getChildren()){
            scaleTransition.scale(node,1,0.5,100,initialDelay);
            fadeTransition.fade(node,1,0,100,initialDelay);
            initialDelay+=100;
        }
    }

    public void scaleFadeTransitionOutDeletePane(Pane n){
        int initialDelay = 100;
        for (Node node : n.getChildren()){
            scaleTransition.scale(node,1,0.5,100,initialDelay);
            fadeTransition.fade(node,1,0,100,initialDelay);
            initialDelay+=100;
        }
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(200));
        pauseTransition.setOnFinished(actionEvent -> {
            Pane parent = (Pane)n.getParent();
            parent.getChildren().remove(0);
        });
        pauseTransition.play();
    }

    public void entireFadeOut(Pane n){
        scaleTransition.scale(n,1,0.5,100,0);
        fadeTransition.fade(n,1,0,100,0);
    }
}
