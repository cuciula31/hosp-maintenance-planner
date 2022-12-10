package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final String FXML_PATH = "mainFrame.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(FXML_PATH));
        Parent root = loader.load();

        primaryStage.setTitle("Planificator revizii");
        primaryStage.setScene(new Scene(root, 1300, 700));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();



        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            primaryStage.setX(mouseEvent.getScreenX() - xOffset);
            primaryStage.setY(mouseEvent.getScreenY() - yOffset);
        });
    }


}
