package namesayer.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import namesayer.model.MicWorker;

import java.net.URL;
import java.util.ResourceBundle;

public class MicTestController implements Initializable {

    @FXML
    private ProgressBar volumeBar;

    private MicWorker worker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        worker = new MicWorker();

        volumeBar.progressProperty().bind(worker.progressProperty());

        new Thread(worker).start();
    }

    public void cleanUp() {
        worker.cancel();
    }
}
