package namesayer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HomeController {


    @FXML
    public void handleSearch(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/namesayer/controller/searchScene.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Search");
        window.show();
    }
    @FXML
    public void handlePrac(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/namesayer/controller/sample.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Practice");
        window.show();

    }



    public void handleUpload(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/namesayer/controller/uploadScene.fxml"));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
    }

    public void handleExistingAttempts(ActionEvent event) throws IOException {
        // Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (new File("recordings").exists()){
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewExAttempt.fxml"));
            Scene scene = new Scene(loader.load());
            window.setScene(scene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Existing Attempts");
            alert.setContentText("Please select another option");
            alert.showAndWait();
            return;
        }


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
