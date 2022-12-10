package ui.splash;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;

import java.io.FileNotFoundException;
import java.util.Objects;

public class MainSplash extends Application {


    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String FXML_PATH = "splash.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
        Parent root = loader.load();

        primaryStage.setTitle("Planificator revizii");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();

        Splash splash = loader.getController();


        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });
    }

    public void stop(Stage primaryStage) {
        primaryStage.close();
    }

}



