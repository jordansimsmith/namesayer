package namesayer.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import namesayer.model.Model;
import namesayer.model.ModelImpl;
import namesayer.model.NameList;
import namesayer.model.SearchResult;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TextField userInput;

    @FXML
    private Label searchResult;

    @FXML
    private Label searchNotFound;

    private Model model;

    private NameList nameList;

    // switch to home screen
    public void handleHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Welcome to Name Sayer");
        stage.setScene(scene);
    }

    public void handleSearch(ActionEvent event) {

        if ("".equals(userInput.getText())) {
            showAlert();
            return;
        }
        // get entered search text and search database
        SearchResult result = model.nameSearch(userInput.getText());
        nameList = result.getNameList();

        // display names that are not found
        if (!result.getNamesNotFound().isEmpty()) {

            StringBuilder notFoundNames = new StringBuilder();
            notFoundNames.append("Names not found:");

            for (String name : result.getNamesNotFound()) {
                notFoundNames.append(" ").append(name);
            }

            searchNotFound.setText(notFoundNames.toString());
        } else {
            searchNotFound.setText("");
        }

        // no names found
        if (nameList == null) {
            showAlert();
            return;
        }

        // show found names in the list view
        searchResult.setText("Names found: " + nameList.toString());

    }


    public void handlePrac(ActionEvent event) throws IOException {

        // cannot play an empty list
        if (searchResult == null || searchResult.getText().equals("")) {
            showAlert();
            return;
        }

        // format list for practising
        List<NameList> nameList = new ArrayList<>();
        nameList.add(this.nameList);

        // switch to practice scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaPlayer.fxml"));
        loader.setController(new MediaPlayer(nameList));
        Scene scene = new Scene(loader.load());
        window.setScene(scene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        model = ModelImpl.getInstance();

        userInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                //\d*
                if (!newValue.matches("[aA-zZ \\-]")) {
                    //[^\d]
                    userInput.setText(newValue.replaceAll("[\\d$&~#@*+%?{}<>\\[\\]|\"!^,./\"|=_()]", ""));
                }

            }
        });

    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Names not found");
        alert.setContentText("Please enter another name");
        alert.showAndWait();
    }


    public void handleKeyPressed(KeyEvent keyEvent) {
        if (userInput.getText().length() > 49) {
            String s = userInput.getText(0, 49);
            userInput.setText(s);
            userInput.positionCaret(49);
        }
    }
}
