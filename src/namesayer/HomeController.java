package namesayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public void handleSearch(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchScene.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Search");
        window.show();
    }
    @FXML
    public void handlePrac(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Practice");
        window.show();

    }

    @FXML
    public void handleQuit(ActionEvent event) {
        /**
         * First delete the temp files
         */

        // Get the current stage and close it.
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }
}
