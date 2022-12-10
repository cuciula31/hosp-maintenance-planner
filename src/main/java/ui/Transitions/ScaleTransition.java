package ui.Transitions;

import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleTransition {

    public void scale(Node node, double from, double to, int milis, int delay) {
        javafx.animation.ScaleTransition st = new javafx.animation.ScaleTransition(Duration.millis(milis), node);
        st.setDelay(Duration.millis(delay));
        st.setFromX(from);
        st.setFromY(from);
        st.setToX(to);
        st.setToY(to);
        st.play();
    }

}
