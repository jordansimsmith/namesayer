package namesayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class micTestController implements Initializable {

    @FXML
    private ProgressBar volumeBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        MicWorker worker = new MicWorker();

        volumeBar.progressProperty().bind(worker.progressProperty());

        new Thread(worker).start();
    }
}
