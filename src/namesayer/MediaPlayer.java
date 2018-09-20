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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MediaPlayer implements Initializable {

    private List<Name> names;
    private Model model;

    private NameVersion currentName;
    private int currentIndex;

    public MediaPlayer(List<Name> names, Model model) {
        this.names = names;
        this.model = model;
    }

    @FXML
    private MediaView mediaViewer;

    @FXML
    private ListView playList;

    @FXML
    private CheckBox handleMode;

    @FXML
    private Text infoPlay;

    @FXML
    public void handleNext(ActionEvent event) {
        setCurrentName(currentIndex + 1);
    }

    @FXML
    public void handleReplay(ActionEvent event) {

        if (currentIndex > 0) {
            setCurrentName(currentIndex - 1);
        }
    }

    @FXML
    protected void handleMicTest(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("micTest.fxml"));
        Stage window = new Stage();
        window.setTitle("Mic Test");
        window.setScene(new Scene(root, 300, 100));
        window.setMinWidth(300);
        window.setMinHeight(100);
        window.show();
    }

    @FXML
    public void handleBadQuality(javafx.event.ActionEvent event) {
        /**
         * Bad quality recording
         */
    }


    @FXML
    protected void handleHome(javafx.event.ActionEvent event) throws IOException {

        Parent viewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene viewScene = new Scene(viewParent);

        // get stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // change scene
        window.setScene(viewScene);
        window.setTitle("Name Sayer");
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // set play list
        ObservableList<Name> nameList = FXCollections.observableList(names);
        playList.setItems(nameList);

        if (nameList.size() > 0) {
            setCurrentName(0);
        }

    }

    private void setCurrentName(int index) {

        Name name = names.get(index);

        // verify name
        if (!(name instanceof NameVersion)) {
            return;
        }

        // safe to cast
        this.currentName = (NameVersion)name;

        // set text
        infoPlay.setText(currentName.getName());

        // set current index
        this.currentIndex = index;

    }
}
