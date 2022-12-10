package main;

import com.jfoenix.controls.JFXButton;
import data.DAL.CreateOnStart;
import data.DAL.DeviceDAL;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ui.Transitions.FadeTransition;
import ui.Transitions.MixedTransition;
import ui.menus.addPane.AddPane;
import ui.menus.visualiserPane.VisualiserPane;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Controller {

    //Panes
    @FXML
    private Pane mainPane;
    @FXML
    private Pane displayPane;
    @FXML
    private Pane principalPane;
    @FXML
    private Pane buttonsWrapper;
    @FXML
    private Pane managerInfos;

    //Buttons
    @FXML
    private Pane incomingEvents;
    @FXML
    private Pane activeEvents;
    @FXML
    private Pane exitButton;
    @FXML
    private Pane quickAddEvent;

    //Text
    @FXML
    private Text incomingEventText;

    //IndirectButtons
    private JFXButton addBackButton = null;
    private JFXButton addButton = null;
    private JFXButton visualiserBackButton = null;


    //Menus
    private AddPane addPane = null;
    private VisualiserPane visualiserPane = null;

    //DAL
    private final CreateOnStart createOnStart = new CreateOnStart();
    private final DeviceDAL deviceDAL = new DeviceDAL();


    //Transitions
    private final MixedTransition mixedTransition = new MixedTransition();
    private final FadeTransition fadeTransition = new FadeTransition();

    @FXML
    private void initialize() {
        mixedTransition.scaleFadeTransitionIn(buttonsWrapper);
        fadeTransition.progressiveFadeIn(managerInfos, 150);
        spawnQuickAdd();
        spawnViewer();
        createOnStart.createTableDevices();
        checkForFuture();
    }

    private void checkForFuture(){
        int counter = (int) deviceDAL.readDevices().stream().filter(device -> (DAYS.between(LocalDate.parse(device.getEndDate()), LocalDate.now())*(-1)) < 30).count();
        incomingEventText.setText(counter + " revizii in apropiere");
    }

    private void spawnQuickAdd() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("addPane.fxml"));
            root = loader.load();
            addPane = loader.getController();
            addBackButton = addPane.getBackButton();
            addButton = addPane.getAddButton();

            addButtonReleased();
            addBackButtonEvent();

        } catch (IOException e) {
            e.printStackTrace();
        }
        displayPane.getChildren().add(root);
        root.toBack();

        assert root != null;
    }

    private void spawnViewer() {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("visualiserPane.fxml"));
            root = loader.load();
            visualiserPane = loader.getController();
            visualiserBackButton = visualiserPane.getBackButton();
//           addButton = addPane.getAddButton();

            visualiserBackButtonEvent();

        } catch (IOException e) {
            e.printStackTrace();
        }
        displayPane.getChildren().add(root);
        root.toBack();

        assert root != null;
    }


    @FXML
    private void exitReleased() {
        System.exit(0);
    }

    @FXML
    private void incomingReleased(){
        mixedTransition.scaleFadeTransitionOut(buttonsWrapper);
        fadeTransition.progressiveFadeOut(managerInfos, 150);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setOnFinished(actionEvent -> {
            visualiserPane.spawnIfViewFew();
        });
        pauseTransition.setDelay(Duration.millis(150));
        pauseTransition.play();
    }


    @FXML
    private void quickAddReleased() {
        mixedTransition.scaleFadeTransitionOut(buttonsWrapper);
        fadeTransition.progressiveFadeOut(managerInfos, 150);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setOnFinished(actionEvent -> {
            addPane.spawn();
        });
        pauseTransition.setDelay(Duration.millis(150));
        pauseTransition.play();
    }

    @FXML
    private void visualiserReleased() {
        mixedTransition.scaleFadeTransitionOut(buttonsWrapper);
        fadeTransition.progressiveFadeOut(managerInfos, 150);

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setOnFinished(actionEvent -> {
            visualiserPane.spawnIfViewAll();
        });
        pauseTransition.setDelay(Duration.millis(150));
        pauseTransition.play();
    }

    private void addBackButtonEvent() {
        addBackButton.setOnMouseReleased(event -> {
            addPane.suicide();
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setOnFinished(actionEvent -> {
                mixedTransition.scaleFadeTransitionIn(buttonsWrapper);
                fadeTransition.progressiveFadeIn(managerInfos, 100);
            });
            pauseTransition.setDelay(Duration.millis(150));
            pauseTransition.play();
            checkForFuture();
        });
    }

    private void visualiserBackButtonEvent() {
        visualiserBackButton.setOnMouseReleased(event -> {
            visualiserPane.suicide();
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setOnFinished(actionEvent -> {
                mixedTransition.scaleFadeTransitionIn(buttonsWrapper);
                fadeTransition.progressiveFadeIn(managerInfos, 100);
            });
            pauseTransition.setDelay(Duration.millis(150));
            pauseTransition.play();
            checkForFuture();
        });
    }


    private void addButtonReleased() {
        addButton.setOnMouseReleased(event -> {
            addPane.addSequence();
//            addPane.suicide();
            PauseTransition pauseTransition = new PauseTransition();
            pauseTransition.setOnFinished(actionEvent -> {
                mixedTransition.scaleFadeTransitionIn(buttonsWrapper);
                fadeTransition.progressiveFadeIn(managerInfos, 100);
            });
            pauseTransition.setDelay(Duration.millis(150));
            pauseTransition.play();
        });
    }
}
