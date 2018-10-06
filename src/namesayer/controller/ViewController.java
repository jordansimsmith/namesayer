package namesayer.controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import namesayer.model.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    public ListView attemptsList;

    @FXML
    private Slider volumeSlider;

    @FXML
    public Button playBtn;

    @FXML
    public Button closeBtn;

    private List<NameVersion> names;


    private Model model;

    private double volume = 100;


    public void handleClose(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void handlePlay(ActionEvent event) {

        NameVersion selected = (NameVersion) attemptsList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Please select a name to play it.");
            alert.showAndWait();
            return;
        }

        // construct task to play back audio
        Task task = new Task() {

            @Override
            protected Void call() throws Exception {

                // play and wait for the audio to stop
                String command = "ffplay -af volume=" + volume / 100 + " -autoexit -nodisp " + selected.getFile().getPath();
                new ProcessBuilder("/bin/bash", "-c", command).start().waitFor();

                return null;
            }
        };

        playBtn.setDisable(true);
        closeBtn.setDisable(true);

        task.setOnSucceeded(e -> {
            playBtn.setDisable(false);
            closeBtn.setDisable(false);
        });

        new Thread(task).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = ModelImpl.getInstance();

        // listen for volume changes
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.volume = newValue.doubleValue());

        // get and sort names from model
        names = model.getAttempts();
        names.sort(Comparator.comparing(NameVersion::toString));

        if (names.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Existing Attempts");
            alert.setContentText("Please return and select another option");
            alert.showAndWait();
            Stage window = (Stage) attemptsList.getScene().getWindow();
            window.close();
        } else {
            // populate list view
            attemptsList.setItems(FXCollections.observableArrayList(names));
        }

    }
}
