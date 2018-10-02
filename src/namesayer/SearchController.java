package namesayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private Model model;



    public void handleHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Welcome to Name Sayer");
        stage.setScene(scene);
    }

    public void handleSearch(ActionEvent event) {
        System.out.println(userInput.getText());
        model = ModelImpl.getInstance();
        NameList nameList = model.nameSearch(userInput.getText());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                //\d*
                if (!newValue.matches("[aA-zZ \\-]")) {
                    //[^\d]
                    userInput.setText(newValue.replaceAll("[\\d$&~#@*+%?{}<>\\[\\]|\"!^,./\"|=_()]", ""));
                }
                if (userInput.getText().length() > 50) {
                    String s = userInput.getText().substring(0, 49);
                    userInput.setText(s);
                }
            }
        });

    }
}
