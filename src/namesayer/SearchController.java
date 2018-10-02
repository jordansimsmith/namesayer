package namesayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private TextField userInput;

    public void handleHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Welcome to Name Sayer");
        stage.setScene(scene);
    }

    public void handleSearch(ActionEvent event) {
        System.out.println(userInput.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInput.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                if(!userInput.getText().matches("[1-5]\\.[0-9]|6\\.0")){
                    //when it not matches the pattern (1.0 - 6.0)
                    //set the textField empty
                    userInput.setText("");
                }
            }
            if (userInput.getText().length() > 51) {
                String s = userInput.getText().substring(0, 49);
                userInput.setText(s);
            }

        });
    }
}
