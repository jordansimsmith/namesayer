package namesayer.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    private PracticeWorker worker;

    private NameVersion currentName;

    private int currentIndex;

    private Model model;


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
        prevBtn.setDisable(true);
        playBtn.setDisable(true);
        nextBtn.setDisable(true);
        closeBtn.setDisable(true);

        List<Name> list = new ArrayList<>();

        list.add(new Name(currentName.getName()));

        worker = model.getPracticeWorker(new NameList(list), true);

        new Thread(worker).start();


        worker.setOnSucceeded(e -> {
            prevBtn.setDisable(false);
            playBtn.setDisable(false);
            nextBtn.setDisable(false);
            closeBtn.setDisable(false);
            worker = null;
        });


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

        // listener updates model of volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> model.setVolume(newValue.doubleValue()));

        if (new File("recordings").exists()){
            attemptsList.setItems(FXCollections.observableArrayList(model.getAttempts()));
            setCurrentName(0);

        }

    }
}
