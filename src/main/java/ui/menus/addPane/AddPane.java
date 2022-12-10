package ui.menus.addPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import data.DAL.DeviceDAL;
import data.primitive.Device;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ui.Transitions.FadeTransition;
import ui.Transitions.MixedTransition;
import ui.error.LocalError;

import java.time.LocalDate;

public class AddPane {

    //UI

    //Pane
    @FXML
    private Pane mainPane;
    //JFXTextField
    @FXML
    private JFXTextField deviceName;
    @FXML
    private JFXTextField deviceLocation;
    //JFXDatePicker
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    //JFXTextArea
    @FXML
    private JFXTextArea specsField;
    //Buttons
    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton addButton;
    //DAL
    private final DeviceDAL deviceDAL = new DeviceDAL();
    //Indirect UI elements
    private LocalError localError = null;


    //TRANSITION
    private final FadeTransition fadeTransition = new FadeTransition();
    private final MixedTransition mixedTransition = new MixedTransition();

    private boolean isEditing = false;
    private Device currentDevice = null;

    @FXML
    private void initialize() {

    }

    public void spawn() {
        startDatePicker.setPromptText("Data efectuare revizie");
        deviceName.setPromptText("Nume Dispozitiv");
        deviceLocation.setPromptText("Locatie Dispozitiv");
        fadeTransition.fade(mainPane, 0, 1, 150, 0);
        mixedTransition.scaleFadeTransitionIn(mainPane);
        mainPane.toFront();
        isEditing = false;
    }


    public void suicide() {
        fadeTransition.fade(mainPane, 1, 0, 150, 500);
        mixedTransition.scaleFadeTransitionOut(mainPane);
        mainPane.toBack();
        clear();
    }

    private void clear() {
        deviceName.clear();
        endDatePicker.setValue(null);
        startDatePicker.setValue(null);
        specsField.clear();
        deviceLocation.clear();
    }


    public JFXButton getBackButton() {
        return backButton;
    }

    public JFXButton getAddButton() {
        return addButton;
    }

    public void addSequence() {

            Device device = new Device();

            if (deviceName.getText().length() > 3 && deviceName.getText() != null) {
                if (startDatePicker.getValue() != null && endDatePicker.getValue() != null && startDatePicker.getValue().isBefore(endDatePicker.getValue())) {
                    if (!deviceLocation.getText().isEmpty()){
                        device.setName(deviceName.getText());
                        device.setStartDate(startDatePicker.getValue().toString());
                        device.setEndDate(endDatePicker.getValue().toString());
                        device.setSpecs(specsField.getText());
                        device.setDeviceLocation(deviceLocation.getText());
                        deviceDAL.insertDevice(device);
                        suicide();
                    }else{
                        deviceLocation.setPromptText("Locatia este invalida");
                    }

                } else {
                    startDatePicker.setPromptText("Data este invalidă");
                }
            } else {
                deviceName.setPromptText("Numele este invalid");
            }

    }
}
