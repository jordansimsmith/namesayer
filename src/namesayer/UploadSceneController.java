package namesayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UploadSceneController {

    private FileChooser fileChooser = new FileChooser();

    private Model model = ModelImpl.getInstance();
    private List<NameList> nameList;

    @FXML
    private ListView uploadList;

    public void handleUpload(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        List<String> names = new ArrayList<>();
        String line;
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("Chosen file: " + file.getName());


            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                names.add(line);
                nameList.add(model.nameSearch(line));

            }
            fileReader.close();
            uploadList.setItems(FXCollections.observableArrayList(names));
        }

        // Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

    }

    public void handleHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void handlePrac(ActionEvent event) throws IOException {
        if (nameList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot play an empty list");
            alert.setContentText("Please upload an appropriate text file");
            alert.showAndWait();
            return;
        }

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setController(new MediaPlayer(nameList));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.setTitle("Practice");
        window.show();
    }
}
