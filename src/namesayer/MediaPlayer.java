package namesayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MediaPlayer implements Initializable {

    private List<NameList> names;
    private Model model;

    private NameList currentName;
    private int currentIndex;

    private PracticeWorker worker;

    public MediaPlayer(List<NameList> names) {
        this.names = names;
    }

    @FXML
    private ProgressBar recordPB;

    @FXML
    private MediaView mediaViewer;

    @FXML
    private ListView playList;

    @FXML
    private CheckBox handleMode;

    @FXML
    private Text infoPlay;

    @FXML
    private Button playButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button stopRecButton;

    @FXML
    private Label status;

    @FXML
    private Slider volumeSlider;

    @FXML
    public void handleNext(ActionEvent event) {

        if (currentIndex < names.size() - 1) {
            setCurrentName(currentIndex + 1);
        } else {
            setCurrentName(0);
        }
    }

    @FXML
    public void handlePrevious(ActionEvent event) {

        if (currentIndex > 0) {
            setCurrentName(currentIndex - 1);
        } else {
            setCurrentName(names.size() - 1);
        }
    }

    @FXML
    public void handlePlay(ActionEvent event) {

        worker = model.getPracticeWorker(currentName, handleMode.isSelected());

        status.textProperty().bind(worker.messageProperty());

        recordPB.progressProperty().bind(worker.progressProperty());
        new Thread(worker).start();

        playButton.setDisable(true);
        homeButton.setDisable(true);
        nextButton.setDisable(true);
        previousButton.setDisable(true);

        // Only have the stop button shown if it is in recording mode
        if (!handleMode.isSelected()) {
            stopRecButton.setDisable(false);
        }

        worker.setOnSucceeded(e -> {
            playButton.setDisable(false);
            homeButton.setDisable(false);
            nextButton.setDisable(false);
            previousButton.setDisable(false);
            stopRecButton.setDisable(true);
            worker = null;

            status.textProperty().unbind();
            status.setText("");
        });
    }

    @FXML
    public void stopRecording(ActionEvent event) {

        if (worker != null) {
            worker.stopRecording();
        }
    }

    @FXML
    public void handleMicTest(ActionEvent event) throws IOException {

        // new stage
        Stage window = new Stage();

        // construct controller
        MicTestController controller = new MicTestController();

        // set on close action
        window.setOnCloseRequest(event1 -> controller.cleanUp());

        // set controller and fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("micTest.fxml"));
        loader.setController(controller);
        Parent root = loader.load();

        window.setTitle("Mic Test");
        window.setScene(new Scene(root, 300, 100));
        window.setMinWidth(300);
        window.setMinHeight(100);
        window.setResizable(false);

        window.show();

    }

    @FXML
    public void handleBadQuality(ActionEvent event) {
        model.lowQualityName(currentName);
    }


    @FXML
    public void handleHome(ActionEvent event) throws IOException {

        Parent viewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene viewScene = new Scene(viewParent);

        // get stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // change scene
        window.setScene(viewScene);
        window.setTitle("Name Sayer");
        window.setResizable(false);
        window.setResizable(true);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = ModelImpl.getInstance();

        // listener updates model of volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> model.setVolume(newValue.doubleValue()));

        // set play list
        ObservableList<NameList> nameList = FXCollections.observableList(names);
        playList.setItems(nameList);

        setCurrentName(0);

    }

    private void setCurrentName(int index) {

        // safe to cast
        this.currentName = names.get(index);

        // set text
        infoPlay.setText(currentName.toString());

        // set current index
        this.currentIndex = index;

    }
}
