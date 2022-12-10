package ui.menus.visualiserPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import data.DAL.DeviceDAL;
import data.primitive.Device;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ui.Transitions.FadeTransition;

import java.time.LocalDate;
import java.util.Locale;

public class DetailsPane {

    @FXML
    private Pane mainPane;

    @FXML
    private Text deviceText;
    @FXML
    private Text startText;
    @FXML
    private Text endText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Pane editPane;
    @FXML
    private Pane principalPane;
    @FXML
    private Pane imagePane;
    @FXML
    private Text deviceLocation;

    @FXML
    private JFXTextField deviceName;
    //JFXDatePicker
    @FXML
    private JFXDatePicker startDatePicker;
    @FXML
    private JFXDatePicker endDatePicker;
    //JFXTextArea
    @FXML
    private JFXTextArea specsField;
    @FXML
    private JFXTextField deviceLocationField;
    //Buttons
    @FXML
    private JFXButton backButton;



    @FXML
    private JFXButton addButton;

    private final String start = "Data revizie:";
    private final String end = "Data expirare revizie:";

    private final FadeTransition fadeTransition = new FadeTransition();
    private final DeviceDAL deviceDAL = new DeviceDAL();
    private Device globalDevice = null;

    @FXML
    private void initialize() {
        initialTransition();
    }

    public void setText(Device device) {
        globalDevice = device;
        System.out.println(globalDevice.getName());
        deviceText.setText(device.getName().toUpperCase(Locale.ROOT));
        startText.setText(start + " " + device.getStartDate());
        endText.setText(end + " " + device.getEndDate());
        descriptionText.setText(device.getSpecs());
        deviceLocation.setText(device.getDeviceLocation());
    }


    @FXML
    private void editReleased() {
        clear();
        setFields(globalDevice);
        editPane.toFront();
        fadeTransition.fade(principalPane, 1, 0, 150, 0);
        fadeTransition.fade(editPane, 0, 1, 150, 150);


    }


    private void initialTransition() {
        fadeTransition.fade(imagePane, 0, 1, 150, 0);
        fadeTransition.fade(principalPane, 0, 1, 150, 0);
        principalPane.toFront();
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    @FXML
    private void editBackReleased(){
        principalPane.toFront();
        fadeTransition.fade(editPane, 1, 0, 150, 0);
        fadeTransition.fade(principalPane, 0, 1, 150, 150);
    }

    @FXML
    private void editButtonReleased(){
        if (deviceName.getText().length() > 3 && deviceName.getText() != null) {
            if (startDatePicker.getValue() != null && endDatePicker.getValue() != null && startDatePicker.getValue().isBefore(endDatePicker.getValue())) {
                globalDevice.setName(deviceName.getText());
                globalDevice.setStartDate(startDatePicker.getValue().toString());
                globalDevice.setEndDate(endDatePicker.getValue().toString());
                globalDevice.setSpecs(specsField.getText());
                globalDevice.setDeviceLocation(deviceLocationField.getText());
                deviceDAL.updateDevice(globalDevice);
                setText(globalDevice);
                editBackReleased();
            } else {
                startDatePicker.setPromptText("Data este invalidă");
            }
        }else{
            deviceName.setPromptText("Numele este invalid");
        }
    }

    private void setFields(Device device){
        deviceName.setText(device.getName());
        startDatePicker.setValue(LocalDate.parse(device.getStartDate()));
        endDatePicker.setValue(LocalDate.parse(device.getEndDate()));
        specsField.setText(device.getSpecs());
        deviceLocationField.setText(device.getDeviceLocation());
    }

    private void clear() {
        deviceName.clear();
        endDatePicker.setValue(null);
        startDatePicker.setValue(null);
        specsField.clear();
        deviceLocationField.clear();
    }

    public JFXButton getAddButton() {
        return addButton;
    }


}
