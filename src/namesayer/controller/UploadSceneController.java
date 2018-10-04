package namesayer.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import namesayer.model.Model;
import namesayer.model.ModelImpl;
import namesayer.model.NameList;
import namesayer.model.SearchResult;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UploadSceneController {

    private FileChooser fileChooser = new FileChooser();

    private Model model = ModelImpl.getInstance();
    private List<NameList> nameList = new ArrayList<>();

    @FXML
    private ListView uploadList;

    public void handleUpload(ActionEvent event) throws IOException {

        // open file explorer in a new window
        Stage stage = new Stage();

        // user selects file to upload
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            // parse text file
            List<SearchResult> results = model.parseFile(file);

            for (SearchResult result: results) {

                // only add if there are names to play
                if (!result.getNameList().getNames().isEmpty()) {
                    nameList.add(result.getNameList());
                }

                // TODO: display names not found
                System.out.println(result.getNamesNotFound());

            }

            // not empty
            if (!nameList.isEmpty()) {
                // set list view with parsed names
                uploadList.setItems(FXCollections.observableArrayList(nameList));
            }
        }

    }

    public void handleHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void handlePrac(ActionEvent event) throws IOException {

        // attempt to play with no names selected
        if (uploadList.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot play an empty list");
            alert.setContentText("Please upload an appropriate text file");
            alert.showAndWait();
            return;
        }

        // switch to practice scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaPlayer.fxml"));
        loader.setController(new MediaPlayer(nameList));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Practice");
        window.show();
    }
}
