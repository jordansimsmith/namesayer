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
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    @FXML
    public ListView attemptsList;

    @FXML
    private Slider volumeSlider;

    @FXML
    public Button prevBtn;

    @FXML
    public Button playBtn;

    @FXML
    public Button nextBtn;

    @FXML
    public Button closeBtn;

    private List<NameVersion> names;

    private NameVersion currentName;

    private int currentIndex;

    private Model model;

    private double volume = 100;


    public void handleClose(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void handlePrevious(ActionEvent event) {
        if (currentIndex < names.size() - 1) {
            setCurrentName(currentIndex + 1);
        } else {
            setCurrentName(0);
        }
    }

    public void handlePlay(ActionEvent event) {
        System.out.println(currentName);

        Task task = new Task() {

            @Override
            protected Void call() throws Exception {

                // play and wait for the audio to stop
                String command = "ffplay -af volume=" + volume/100 + " -autoexit -nodisp " + currentName.getFile().getPath();
                new ProcessBuilder("/bin/bash", "-c", command).start().waitFor();

                return null;
            }
        };

        prevBtn.setDisable(true);
        playBtn.setDisable(true);
        nextBtn.setDisable(true);
        closeBtn.setDisable(true);

        task.setOnSucceeded(e -> {
            prevBtn.setDisable(false);
            playBtn.setDisable(false);
            nextBtn.setDisable(false);
            closeBtn.setDisable(false);
        });

        new Thread(task).start();
    }

    public void handleNext(ActionEvent event) {
        if (currentIndex > 0) {
            setCurrentName(currentIndex - 1);
        } else {
            setCurrentName(names.size() - 1);
        }
    }

    private void setCurrentName(int index) {

        // safe to cast
        currentName = names.get(index);

        // set current index
        currentIndex = index;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = ModelImpl.getInstance();


        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> this.volume = newValue.doubleValue());
        names = model.getAttempts();

        if (names.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Existing Attempts");
            alert.setContentText("Please return and select another option");
            alert.showAndWait();
            Stage window = (Stage) attemptsList.getScene().getWindow();
            window.close();
        } else {
            attemptsList.setItems(FXCollections.observableArrayList(names));
            setCurrentName(0);
        }




    }
}
