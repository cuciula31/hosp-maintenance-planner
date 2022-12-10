package ui.menus.visualiserPane;

import com.jfoenix.controls.JFXButton;
import data.DAL.DeviceDAL;
import data.primitive.Device;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.Transitions.FadeTransition;
import ui.Transitions.MixedTransition;
import ui.cell.VisualiserCell;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class VisualiserPane {


    //UI
    //Pane
    @FXML
    private Pane mainPane;
    @FXML
    private Pane detailsContainer;
    //vbox
    @FXML
    private VBox vbox;
    //scrollPane
    @FXML
    private ScrollPane scrollPane;
    //buttons
    @FXML
    private JFXButton backButton;
    @FXML
    private ComboBox<String> deviceLocation;

    //dynamic elements
    private DetailsPane detailsPane = null;


    //Transitions
    private final MixedTransition mixedTransition = new MixedTransition();
    private final FadeTransition fadeTransition = new FadeTransition();

    //DAL
    private final DeviceDAL deviceDAL = new DeviceDAL();

    private int currentIndex;
    private Device globalDevice = null;

    private List<Device> fewDevices = null;
    public boolean fewAll = false;

    @FXML
    private void initialize() {
//        deviceLocation.getItems().clear();
        List<String> listOfDepartments = deviceDAL.readDevices().stream().map(Device::getDeviceLocation).distinct().collect(Collectors.toList());
        listOfDepartments.forEach(string -> deviceLocation.getItems().add(string));
//        deviceLocation.disarm();

    }

    private void generateACell(String text) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("visualiserCell.fxml"));
            root = loader.load();
            VisualiserCell currentCell = loader.getController();
            currentCell.setText(text);

        } catch (IOException e) {
            e.printStackTrace();
        }
        vbox.getChildren().add(root);
        assert root != null;
    }

    private void spawnViewer(Device device) {
        detailsContainer.getChildren().clear();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("detailsPane.fxml"));
            root = loader.load();
            detailsPane = loader.getController();
            detailsPane.setText(device);

            detailsPane.getDeleteButton().setOnMouseReleased(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Atenție");
                alert.setHeaderText("Doriți ștergerea dispozitivului curent?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    delete();
                }
            });

            detailsPane.getAddButton().setOnMouseClicked(event -> {
                if (fewAll) {
                    reinitFew();
                    reinitFewDeviceList();
                } else {
                    reinitAll();
                    reinitDeviceLocation();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        detailsContainer.getChildren().add(root);
        root.toFront();
        assert root != null;
    }

    private void spawnCells() {
        vbox.getChildren().clear();
        deviceDAL.readDevices().forEach((device -> {
            generateACell(device.getName() + "\n" + "exp: " + device.getEndDate());
        }));
    }

    private void reinitDeviceLocation(){
        deviceLocation.getItems().clear();
        List<String> listOfDepartments = deviceDAL.readDevices().stream().map(Device::getDeviceLocation).distinct().collect(Collectors.toList());
        listOfDepartments.forEach(string -> deviceLocation.getItems().add(string));
//        deviceLocation.disarm();
    }

    private void  reinitFewDeviceList(){
        deviceLocation.getItems().clear();
        List<String> listOfDepartments = fewDevices.stream().map(Device::getDeviceLocation).distinct().collect(Collectors.toList());
        listOfDepartments.forEach(string -> deviceLocation.getItems().add(string));
        deviceLocation.disarm();
    }

    private void spawnCellsFew() {

        vbox.getChildren().clear();
        fewDevices = deviceDAL.readDevices().stream().filter(device -> (DAYS.between(LocalDate.parse(device.getEndDate()), LocalDate.now()) * (-1)) < 30).collect(Collectors.toList());
        deviceDAL.readDevices().stream().filter(device -> (DAYS.between(LocalDate.parse(device.getEndDate()), LocalDate.now()) * (-1)) < 30).collect(Collectors.toList()).forEach(n -> {
            System.out.println(DAYS.between(LocalDate.parse(n.getEndDate()), LocalDate.now()));

        });
        fewDevices.forEach((device) -> generateACell(device.getName() + "\n" + "exp: " + device.getEndDate()));
    }

    private void spawnCellWithSpecificList(List<Device> devices){
        vbox.getChildren().clear();
        devices.forEach((device) -> generateACell(device.getName() + "\n" + "exp: " + device.getEndDate()));
    }

    private void cellActionWithSpecificList(List<Device> devices){
        for (Node n : vbox.getChildren()) {
            n.setOnMouseReleased(event -> {
                currentIndex = vbox.getChildren().indexOf(n);
                globalDevice = devices.get(currentIndex);
                System.out.println(currentIndex + " " + deviceDAL.readDevices().get(currentIndex).getName());
                spawnViewer(devices.get(currentIndex));
            });
        }
    }

    @FXML
    private void locationValueChanged(){
        List<Device> devicesFromSpecificLocation = deviceDAL.readDevices().stream()
                .filter(device -> device.getDeviceLocation().toLowerCase(Locale.ROOT).equals(deviceLocation.getValue()))
                .collect(Collectors.toList());
        spawnCellWithSpecificList(devicesFromSpecificLocation);
        cellActionWithSpecificList(devicesFromSpecificLocation);
    }

    private void fewCellAction() {
        for (Node n : vbox.getChildren()) {
            n.setOnMouseReleased(event -> {
                currentIndex = vbox.getChildren().indexOf(n);
                globalDevice = fewDevices.get(currentIndex);
                System.out.println(currentIndex + " " + deviceDAL.readDevices().get(currentIndex).getName());
                spawnViewer(fewDevices.get(currentIndex));
            });
        }
    }

    private void cellAction() {
        for (Node n : vbox.getChildren()) {
            n.setOnMouseReleased(event -> {
                currentIndex = vbox.getChildren().indexOf(n);
                globalDevice = deviceDAL.readDevices().get(currentIndex);
                System.out.println(currentIndex + " " + deviceDAL.readDevices().get(currentIndex).getName());
                spawnViewer(deviceDAL.readDevices().get(currentIndex));
            });
        }
    }

    public void spawnIfViewFew() {
        detailsContainer.getChildren().clear();
        spawnCellsFew();
        fewCellAction();
        reinitFewDeviceList();
        fadeTransition.fade(mainPane, 0, 1, 150, 0);
        mixedTransition.scaleFadeTransitionIn(mainPane);
        mainPane.toFront();
        fewAll = true;
    }

    public void spawnIfViewAll() {
        detailsContainer.getChildren().clear();
        spawnCells();
        reinitDeviceLocation();
        cellAction();
        fadeTransition.fade(mainPane, 0, 1, 150, 0);
        mixedTransition.scaleFadeTransitionIn(mainPane);
        mainPane.toFront();
        fewAll = false;
    }

    public void suicide() {
        fadeTransition.fade(mainPane, 1, 0, 150, 500);
        mixedTransition.scaleFadeTransitionOut(mainPane);
        mainPane.toBack();
        deviceLocation.setValue(null);
    }

    private void clear() {
        vbox.getChildren().clear();
    }

    private int deviceIndex(Device device) {

        int foundIndex = -1;

        for (int i = 0; i < deviceDAL.readDevices().size(); i++) {
            if (deviceDAL.readDevices().get(i).getId() == device.getId()) {
                foundIndex = i;
                System.out.println(i);
            }
        }
        return foundIndex;
    }

    private void delete() {
        mixedTransition.scaleFadeTransitionOutDeletePane((Pane) detailsContainer.getChildren().get(0));
        deviceDAL.deleteDevice(deviceDAL.readDevices().get(deviceIndex(globalDevice)));
        if (fewAll) {
            reinitFew();
            reinitFewDeviceList();
        } else {
            reinitAll();
            reinitDeviceLocation();
        }
    }

    private void reinitAll() {
        clear();
        spawnCells();
        cellAction();
    }

    private void reinitFew() {
        clear();
        spawnCellsFew();
        fewCellAction();
    }

    public JFXButton getBackButton() {
        return backButton;
    }
}
